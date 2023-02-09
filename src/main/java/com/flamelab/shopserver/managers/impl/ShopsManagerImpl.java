package com.flamelab.shopserver.managers.impl;

import com.flamelab.shopserver.dtos.create.external.CreateShopDto;
import com.flamelab.shopserver.dtos.create.external.CreateWalletDto;
import com.flamelab.shopserver.dtos.create.internal.InternalCreateShop;
import com.flamelab.shopserver.dtos.transafer.TransferShopDto;
import com.flamelab.shopserver.dtos.transafer.TransferWalletDto;
import com.flamelab.shopserver.dtos.update.UpdateShopDto;
import com.flamelab.shopserver.entities.AuthToken;
import com.flamelab.shopserver.entities.Shop;
import com.flamelab.shopserver.entities.Wallet;
import com.flamelab.shopserver.enums.ProductName;
import com.flamelab.shopserver.exceptions.ResourceException;
import com.flamelab.shopserver.exceptions.ShopHasNotEnoughMoneyException;
import com.flamelab.shopserver.internal_data.Product;
import com.flamelab.shopserver.managers.ShopsManager;
import com.flamelab.shopserver.services.AuthService;
import com.flamelab.shopserver.services.ShopsService;
import com.flamelab.shopserver.services.WalletsService;
import com.flamelab.shopserver.utiles.MapperUtility;
import com.flamelab.shopserver.utiles.naming.FieldNames;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static com.flamelab.shopserver.enums.AmountActionType.DECREASE;
import static com.flamelab.shopserver.enums.OwnerType.SHOP;
import static com.flamelab.shopserver.enums.Roles.CUSTOMER;
import static com.flamelab.shopserver.utiles.naming.FieldNames.TOKEN__FIELD_APPELLATION;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Service
@RequiredArgsConstructor
public class ShopsManagerImpl implements ShopsManager {

    private final ShopsService shopsService;
    private final WalletsService walletsService;
    private final AuthService authService;
    private final MapperUtility<Shop, TransferShopDto> shopMapperFromEntityToTransferDto;
    private final MapperUtility<Wallet, TransferWalletDto> walletMapperFromEntityToTransferDto;

    @Override
    public TransferShopDto createShop(CreateShopDto createShopDto, String authorization) {
        int shopCapitalOnOpening = 1000;
        Shop shop = shopsService.createEntity(provideInternalCreateShop(createShopDto, authorization));
        Wallet wallet = walletsService.createEntity(new CreateWalletDto(shop.getId(), SHOP, shopCapitalOnOpening));
        return shopMapperFromEntityToTransferDto.map(
                shopsService.addWalletToShop(shop.getId(), wallet.getId()),
                Shop.class,
                TransferShopDto.class
        );
    }

    private InternalCreateShop provideInternalCreateShop(CreateShopDto createShopDto, String authorization) {
        AuthToken token = authService.getToken(Map.of(TOKEN__FIELD_APPELLATION, authorization));
        InternalCreateShop internalCreateShop = new InternalCreateShop();
        internalCreateShop.setName(createShopDto.getName());
        internalCreateShop.setOwnerId(new ObjectId(token.getUserId()));
        return internalCreateShop;
    }

    @Override
    public TransferShopDto getShopById(ObjectId shopId) {
        return shopMapperFromEntityToTransferDto.map(
                shopsService.getEntityById(shopId),
                Shop.class,
                TransferShopDto.class
        );
    }

    @Override
    public TransferShopDto getShopBy(Map<FieldNames, Object> criterias) {
        return shopMapperFromEntityToTransferDto.map(
                shopsService.getEntityByCriterias(criterias),
                Shop.class,
                TransferShopDto.class
        );
    }

    @Override
    public List<TransferShopDto> getAllShops() {
        return shopMapperFromEntityToTransferDto.mapToList(
                shopsService.getAllEntities(),
                Shop.class,
                TransferShopDto.class
        );
    }

    @Override
    public List<TransferShopDto> getAllShopsByCriterias(Map<FieldNames, Object> criterias) {
        return shopMapperFromEntityToTransferDto.mapToList(
                shopsService.getAllEntitiesByCriterias(criterias),
                Shop.class,
                TransferShopDto.class
        );
    }

    @Override
    public List<Product> getAllProductsInTheShop(ObjectId shopId) {
        return shopsService.getAllProductsInTheShop(shopId);
    }

    @Override
    public TransferWalletDto getShopWallet(ObjectId shopId) {
        return walletMapperFromEntityToTransferDto.map(
                walletsService.getWalletByOwnerId(shopId),
                Wallet.class,
                TransferWalletDto.class
        );
    }

    @Override
    public TransferShopDto updateShopData(ObjectId shopId, UpdateShopDto updateShopDto) {
        return shopMapperFromEntityToTransferDto.map(
                shopsService.updateEntityById(shopId, updateShopDto),
                Shop.class,
                TransferShopDto.class
        );
    }

    @Override
    public TransferShopDto buyProductsFromTheStock(ObjectId shopId, ProductName productName, double price, int amount) {
        Shop shop = shopsService.getEntityById(shopId);
        Product product;
        boolean isShopContainsProduct = shopsService.isShopContainsProduct(shopId, productName);
        if (isShopContainsProduct) {
            product = shopsService.getProductData(shopId, productName);
        } else {
            product = new Product(productName, price, amount);
        }
        boolean isWalletAmountEnough = walletsService.isWalletHasEnoughAmountByOwnerId(shopId, amount);
        if (!isWalletAmountEnough) {
            throw new ShopHasNotEnoughMoneyException(String.format("The shop with id '%s' has money less then '%s' and can't do the payment", shopId, amount));
        }
        walletsService.updateWalletAmount(shop.getWalletId(), DECREASE, product.getAmount() * amount);
        return shopMapperFromEntityToTransferDto.map(
                shopsService.addProductsToTheStore(shopId, isShopContainsProduct, productName, price, amount),
                Shop.class,
                TransferShopDto.class
        );
    }

    @Override
    public TransferShopDto setProductPrice(ObjectId shopId, ProductName productName, double price) {
        return shopMapperFromEntityToTransferDto.map(
                shopsService.setProductPrice(shopId, productName, price),
                Shop.class,
                TransferShopDto.class
        );
    }

    @Override
    public void deleteShop(ObjectId shopId, String authorization) {
        AuthToken token = authService.getToken(Map.of(TOKEN__FIELD_APPELLATION, authorization));
        TransferShopDto shop = getShopById(shopId);
        if (token.getRole().equals(CUSTOMER)) {
            if (!shop.getOwnerId().equals(token.getUserId())) {
                throw new ResourceException(BAD_REQUEST, "User can't delete other user");
            }
        }
        shopsService.deleteEntityById(shopId);
        walletsService.deleteEntityById(shop.getWalletId());
    }
}
