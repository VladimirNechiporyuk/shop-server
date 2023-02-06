package com.flamelab.shopserver.services;

import com.flamelab.shopserver.dtos.create.CreateUserDto;
import com.flamelab.shopserver.dtos.transafer.TransferUserDto;
import com.flamelab.shopserver.dtos.update.UpdateUserDto;
import com.flamelab.shopserver.dtos.update.UpdateUserPasswordDto;
import com.flamelab.shopserver.entities.CommonEntity;
import com.flamelab.shopserver.entities.User;
import com.flamelab.shopserver.enums.ProductName;
import com.flamelab.shopserver.utiles.naming.FieldNames;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.Map;

public interface UsersService extends CommonService<CreateUserDto, User, UpdateUserDto> {

    User addWalletToUser(ObjectId userId, ObjectId walletId);

    User addProductsToTheBucket(ObjectId userId, ProductName productName, double price, int amount);

    void updateUserPassword(UpdateUserPasswordDto updateUserPasswordDto);
}
