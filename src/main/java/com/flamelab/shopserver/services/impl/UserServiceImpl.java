package com.flamelab.shopserver.services.impl;

import com.flamelab.shopserver.dtos.create.external.CreateUserDto;
import com.flamelab.shopserver.dtos.update.UpdateUserDto;
import com.flamelab.shopserver.dtos.update.UpdateUserPasswordDto;
import com.flamelab.shopserver.entities.User;
import com.flamelab.shopserver.enums.ProductName;
import com.flamelab.shopserver.internal_data.Product;
import com.flamelab.shopserver.services.UserService;
import com.flamelab.shopserver.utiles.DbEntityUtility;
import com.flamelab.shopserver.utiles.MapperUtility;
import com.flamelab.shopserver.utiles.naming.FieldNames;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static com.flamelab.shopserver.utiles.naming.DbCollectionNames.USERS__DB_COLLECTION;
import static com.flamelab.shopserver.utiles.naming.FieldNames.ID__FIELD_APPELLATION;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final MapperUtility<User, UpdateUserDto> mapperFromEntityToUpdateDto;
    private final DbEntityUtility<User, CreateUserDto, UpdateUserDto> dbEntityUtility;

    @Override
    public User createEntity(CreateUserDto createEntity) {
        return dbEntityUtility.saveEntity(createEntity, CreateUserDto.class, User.class, USERS__DB_COLLECTION);
    }

    @Override
    public User getEntityById(ObjectId entityId) {
        return fetchUserById(entityId);
    }

    @Override
    public User getEntityByCriterias(Map<FieldNames, Object> criterias) {
        return dbEntityUtility.findOneByOrThrow(criterias, User.class, USERS__DB_COLLECTION);
    }

    @Override
    public List<User> getAllEntitiesByCriterias(Map<FieldNames, Object> criterias) {
        return dbEntityUtility.findAllBy(criterias, User.class, USERS__DB_COLLECTION);
    }

    @Override
    public List<User> getAllEntities() {
        return dbEntityUtility.findAllByClass(User.class, USERS__DB_COLLECTION);
    }

    @Override
    public boolean isEntityExistsByCriterias(Map<FieldNames, Object> criterias) {
        return dbEntityUtility.isEntityExistsBy(criterias, User.class, USERS__DB_COLLECTION);
    }

    @Override
    public User updateEntityById(ObjectId entityId, UpdateUserDto dtoWithNewData) {
        return dbEntityUtility.updateEntity(Map.of(ID__FIELD_APPELLATION, entityId), dtoWithNewData, UpdateUserDto.class, User.class, USERS__DB_COLLECTION);
    }

    @Override
    public User updateEntityBy(Map<FieldNames, Object> criterias, UpdateUserDto dtoWithNewData) {
        return dbEntityUtility.updateEntity(criterias, dtoWithNewData, UpdateUserDto.class, User.class, USERS__DB_COLLECTION);
    }

    @Override
    public void deleteEntityById(ObjectId entityId) {
        dbEntityUtility.deleteEntityBy(Map.of(ID__FIELD_APPELLATION, entityId), User.class, USERS__DB_COLLECTION);
    }

    @Override
    public User addWalletToUser(ObjectId userId, ObjectId walletId) {
        Map<FieldNames, Object> searchCriterias = Map.of(ID__FIELD_APPELLATION, userId);
        User user = fetchUserById(userId);
        user.setWalletId(walletId);
        UpdateUserDto updateUserDto = mapperFromEntityToUpdateDto.map(user, User.class, UpdateUserDto.class);
        return dbEntityUtility.updateEntity(searchCriterias, updateUserDto, UpdateUserDto.class, User.class, USERS__DB_COLLECTION);
    }

    @Override
    public User addProductsToTheBucket(ObjectId userId, ProductName productName, double price, int amount) {
        Map<FieldNames, Object> searchCriterias = Map.of(ID__FIELD_APPELLATION, userId);
        User user = fetchUserById(userId);
        addProductToTheBucket(user, productName, price, amount);
        UpdateUserDto updateUserDto = mapperFromEntityToUpdateDto.map(user, User.class, UpdateUserDto.class);
        return dbEntityUtility.updateEntity(searchCriterias, updateUserDto, UpdateUserDto.class, User.class, USERS__DB_COLLECTION);
    }

    @Override
    public void updateUserPassword(UpdateUserPasswordDto updateUserPasswordDto) {

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
