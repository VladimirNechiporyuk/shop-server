package com.flamelab.shopserver.services.impl;

import com.flamelab.shopserver.dtos.create.CreateUserDto;
import com.flamelab.shopserver.dtos.transafer.TransferUserDto;
import com.flamelab.shopserver.dtos.update.UpdateUserDto;
import com.flamelab.shopserver.entities.User;
import com.flamelab.shopserver.enums.ProductName;
import com.flamelab.shopserver.internal_data.Product;
import com.flamelab.shopserver.services.UsersService;
import com.flamelab.shopserver.utiles.DbEntityUtility;
import com.flamelab.shopserver.utiles.DifferenceUtility;
import com.flamelab.shopserver.utiles.EntityBuilder;
import com.flamelab.shopserver.utiles.MapperUtility;
import com.flamelab.shopserver.utiles.naming.FieldNames;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.flamelab.shopserver.utiles.naming.DbCollectionNames.USERS__DB_COLLECTION;
import static com.flamelab.shopserver.utiles.naming.FieldNames.*;

@Service
@RequiredArgsConstructor
public class UsersServiceImpl implements UsersService {

    private final MapperUtility<User, TransferUserDto> mapperFromEntityToTransferDto;
    private final MapperUtility<UpdateUserDto, User> mapperFromUpdateDtoToEntity;
    private final DbEntityUtility<User> dbEntityUtility;
    private final EntityBuilder<User, CreateUserDto> entityBuilder;
    private final DifferenceUtility<User> differenceUtility;

    @Override
    public TransferUserDto createUser(CreateUserDto createUserDto) {
        User user = entityBuilder.buildEntityFromDto(createUserDto, CreateUserDto.class, User.class);
        user.setBasket(new ArrayList<>());
        return mapperFromEntityToTransferDto.map(
                dbEntityUtility.saveEntity(user, User.class, USERS__DB_COLLECTION),
                User.class,
                TransferUserDto.class
        );
    }

    @Override
    public TransferUserDto getUserById(ObjectId userId) {
        return mapperFromEntityToTransferDto.map(
                fetchUserById(userId),
                User.class,
                TransferUserDto.class
        );
    }

    @Override
    public Object getUserBy(Map<FieldNames, Object> criterias) {
        return mapperFromEntityToTransferDto.map(
                dbEntityUtility.findOneBy(criterias, User.class, USERS__DB_COLLECTION),
                User.class,
                TransferUserDto.class
        );
    }

    @Override
    public List<TransferUserDto> getAllUsers() {
        return mapperFromEntityToTransferDto.mapToList(
                dbEntityUtility.findAllByClass(User.class, USERS__DB_COLLECTION),
                User.class,
                TransferUserDto.class
        );
    }

    @Override
    public TransferUserDto updateUserData(ObjectId userId, UpdateUserDto updateUserDto) {
        User existingUser = fetchUserById(userId);
        User userWithNewData = mapperFromUpdateDtoToEntity.map(updateUserDto, UpdateUserDto.class, User.class);
        Map<FieldNames, Object> changes = differenceUtility.getChanges(existingUser, userWithNewData, User.class);
        return mapperFromEntityToTransferDto.map(
                dbEntityUtility.updateEntity(existingUser, User.class, changes, USERS__DB_COLLECTION),
                User.class,
                TransferUserDto.class
        );
    }

    @Override
    public TransferUserDto addWalletToUser(ObjectId userId, ObjectId walletId) {
        User user = fetchUserById(userId);
        Map<FieldNames, Object> changes = Map.of(WALLET_ID__FIELD_APPELLATION, walletId);
        return mapperFromEntityToTransferDto.map(
                dbEntityUtility.updateEntity(user, User.class, changes, USERS__DB_COLLECTION),
                User.class,
                TransferUserDto.class
        );
    }

    @Override
    public TransferUserDto addProductsToTheBucket(ObjectId userId, ProductName productName, double price, int amount) {
        User user = fetchUserById(userId);
        List<Product> basket = user.getBasket();
        addProductToTheBucket(basket, productName, price, amount);
        Map<FieldNames, Object> changes = Map.of(BASKET__FIELD_APPELLATION, basket);
        return mapperFromEntityToTransferDto.map(
                dbEntityUtility.updateEntity(user, User.class, changes, USERS__DB_COLLECTION),
                User.class,
                TransferUserDto.class
        );
    }

    @Override
    public void deleteUser(ObjectId userId) {
        dbEntityUtility.deleteEntityBy(Map.of(ID__FIELD_APPELLATION, userId), User.class, USERS__DB_COLLECTION);
    }

    private User fetchUserById(ObjectId userId) {
        return dbEntityUtility.findOneBy(Map.of(ID__FIELD_APPELLATION, userId), User.class, USERS__DB_COLLECTION);
    }

    private void addProductToTheBucket(List<Product> basket, ProductName productName, double price, int amount) {
        if (isBasketContainsProduct(basket, productName)) {
            for (Product product : basket) {
                if (product.getName().equals(productName)) {
                    product.setAmount(product.getAmount() + amount);
                }
            }
        } else {
            basket.add(new Product(productName, price, amount));
        }
    }

    private boolean isBasketContainsProduct(List<Product> basket, ProductName productName) {
        for (Product product : basket) {
            if (product.getName().equals(productName)) {
                return true;
            }
        }
        return false;
    }

}
