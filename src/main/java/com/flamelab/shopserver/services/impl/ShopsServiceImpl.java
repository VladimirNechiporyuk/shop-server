package com.flamelab.shopserver.services.impl;

import com.flamelab.shopserver.dtos.create.CreateShopDto;
import com.flamelab.shopserver.dtos.create.CreateWalletDto;
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
    private final DbEntityUtility<Shop, CreateShopDto, UpdateShopDto> dbEntityUtility;

    @Override
    public TransferShopDto createEntity(CreateWalletDto createEntity) {
        return null;
    }

    @Override
    public TransferShopDto getEntityById(ObjectId entityId) {
        return null;
    }

    @Override
    public TransferShopDto getEntityByCriterias(Map<FieldNames, Object> criterias) {
        return null;
    }

    @Override
    public List<TransferShopDto> getAllEntities() {
        return null;
    }

    @Override
    public boolean isEntityExistsByCriterias(Map<FieldNames, Object> criterias) {
        return false;
    }

    @Override
    public TransferShopDto updateEntityBy(Map<FieldNames, Object> criterias, UpdateShopDto dtoWithNewData) {
        return null;
    }

    @Override
    public void deleteEntityByCriterias(Map<FieldNames, Object> criterias) {

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
        return null;
    }

    @Override
    public TransferShopDto getProductsFromTheStock(ObjectId shopId, ProductName productName, double price, int amount) {
        Shop shop = fetchShopBy(Map.of(ID__FIELD_APPELLATION, shopId));
        Product product = fetchProductInShop(shop, productName);
        shop.getProducts().remove(product);
        int productResultAmount = product.getAmount() + amount;
        product.setAmount(productResultAmount);
        product.setPrice(price);
        shop.getProducts().add(product);
        return mapperFromEntityToTransferDto.map(shop, Shop.class, TransferShopDto.class);
    }

    @Override
    public void decreaseProductsAmount(ObjectId shopId, ProductName productName, int amount) {
        Shop shop = fetchShopBy(Map.of(ID__FIELD_APPELLATION, shopId));
        Product product = fetchProductInShop(shop, productName);
        shop.getProducts().remove(product);
        int productResultAmount = product.getAmount() - amount;
        product.setAmount(productResultAmount);
        shop.getProducts().add(product);
        mapperFromEntityToTransferDto.map(shop, Shop.class, TransferShopDto.class);
    }

    @Override
    public TransferShopDto setProductPrice(ObjectId shopId, ProductName productName, double price) {
        Shop shop = fetchShopBy(Map.of(ID__FIELD_APPELLATION, shopId));
        Product product = fetchProductInShop(shop, productName);
        shop.getProducts().remove(product);
        product.setPrice(price);
        shop.getProducts().add(product);
        return mapperFromEntityToTransferDto.map(shop, Shop.class, TransferShopDto.class);
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
