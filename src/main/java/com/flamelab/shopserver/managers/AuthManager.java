package com.flamelab.shopserver.managers;

import com.flamelab.shopserver.dtos.create.CreateAuthTokenDto;
import com.flamelab.shopserver.dtos.transfer.TransferAuthTokenDto;
import com.flamelab.shopserver.enums.Roles;

import java.util.List;

public interface AuthManager {

    TransferAuthTokenDto login(CreateAuthTokenDto createAuthTokenDto);

    void logout(TransferAuthTokenDto authToken);

    TransferAuthTokenDto validateAuthToken(String token, List<Roles> availableRoles);

}
