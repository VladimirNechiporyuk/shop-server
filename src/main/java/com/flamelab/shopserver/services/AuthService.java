package com.flamelab.shopserver.services;

import com.flamelab.shopserver.entities.AuthToken;
import com.flamelab.shopserver.entities.User;
import com.flamelab.shopserver.enums.Roles;

import java.util.List;

public interface AuthService {

    AuthToken createToken(User user);

    AuthToken validateTokenAndReturn(String token, List<Roles> availableRoles);

    AuthToken getTokenByEmail(String email);

    boolean isTokenExistsByEmail(String email);

    void deleteTokenByTokenId(String tokenId);

}
