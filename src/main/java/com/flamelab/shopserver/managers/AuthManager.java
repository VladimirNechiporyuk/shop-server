package com.flamelab.shopserver.managers;

import com.flamelab.shopserver.dtos.create.CreateUserAuthToken;
import com.flamelab.shopserver.dtos.transafer.TransferAuthTokenDto;
import com.flamelab.shopserver.enums.Roles;

import java.util.List;

public interface AuthManager {

    TransferAuthTokenDto login(CreateUserAuthToken createUserAuthToken);

    void isAuthorized(String token, List<Roles> availableRoles);

}
