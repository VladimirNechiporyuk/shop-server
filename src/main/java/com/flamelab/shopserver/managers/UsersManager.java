package com.flamelab.shopserver.managers;

import com.flamelab.shopserver.dtos.create.CreateUserDto;
import com.flamelab.shopserver.dtos.transfer.*;
import com.flamelab.shopserver.dtos.update.RecoverPasswordDto;
import com.flamelab.shopserver.dtos.update.UpdateUserDto;
import com.flamelab.shopserver.dtos.update.UpdateUserPasswordDto;

import java.util.List;

public interface UsersManager {

    TransferUserDto createUser(CreateUserDto createUserDto);

    TransferUserDto createUserAdmin(TransferAuthTokenDto authToken, CreateUserDto createUserDto);

    void sendTemporaryCodeToEmail(String email);

    TransferValidationResultDto verifyTempCode(TransferTemporaryCodeDto temporaryCodeDto);

    TransferUserDto confirmRegistration(String userId, int tempCode);

    TransferUserDto activateUser(TransferAuthTokenDto validateAuthToken, String userId);

    TransferUserDto getUserById(TransferAuthTokenDto authToken, String userId);

    List<TransferUserDto> getAllUsersByTextInParameters(TransferAuthTokenDto authToken, String text);

    List<TransferUserDto> getAllUsers(TransferAuthTokenDto authToken);

    TransferUserDto updateUserData(TransferAuthTokenDto authToken, String userId, UpdateUserDto updateUserDto);

    void recoverPassword(RecoverPasswordDto recoverPasswordDto);

    void recoverPassword(TransferAuthTokenDto authToken, RecoverPasswordDto recoverPasswordDto);

    void updateUserPassword(TransferAuthTokenDto authToken, UpdateUserPasswordDto updateUserPasswordDto);

    void deleteUser(TransferAuthTokenDto authToken, String userId);
}
