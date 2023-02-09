package com.flamelab.shopserver.services;

import com.flamelab.shopserver.dtos.create.external.CreateUserDto;
import com.flamelab.shopserver.dtos.update.UpdateUserDto;
import com.flamelab.shopserver.dtos.update.UpdateUserPasswordDto;
import com.flamelab.shopserver.entities.User;
import com.flamelab.shopserver.enums.ProductName;
import org.bson.types.ObjectId;

public interface UserService extends CommonService<CreateUserDto, User, UpdateUserDto> {

    User addWalletToUser(ObjectId userId, ObjectId walletId);

    User addProductsToTheBucket(ObjectId userId, ProductName productName, double price, int amount);

    void updateUserPassword(UpdateUserPasswordDto updateUserPasswordDto);
}
