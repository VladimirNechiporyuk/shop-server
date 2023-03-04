package com.flamelab.shopserver.managers;

import com.flamelab.shopserver.dtos.create.external.CreateUserAuthToken;
import com.flamelab.shopserver.dtos.transfer.TransferAuthTokenDto;
import com.flamelab.shopserver.enums.Roles;

import java.util.List;

public interface AuthManager {

    TransferAuthTokenDto login(CreateUserAuthToken createUserAuthToken);

    void logout(String authorization);

    TransferAuthTokenDto validateAuthToken(String token, List<Roles> availableRoles);

}
