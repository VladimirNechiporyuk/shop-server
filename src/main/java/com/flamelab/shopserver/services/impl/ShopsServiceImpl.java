package com.flamelab.shopserver.services.impl;

import com.flamelab.shopserver.dtos.create.CreateShopDto;
import com.flamelab.shopserver.dtos.transafer.TransferShopDto;
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

    private final MapperUtility<Shop, TransferShopDto> mapperFromEntityToTransferDto;
    private final MapperUtility<Shop, UpdateShopDto> mapperFromEntityToUpdateDto;
    private final DbEntityUtility<Shop, CreateShopDto, UpdateShopDto> dbEntityUtility;

    @Override
    public TransferShopDto createEntity(CreateShopDto createEntity) {
        return mapperFromEntityToTransferDto.map(
                dbEntityUtility.saveEntity(createEntity, CreateShopDto.class, Shop.class, SHOPS__DB_COLLECTION),
                Shop.class,
                TransferShopDto.class
        );
    }

    @Override
    public TransferShopDto getEntityById(ObjectId entityId) {
        return mapperFromEntityToTransferDto.map(
                fetchShopById(entityId),
                Shop.class,
                TransferShopDto.class
        );
    }

    @Override
    public TransferShopDto getEntityByCriterias(Map<FieldNames, Object> criterias) {
        return mapperFromEntityToTransferDto.map(
                fetchShopBy(criterias),
                Shop.class,
                TransferShopDto.class
        );
    }

    @Override
    public List<TransferShopDto> getAllEntities() {
        return mapperFromEntityToTransferDto.mapToList(
                dbEntityUtility.findAllByClass(Shop.class, SHOPS__DB_COLLECTION),
                Shop.class,
                TransferShopDto.class
        );
    }

    @Override
    public boolean isEntityExistsByCriterias(Map<FieldNames, Object> criterias) {
        return dbEntityUtility.isEntityExistsBy(criterias, Shop.class, SHOPS__DB_COLLECTION);
    }

    @Override
    public TransferShopDto updateEntityById(ObjectId entityId, UpdateShopDto dtoWithNewData) {
        return mapperFromEntityToTransferDto.map(
                dbEntityUtility.updateEntity(Map.of(ID__FIELD_APPELLATION, entityId), dtoWithNewData, UpdateShopDto.class, Shop.class, SHOPS__DB_COLLECTION),
                Shop.class,
                TransferShopDto.class
        );
    }

    @Override
    public TransferShopDto updateEntityBy(Map<FieldNames, Object> criterias, UpdateShopDto dtoWithNewData) {
        return mapperFromEntityToTransferDto.map(
                dbEntityUtility.updateEntity(criterias, dtoWithNewData, UpdateShopDto.class, Shop.class, SHOPS__DB_COLLECTION),
                Shop.class,
                TransferShopDto.class
        );
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
    public TransferShopDto addWalletToShop(ObjectId shopId, ObjectId walletId) {
        Map<FieldNames, Object> searchCriterias = Map.of(ID__FIELD_APPELLATION, shopId);
        Shop shop = fetchShopById(shopId);
        shop.setWalletId(walletId);
        UpdateShopDto dtoWithNewData = mapperFromEntityToUpdateDto.map(shop, Shop.class, UpdateShopDto.class);
        return mapperFromEntityToTransferDto.map(
                dbEntityUtility.updateEntity(searchCriterias, dtoWithNewData, UpdateShopDto.class, Shop.class, SHOPS__DB_COLLECTION),
                Shop.class,
                TransferShopDto.class
        );
    }

    @Override
    public TransferShopDto getProductsFromTheStock(ObjectId shopId, ProductName productName, double price, int amount) {
        Map<FieldNames, Object> searchCriterias = Map.of(ID__FIELD_APPELLATION, shopId);
        Shop shop = fetchShopBy(searchCriterias);
        Product product = fetchProductInShop(shop, productName);
//        shop.getProducts().remove(product);
        int productResultAmount = product.getAmount() + amount;
        product.setAmount(productResultAmount);
        product.setPrice(price);
//        shop.getProducts().add(product);
        UpdateShopDto dtoWithNewData = mapperFromEntityToUpdateDto.map(shop, Shop.class, UpdateShopDto.class);
        return mapperFromEntityToTransferDto.map(
                dbEntityUtility.updateEntity(searchCriterias, dtoWithNewData, UpdateShopDto.class, Shop.class, SHOPS__DB_COLLECTION),
                Shop.class,
                TransferShopDto.class);
    }

    @Override
    public TransferShopDto decreaseProductsAmount(ObjectId shopId, ProductName productName, int amount) {
        Map<FieldNames, Object> searchCriterias = Map.of(ID__FIELD_APPELLATION, shopId);
        Shop shop = fetchShopBy(searchCriterias);
        Product product = fetchProductInShop(shop, productName);
//        shop.getProducts().remove(product);
        int productResultAmount = product.getAmount() - amount;
        product.setAmount(productResultAmount);
//        shop.getProducts().add(product);
        UpdateShopDto dtoWithNewData = mapperFromEntityToUpdateDto.map(shop, Shop.class, UpdateShopDto.class);
        return mapperFromEntityToTransferDto.map(
                dbEntityUtility.updateEntity(searchCriterias, dtoWithNewData, UpdateShopDto.class, Shop.class, SHOPS__DB_COLLECTION),
                Shop.class,
                TransferShopDto.class);
    }

    @Override
    public TransferShopDto setProductPrice(ObjectId shopId, ProductName productName, double price) {
        Map<FieldNames, Object> searchCriterias = Map.of(ID__FIELD_APPELLATION, shopId);
        Shop shop = fetchShopBy(searchCriterias);
        Product product = fetchProductInShop(shop, productName);
//        shop.getProducts().remove(product);
        product.setPrice(price);
//        shop.getProducts().add(product);
        UpdateShopDto dtoWithNewData = mapperFromEntityToUpdateDto.map(shop, Shop.class, UpdateShopDto.class);
        return mapperFromEntityToTransferDto.map(
                dbEntityUtility.updateEntity(searchCriterias, dtoWithNewData, UpdateShopDto.class, Shop.class, SHOPS__DB_COLLECTION),
                Shop.class,
                TransferShopDto.class);
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
