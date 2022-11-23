package com.flamelab.shopserver.services;

import com.flamelab.shopserver.dtos.create.CreateUserDto;
import com.flamelab.shopserver.dtos.transafer.TransferUserDto;
import com.flamelab.shopserver.dtos.update.UpdateUserDto;
import com.flamelab.shopserver.enums.ProductName;
import com.flamelab.shopserver.utiles.naming.FieldNames;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.Map;

public interface UsersService {

    TransferUserDto createUser(CreateUserDto createUserDto);

    TransferUserDto getUserById(ObjectId userId);

    Object getUserBy(Map<FieldNames, Object> criterias);

    List<TransferUserDto> getAllUsers();

    TransferUserDto updateUserById(ObjectId userId, UpdateUserDto updateUserDto);

    TransferUserDto addWalletToUser(ObjectId userId, ObjectId walletId);

    TransferUserDto addProductsToTheBucket(ObjectId userId, ProductName productName, double price, int amount);

    void deleteUser(ObjectId userId);
}
