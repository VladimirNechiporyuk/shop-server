package com.flamelab.shopserver.managers;

import com.flamelab.shopserver.dtos.create.external.CreateUserDto;
import com.flamelab.shopserver.dtos.transfer.TransferAuthTokenDto;
import com.flamelab.shopserver.dtos.transfer.TransferUserDto;
import com.flamelab.shopserver.dtos.transfer.TransferWalletDto;
import com.flamelab.shopserver.dtos.update.UpdateUserDto;
import com.flamelab.shopserver.dtos.update.UpdateUserPasswordDto;
import com.flamelab.shopserver.enums.ProductName;
import com.flamelab.shopserver.utiles.naming.FieldNames;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.Map;

public interface UsersManager {

    TransferUserDto createUser(CreateUserDto createUserDto);

    TransferUserDto getUserById(TransferAuthTokenDto authToken, ObjectId userId);

    List<TransferUserDto> getAllUsersByCriterias(TransferAuthTokenDto authToken, Map<FieldNames, Object> criterias);

    List<TransferUserDto> getAllUsers(TransferAuthTokenDto authToken);

    TransferWalletDto getUserWallet(TransferAuthTokenDto authToken, ObjectId userId);

    TransferUserDto updateUserData(TransferAuthTokenDto authToken, ObjectId userId, UpdateUserDto updateUserDto);

    TransferWalletDto deposit(TransferAuthTokenDto authToken, int amount);

    TransferUserDto buyProducts(TransferAuthTokenDto authToken, ObjectId shopId, ProductName productName, int amount);

    void updateUserPassword(TransferAuthTokenDto authToken, UpdateUserPasswordDto updateUserPasswordDto);

    void deleteUser(TransferAuthTokenDto authToken, ObjectId userId);
}
