package com.flamelab.shopserver.services;

import com.flamelab.shopserver.entities.AuthToken;
import com.flamelab.shopserver.entities.User;
import com.flamelab.shopserver.enums.Roles;

import java.util.List;

public interface AuthService {

    AuthToken createToken(User user);

    AuthToken validateTokenAndReturn(String token, List<Roles> availableRoles);

    boolean isTokenExists(String email);

    void deleteTokenByValue(String token);

    void deleteTokenByEmail(String email);

}
