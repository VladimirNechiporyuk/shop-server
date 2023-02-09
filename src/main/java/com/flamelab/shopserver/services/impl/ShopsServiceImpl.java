package com.flamelab.shopserver.services.impl;

import com.flamelab.shopserver.dtos.create.external.CreateShopDto;
import com.flamelab.shopserver.dtos.create.internal.InternalCreateShop;
import com.flamelab.shopserver.dtos.update.UpdateShopDto;
import com.flamelab.shopserver.entities.Shop;
import com.flamelab.shopserver.enums.ProductName;
import com.flamelab.shopserver.exceptions.NoProductException;
import com.flamelab.shopserver.internal_data.Product;
import com.flamelab.shopserver.services.ShopsService;
import com.flamelab.shopserver.utiles.DbEntityUtility;
import com.flamelab.shopserver.utiles.MapperUtility;
import com.flamelab.shopserver.utiles.naming.FieldNames;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.flamelab.shopserver.utiles.naming.DbCollectionNames.SHOPS__DB_COLLECTION;
import static com.flamelab.shopserver.utiles.naming.FieldNames.ID__FIELD_APPELLATION;

@Service
@RequiredArgsConstructor
public class ShopsServiceImpl implements ShopsService {

    private final MapperUtility<Shop, UpdateShopDto> mapperFromEntityToUpdateDto;
    private final DbEntityUtility<Shop, InternalCreateShop, UpdateShopDto> dbEntityUtility;

    @Override
    public Shop createEntity(InternalCreateShop createEntity) {
        return dbEntityUtility.saveEntity(createEntity, InternalCreateShop.class, Shop.class, SHOPS__DB_COLLECTION);
    }

    @Override
    public Shop getEntityById(ObjectId entityId) {
        return fetchShopById(entityId);
    }

    @Override
    public Shop getEntityByCriterias(Map<FieldNames, Object> criterias) {
        return fetchShopBy(criterias);
    }

    @Override
    public List<Shop> getAllEntitiesByCriterias(Map<FieldNames, Object> criterias) {
        return dbEntityUtility.findAllBy(criterias, Shop.class, SHOPS__DB_COLLECTION);
    }

    @Override
    public List<Shop> getAllEntities() {
        return dbEntityUtility.findAllByClass(Shop.class, SHOPS__DB_COLLECTION);
    }

    @Override
    public boolean isEntityExistsByCriterias(Map<FieldNames, Object> criterias) {
        return dbEntityUtility.isEntityExistsBy(criterias, Shop.class, SHOPS__DB_COLLECTION);
    }

    @Override
    public Shop updateEntityById(ObjectId entityId, UpdateShopDto dtoWithNewData) {
        return dbEntityUtility.updateEntity(Map.of(ID__FIELD_APPELLATION, entityId), dtoWithNewData, UpdateShopDto.class, Shop.class, SHOPS__DB_COLLECTION);
    }

    @Override
    public Shop updateEntityBy(Map<FieldNames, Object> criterias, UpdateShopDto dtoWithNewData) {
        return dbEntityUtility.updateEntity(criterias, dtoWithNewData, UpdateShopDto.class, Shop.class, SHOPS__DB_COLLECTION);
    }

    @Override
    public void deleteEntityById(ObjectId entityId) {
        dbEntityUtility.deleteEntityBy(Map.of(ID__FIELD_APPELLATION, entityId), Shop.class, SHOPS__DB_COLLECTION);
    }

    @Override
    public void deleteEntityByCriterias(Map<FieldNames, Object> criterias) {
        dbEntityUtility.deleteEntityBy(criterias, Shop.class, SHOPS__DB_COLLECTION);
    }

    @Override
    public List<Product> getAllProductsInTheShop(ObjectId shopId) {
        Shop shop = fetchShopBy(Map.of(ID__FIELD_APPELLATION, shopId));
        return shop.getProducts();
    }

    @Override
    public Product getProductData(ObjectId shopId, ProductName productName) {
        Shop shop = fetchShopBy(Map.of(ID__FIELD_APPELLATION, shopId));
        return fetchProductInShop(shop, productName);
    }

    @Override
    public boolean isShopContainsProduct(ObjectId shopId, ProductName productName) {
        Shop shop = fetchShopBy(Map.of(ID__FIELD_APPELLATION, shopId));
        for (Product product : shop.getProducts()) {
            if (product.getName().equals(productName)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Shop addWalletToShop(ObjectId shopId, ObjectId walletId) {
        Map<FieldNames, Object> searchCriterias = Map.of(ID__FIELD_APPELLATION, shopId);
        Shop shop = fetchShopById(shopId);
        shop.setWalletId(walletId);
        UpdateShopDto dtoWithNewData = mapperFromEntityToUpdateDto.map(shop, Shop.class, UpdateShopDto.class);
        return dbEntityUtility.updateEntity(searchCriterias, dtoWithNewData, UpdateShopDto.class, Shop.class, SHOPS__DB_COLLECTION);
    }

    @Override
    public Shop addProductsToTheStore(ObjectId shopId, boolean isShopContainsProduct, ProductName productName, double price, int amount) {
        Map<FieldNames, Object> searchCriterias = Map.of(ID__FIELD_APPELLATION, shopId);
        Shop shop = fetchShopBy(searchCriterias);
        Product product;
        if (isShopContainsProduct) {
            product = fetchProductInShop(shop, productName);
            shop.getProducts().remove(product);
            int productResultAmount = product.getAmount() + amount;
            product.setAmount(productResultAmount);
            product.setPrice(price);
        } else {
            product = new Product(productName, price, amount);
        }
        shop.getProducts().add(product);
        UpdateShopDto dtoWithNewData = mapperFromEntityToUpdateDto.map(shop, Shop.class, UpdateShopDto.class);
        return dbEntityUtility.updateEntity(searchCriterias, dtoWithNewData, UpdateShopDto.class, Shop.class, SHOPS__DB_COLLECTION);
    }

    @Override
    public Shop decreaseProductsAmount(ObjectId shopId, ProductName productName, int amount) {
        Map<FieldNames, Object> searchCriterias = Map.of(ID__FIELD_APPELLATION, shopId);
        Shop shop = fetchShopBy(searchCriterias);
        Product product = fetchProductInShop(shop, productName);
//        shop.getProducts().remove(product);
        int productResultAmount = product.getAmount() - amount;
        product.setAmount(productResultAmount);
//        shop.getProducts().add(product);
        UpdateShopDto dtoWithNewData = mapperFromEntityToUpdateDto.map(shop, Shop.class, UpdateShopDto.class);
        return dbEntityUtility.updateEntity(searchCriterias, dtoWithNewData, UpdateShopDto.class, Shop.class, SHOPS__DB_COLLECTION);
    }

    @Override
    public Shop setProductPrice(ObjectId shopId, ProductName productName, double price) {
        Map<FieldNames, Object> searchCriterias = Map.of(ID__FIELD_APPELLATION, shopId);
        Shop shop = fetchShopBy(searchCriterias);
        Product product = fetchProductInShop(shop, productName);
//        shop.getProducts().remove(product);
        product.setPrice(price);
//        shop.getProducts().add(product);
        UpdateShopDto dtoWithNewData = mapperFromEntityToUpdateDto.map(shop, Shop.class, UpdateShopDto.class);
        return dbEntityUtility.updateEntity(searchCriterias, dtoWithNewData, UpdateShopDto.class, Shop.class, SHOPS__DB_COLLECTION);
    }

    private Shop fetchShopById(ObjectId shopId) {
        return dbEntityUtility.findOneBy(Map.of(ID__FIELD_APPELLATION, shopId), Shop.class, SHOPS__DB_COLLECTION);
    }

    private Shop fetchShopBy(Map<FieldNames, Object> criterias) {
        return dbEntityUtility.findOneBy(criterias, Shop.class, SHOPS__DB_COLLECTION);
    }

    private Product fetchProductInShop(Shop shop, ProductName productName) {
        Optional<Product> productOptional = shop.getProducts()
                .stream()
                .filter(product -> product.getName().equals(productName))
                .findFirst();
        if (productOptional.isPresent()) {
            return productOptional.get();
        } else {
            throw new NoProductException(String.format("The shop with id '%s' has no products with name '%s'", shop.getId(), productName));
        }
    }
}
