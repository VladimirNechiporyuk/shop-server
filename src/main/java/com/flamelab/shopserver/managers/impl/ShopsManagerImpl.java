package com.flamelab.shopserver.managers.impl;

import com.flamelab.shopserver.dtos.create.CreateShopDto;
import com.flamelab.shopserver.dtos.create.CreateWalletDto;
import com.flamelab.shopserver.dtos.transafer.TransferShopDto;
import com.flamelab.shopserver.dtos.transafer.TransferWalletDto;
import com.flamelab.shopserver.dtos.update.UpdateShopDto;
import com.flamelab.shopserver.enums.ProductName;
import com.flamelab.shopserver.exceptions.ShopHasNotEnoughMoneyException;
import com.flamelab.shopserver.internal_data.Product;
import com.flamelab.shopserver.managers.ShopsManager;
import com.flamelab.shopserver.services.ShopsService;
import com.flamelab.shopserver.services.WalletsService;
import com.flamelab.shopserver.utiles.naming.FieldNames;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static com.flamelab.shopserver.enums.AmountActionType.DECREASE;
import static com.flamelab.shopserver.enums.AmountActionType.INCREASE;
import static com.flamelab.shopserver.enums.OwnerType.SHOP;

@Service
@RequiredArgsConstructor
public class ShopsManagerImpl implements ShopsManager {

    private final ShopsService shopsService;
    private final WalletsService walletsService;

    @Override
    public TransferShopDto createShop(CreateShopDto createShopDto) {
        int shopCapitalOnOpening = 1000;
        TransferShopDto shop = shopsService.createEntity(createShopDto);
        TransferWalletDto wallet = walletsService.createEntity(new CreateWalletDto(shop.getId(), SHOP, shopCapitalOnOpening));
        return shopsService.addWalletToShop(shop.getId(), wallet.getId());
    }

    @Override
    public TransferShopDto getShopById(ObjectId id) {
        return shopsService.getEntityById(id);
    }

    @Override
    public TransferShopDto getShopBy(Map<FieldNames, Object> criterias) {
        return shopsService.getEntityByCriterias(criterias);
    }

    @Override
    public List<TransferShopDto> getAllShops() {
        return shopsService.getAllEntities();
    }

    @Override
    public List<TransferShopDto> getAllShopsByCriterias(Map<FieldNames, Object> criterias) {
        return null;
    }

    @Override
    public List<Product> getAllProductsInTheShop(ObjectId id) {
        return shopsService.getAllProductsInTheShop(id);
    }

    @Override
    public TransferWalletDto getShopWallet(ObjectId shopId) {
        return walletsService.getWalletByOwnerId(shopId);
    }

    @Override
    public TransferShopDto updateShopData(ObjectId id, UpdateShopDto updateShopDto) {
        return shopsService.updateEntityById(id, updateShopDto);
    }

    @Override
    public TransferShopDto buyProductsFromTheStock(ObjectId shopId, ProductName productName, double price, int amount) {
        TransferShopDto shop = shopsService.getEntityById(shopId);
        Product product = shopsService.getProductData(shopId, productName);
        boolean isWalletAmountEnough = walletsService.isWalletHasEnoughAmountByOwnerId(shopId, amount);
        if (!isWalletAmountEnough) {
            throw new ShopHasNotEnoughMoneyException(String.format("The shop with id '%s' has money less then '%s' and can't do the payment", shopId, amount));
        }
        walletsService.updateWalletAmount(shop.getWalletId(), DECREASE, product.getAmount() * amount);
        return shopsService.getProductsFromTheStock(shopId, productName, price, amount);
    }

    @Override
    public TransferShopDto setProductPrice(ObjectId shopId, ProductName productName, double price) {
        return shopsService.setProductPrice(shopId, productName, price);
    }

    @Override
    public void deleteShop(ObjectId id) {
        shopsService.deleteEntityById(id);
    }
}
