package com.flamelab.shopserver.services;

import com.flamelab.shopserver.entities.AuthToken;
import com.flamelab.shopserver.entities.User;
import com.flamelab.shopserver.enums.Roles;
import com.flamelab.shopserver.dtos.create.internal.InternalCreateUserAuthToken;
import com.flamelab.shopserver.utiles.naming.FieldNames;

import java.util.List;
import java.util.Map;

public interface AuthService {

    boolean isPasswordCorrect(User user, String enteredPassword);

    AuthToken createToken(InternalCreateUserAuthToken createUserAuthToken, User user);

    AuthToken getToken(Map<FieldNames, Object> criterias);

    void increaseTokenUsageAmount(Map<FieldNames, Object> criterias);

    boolean isTokenValid(String token, List<Roles> availableRoles);

    boolean isTokenExists(String email);

    void deleteTokenByValue(String tokenValue);

    void deleteTokenByEmail(String email);

}
