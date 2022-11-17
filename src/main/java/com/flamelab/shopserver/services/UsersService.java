package com.flamelab.shopserver.services;

import com.flamelab.shopserver.dtos.create.CreateUserDto;
import com.flamelab.shopserver.dtos.transafer.TransferUserDto;
import com.flamelab.shopserver.dtos.update.UpdateUserDto;
import com.flamelab.shopserver.enums.ProductName;
import org.bson.types.ObjectId;

import java.util.List;

public interface UsersService {

    TransferUserDto createUser(CreateUserDto createUserDto);

    TransferUserDto getUserById(ObjectId userId);

    List<TransferUserDto> getAllUsers();

    TransferUserDto updateUserData(ObjectId userId, UpdateUserDto updateUserDto);

    TransferUserDto addWalletToUser(ObjectId userId, ObjectId walletId);

    TransferUserDto addProductsToTheBucket(ObjectId userId, ProductName productName, double price, int amount);

    void deleteUser(ObjectId userId);
}
