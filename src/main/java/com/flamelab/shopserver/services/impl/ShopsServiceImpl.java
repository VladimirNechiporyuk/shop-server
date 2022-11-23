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
import java.util.Optional;

import static com.flamelab.shopserver.utiles.naming.DbCollectionNames.SHOPS__DB_COLLECTION;
import static com.flamelab.shopserver.utiles.naming.FieldNames.ID__FIELD_APPELLATION;
import static com.flamelab.shopserver.utiles.naming.FieldNames.WALLET_ID__FIELD_APPELLATION;

@Service
@RequiredArgsConstructor
public class ShopsServiceImpl implements ShopsService {

    private final MapperUtility<Shop, TransferShopDto> mapperFromEntityToTransferDto;
    private final MapperUtility<UpdateShopDto, Shop> mapperFromUpdateDtoToEntity;
    private final DbEntityUtility<Shop> dbEntityUtility;
    private final EntityBuilder<Shop, CreateShopDto> entityBuilder;
    private final DifferenceUtility<Shop> differenceUtility;

    @Override
    public TransferShopDto createShop(CreateShopDto createShopDto) {
        Shop shop = entityBuilder.buildEntityFromDto(createShopDto, CreateShopDto.class, Shop.class);
        shop.setProducts(new ArrayList<>());
        return mapperFromEntityToTransferDto.map(
                dbEntityUtility.saveEntity(shop, Shop.class, SHOPS__DB_COLLECTION),
                Shop.class,
                TransferShopDto.class
        );
    }

    @Override
    public TransferShopDto getShopById(ObjectId shopId) {
        return mapperFromEntityToTransferDto.map(
                dbEntityUtility.findOneBy(Map.of(ID__FIELD_APPELLATION, shopId), Shop.class, SHOPS__DB_COLLECTION),
                Shop.class,
                TransferShopDto.class
        );
    }

    @Override
    public List<TransferShopDto> getAllShops() {
        return mapperFromEntityToTransferDto.mapToList(
                dbEntityUtility.findAllByClass(Shop.class, SHOPS__DB_COLLECTION),
                Shop.class,
                TransferShopDto.class
        );
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
    public TransferShopDto updateShopData(ObjectId shopId, UpdateShopDto updateShopDto) {
        Map<FieldNames, Object> searchCriterias = Map.of(ID__FIELD_APPELLATION, shopId);
        Shop existingShop = fetchShopBy(Map.of(ID__FIELD_APPELLATION, shopId));
        Shop shopWithNewData = mapperFromUpdateDtoToEntity.map(updateShopDto, UpdateShopDto.class, Shop.class);
        Map<FieldNames, Object> changes = differenceUtility.getChanges(existingShop, shopWithNewData, Shop.class);
        return mapperFromEntityToTransferDto.map(
                dbEntityUtility.updateEntity(searchCriterias, Shop.class, changes, SHOPS__DB_COLLECTION),
                Shop.class,
                TransferShopDto.class
        );
    }

    @Override
    public TransferShopDto addWalletToShop(ObjectId shopId, ObjectId walletId) {
        Map<FieldNames, Object> searchCriterias = Map.of(ID__FIELD_APPELLATION, shopId);
        fetchShopBy(Map.of(ID__FIELD_APPELLATION, shopId));
        Map<FieldNames, Object> changes = Map.of(WALLET_ID__FIELD_APPELLATION, walletId);
        return mapperFromEntityToTransferDto.map(
                dbEntityUtility.updateEntity(searchCriterias, Shop.class, changes, SHOPS__DB_COLLECTION),
                Shop.class,
                TransferShopDto.class
        );
    }

    @Override
    public TransferShopDto buyProductsFromTheStock(ObjectId shopId, ProductName productName, double price, int amount) {
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
    public TransferShopDto decreaseProductsAmount(ObjectId shopId, ProductName productName, int amount) {
        Shop shop = fetchShopBy(Map.of(ID__FIELD_APPELLATION, shopId));
        Product product = fetchProductInShop(shop, productName);
        shop.getProducts().remove(product);
        int productResultAmount = product.getAmount() - amount;
        product.setAmount(productResultAmount);
        shop.getProducts().add(product);
        return mapperFromEntityToTransferDto.map(shop, Shop.class, TransferShopDto.class);
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

    @Override
    public void deleteShop(ObjectId shopId) {
        dbEntityUtility.deleteEntityBy(Map.of(ID__FIELD_APPELLATION, shopId), Shop.class, SHOPS__DB_COLLECTION);
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
