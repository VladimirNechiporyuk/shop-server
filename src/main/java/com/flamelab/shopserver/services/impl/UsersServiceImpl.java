package com.flamelab.shopserver.services.impl;

import com.flamelab.shopserver.dtos.create.CreateUserDto;
import com.flamelab.shopserver.dtos.transafer.TransferUserDto;
import com.flamelab.shopserver.dtos.update.UpdateUserDto;
import com.flamelab.shopserver.entities.User;
import com.flamelab.shopserver.enums.ProductName;
import com.flamelab.shopserver.internal_data.Product;
import com.flamelab.shopserver.services.UsersService;
import com.flamelab.shopserver.utiles.DbEntityUtility;
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
import static com.flamelab.shopserver.utiles.naming.FieldNames.ID__FIELD_APPELLATION;
import static com.flamelab.shopserver.utiles.naming.FieldNames.WALLET_ID__FIELD_APPELLATION;

@Service
@RequiredArgsConstructor
public class UsersServiceImpl implements UsersService {

    private final MapperUtility<User, TransferUserDto> mapperFromEntityToTransferDto;
    private final MapperUtility<User, UpdateUserDto> mapperFromEntityToUpdateDto;
    private final DbEntityUtility<User, CreateUserDto, UpdateUserDto> dbEntityUtility;

    @Override
    public TransferUserDto createEntity(CreateUserDto createEntity) {
        return mapperFromEntityToTransferDto.map(
                dbEntityUtility.saveEntity(createEntity, CreateUserDto.class, User.class, USERS__DB_COLLECTION),
                User.class,
                TransferUserDto.class
        );
    }

    @Override
    public TransferUserDto getEntityById(ObjectId entityId) {
        return mapperFromEntityToTransferDto.map(
                fetchUserById(entityId),
                User.class,
                TransferUserDto.class
        );
    }

    @Override
    public TransferUserDto getEntityByCriterias(Map<FieldNames, Object> criterias) {
        return mapperFromEntityToTransferDto.map(
                dbEntityUtility.findOneByOrThrow(criterias, User.class, USERS__DB_COLLECTION),
                User.class,
                TransferUserDto.class
        );
    }

    @Override
    public List<TransferUserDto> getAllEntitiesByCriterias(Map<FieldNames, Object> criterias) {
        return mapperFromEntityToTransferDto.mapToList(
                dbEntityUtility.findAllBy(criterias, User.class, USERS__DB_COLLECTION),
                User.class,
                TransferUserDto.class
        );
    }

    @Override
    public List<TransferUserDto> getAllEntities() {
        return mapperFromEntityToTransferDto.mapToList(
                dbEntityUtility.findAllByClass(User.class, USERS__DB_COLLECTION),
                User.class,
                TransferUserDto.class
        );
    }

    @Override
    public boolean isEntityExistsByCriterias(Map<FieldNames, Object> criterias) {
        return dbEntityUtility.isEntityExistsBy(criterias, User.class, USERS__DB_COLLECTION);
    }

    @Override
    public TransferUserDto updateEntityById(ObjectId entityId, UpdateUserDto dtoWithNewData) {
        return mapperFromEntityToTransferDto.map(
                dbEntityUtility.updateEntity(Map.of(ID__FIELD_APPELLATION, entityId), dtoWithNewData, UpdateUserDto.class, User.class, USERS__DB_COLLECTION),
                User.class,
                TransferUserDto.class
        );
    }

    @Override
    public TransferUserDto updateEntityBy(Map<FieldNames, Object> criterias, UpdateUserDto dtoWithNewData) {
        return mapperFromEntityToTransferDto.map(
                dbEntityUtility.updateEntity(criterias, dtoWithNewData, UpdateUserDto.class, User.class, USERS__DB_COLLECTION),
                User.class,
                TransferUserDto.class
        );
    }

    @Override
    public void deleteEntityById(ObjectId entityId) {
        dbEntityUtility.deleteEntityBy(Map.of(ID__FIELD_APPELLATION, entityId), User.class, USERS__DB_COLLECTION);
    }

    @Override
    public TransferUserDto addWalletToUser(ObjectId userId, ObjectId walletId) {
        Map<FieldNames, Object> searchCriterias = Map.of(ID__FIELD_APPELLATION, userId);
        User user = fetchUserById(userId);
        user.setWalletId(walletId);
        UpdateUserDto updateUserDto = mapperFromEntityToUpdateDto.map(user, User.class, UpdateUserDto.class);
        return mapperFromEntityToTransferDto.map(
                dbEntityUtility.updateEntity(searchCriterias, updateUserDto, UpdateUserDto.class, User.class, USERS__DB_COLLECTION),
                User.class,
                TransferUserDto.class
        );
    }

    @Override
    public TransferUserDto addProductsToTheBucket(ObjectId userId, ProductName productName, double price, int amount) {
        Map<FieldNames, Object> searchCriterias = Map.of(ID__FIELD_APPELLATION, userId);
        User user = fetchUserById(userId);
        addProductToTheBucket(user, productName, price, amount);
        UpdateUserDto updateUserDto = mapperFromEntityToUpdateDto.map(user, User.class, UpdateUserDto.class);
        return mapperFromEntityToTransferDto.map(
                dbEntityUtility.updateEntity(searchCriterias, updateUserDto, UpdateUserDto.class, User.class, USERS__DB_COLLECTION),
                User.class,
                TransferUserDto.class
        );
    }

    @Override
    public void deleteEntityByCriterias(Map<FieldNames, Object> criterias) {
        dbEntityUtility.deleteEntityBy(criterias, User.class, USERS__DB_COLLECTION);
    }

    private User fetchUserById(ObjectId userId) {
        return dbEntityUtility.findOneBy(Map.of(ID__FIELD_APPELLATION, userId), User.class, USERS__DB_COLLECTION);
    }

    private void addProductToTheBucket(User user, ProductName productName, double price, int amount) {
        List<Product> basket = user.getBasket();
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
