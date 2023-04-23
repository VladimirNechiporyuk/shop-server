package com.flamelab.shopserver.managers.impl;

import com.flamelab.shopserver.dtos.create.CreateAuthTokenDto;
import com.flamelab.shopserver.dtos.transfer.TransferAuthTokenDto;
import com.flamelab.shopserver.entities.User;
import com.flamelab.shopserver.enums.Roles;
import com.flamelab.shopserver.exceptions.ResourceException;
import com.flamelab.shopserver.managers.AuthManager;
import com.flamelab.shopserver.mappers.AuthTokenMapper;
import com.flamelab.shopserver.services.AuthService;
import com.flamelab.shopserver.services.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.flamelab.shopserver.enums.AuthTokenType.BEARER;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

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
                if (authService.isTokenExists(createAuthTokenDto.getEmail())) {
                    authService.deleteTokenByEmail(createAuthTokenDto.getEmail());
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

    @Override
    public void logout(TransferAuthTokenDto authToken) {
        authService.deleteTokenByValue(authToken.getToken());
    }

    @Override
    public TransferAuthTokenDto validateAuthToken(String token, List<Roles> availableRoles) {
        return authTokenMapper.mapToDto(authService.validateTokenAndReturn(token, availableRoles));
    }

}
