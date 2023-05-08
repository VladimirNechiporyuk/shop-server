package com.flamelab.shopserver.managers.impl;

import com.flamelab.shopserver.dtos.create.CreateAuthTokenDto;
import com.flamelab.shopserver.dtos.transfer.TransferAuthTokenDto;
import com.flamelab.shopserver.entities.AuthToken;
import com.flamelab.shopserver.entities.User;
import com.flamelab.shopserver.enums.Roles;
import com.flamelab.shopserver.exceptions.ResourceException;
import com.flamelab.shopserver.managers.AuthManager;
import com.flamelab.shopserver.mappers.AuthTokenMapper;
import com.flamelab.shopserver.services.AuthService;
import com.flamelab.shopserver.services.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

@Service
@RequiredArgsConstructor
public class AuthManagerImpl implements AuthManager {

    private final UsersService userService;
    private final AuthService authService;
    private final AuthTokenMapper authTokenMapper;

    private final String INCORRECT_EMAIL_OR_PASSWORD_EXCEPTION_TEXT = "Entered email or password is not correct.";

    @Override
    public TransferAuthTokenDto login(CreateAuthTokenDto createAuthTokenDto) {
        User user = userService.getUserByEmail(createAuthTokenDto.getEmail());
        if (user != null) {
            if (user.getPassword().equals(createAuthTokenDto.getPassword())) {
                validateIsUserActive(user);
                if (authService.isTokenExistsByEmail(createAuthTokenDto.getEmail())) {
                    AuthToken token = authService.getTokenByEmail(createAuthTokenDto.getEmail());
                    authService.deleteTokenByTokenId(token.getId());
                }
                return authTokenMapper.mapToDto(authService.createToken(user));
            } else {
                // according to security protocols for wrong Email or Password options need to throw the same exception
                throw new ResourceException(UNAUTHORIZED, INCORRECT_EMAIL_OR_PASSWORD_EXCEPTION_TEXT);
            }
        } else {
            throw new ResourceException(UNAUTHORIZED, INCORRECT_EMAIL_OR_PASSWORD_EXCEPTION_TEXT);
        }
    }

    private void validateIsUserActive(User user) {
        if (!user.isActive()) {
            throw new ResourceException(UNPROCESSABLE_ENTITY, "User is not active. Please activate it via link in your email.");
        }
    }

    @Override
    public void logout(TransferAuthTokenDto authToken) {
        authService.deleteTokenByTokenId(authToken.getId());
    }

    @Override
    public TransferAuthTokenDto validateAuthToken(String token, List<Roles> availableRoles) {
        return authTokenMapper.mapToDto(authService.validateTokenAndReturn(token, availableRoles));
    }

}
