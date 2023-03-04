package com.flamelab.shopserver.managers.impl;

import com.flamelab.shopserver.dtos.create.external.CreateUserAuthToken;
import com.flamelab.shopserver.dtos.transfer.TransferAuthTokenDto;
import com.flamelab.shopserver.entities.AuthToken;
import com.flamelab.shopserver.entities.User;
import com.flamelab.shopserver.enums.Roles;
import com.flamelab.shopserver.exceptions.EmailOrPasswordIsNotCorrectException;
import com.flamelab.shopserver.exceptions.UnauthorizedUserException;
import com.flamelab.shopserver.dtos.create.internal.InternalCreateUserAuthToken;
import com.flamelab.shopserver.managers.AuthManager;
import com.flamelab.shopserver.services.AuthService;
import com.flamelab.shopserver.services.UserService;
import com.flamelab.shopserver.utiles.MapperUtility;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static com.flamelab.shopserver.utiles.naming.FieldNames.EMAIL__FIELD_APPELLATION;

@Service
@RequiredArgsConstructor
public class AuthManagerImpl implements AuthManager {

    private final UserService userService;
    private final AuthService authService;
    private final MapperUtility<AuthToken, TransferAuthTokenDto> mapperFromEntityToTransferDto;
    private final String INCORRECT_EMAIL_OR_PASSWORD_EXCEPTION_TEXT = "Entered email or password is not correct.";
    private final PasswordEncoder encoder;

    @Override
    public TransferAuthTokenDto login(CreateUserAuthToken createUserAuthToken) {
        User user = userService.getEntityByCriterias(Map.of(EMAIL__FIELD_APPELLATION, createUserAuthToken.getEmail()));
        if (user != null) {
            if (authService.isPasswordCorrect(user, createUserAuthToken.getPassword())) {
                if (authService.isTokenExists(createUserAuthToken.getEmail())) {
                    authService.deleteTokenByEmail(createUserAuthToken.getEmail());
                }
                return mapperFromEntityToTransferDto.map(
                        authService.createToken(provideInternalAuthToken(createUserAuthToken, user), user),
                        AuthToken.class,
                        TransferAuthTokenDto.class);
                // according to security protocols for wrong Email or/and Password options need to throw the same exception
            } else {
                throw new EmailOrPasswordIsNotCorrectException(INCORRECT_EMAIL_OR_PASSWORD_EXCEPTION_TEXT);
            }
        } else {
            throw new EmailOrPasswordIsNotCorrectException(INCORRECT_EMAIL_OR_PASSWORD_EXCEPTION_TEXT);
        }
    }

    @Override
    public void logout(String authorization) {
        authService.deleteTokenByValue(authorization);
    }

    @Override
    public TransferAuthTokenDto validateAuthToken(String token, List<Roles> availableRoles) {
        if (token.isEmpty()) {
            throw new UnauthorizedUserException("Unauthorized");
        }
        return mapperFromEntityToTransferDto.map(
                authService.getTokenIfValid(token, availableRoles),
                AuthToken.class,
                TransferAuthTokenDto.class
        );
    }

    private InternalCreateUserAuthToken provideInternalAuthToken(CreateUserAuthToken createUserAuthToken, User user) {
        InternalCreateUserAuthToken internalCreateToken = new InternalCreateUserAuthToken();
        internalCreateToken.setUserId(user.getId().toHexString());
        internalCreateToken.setToken(ObjectId.get().toHexString());
        internalCreateToken.setEmail(createUserAuthToken.getEmail());
        internalCreateToken.setRole(user.getRole());
        internalCreateToken.setUsageAmount(0);
        return internalCreateToken;
    }

}
