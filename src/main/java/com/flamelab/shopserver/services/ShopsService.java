package com.flamelab.shopserver.services;

import com.flamelab.shopserver.dtos.create.CreateShopDto;
import com.flamelab.shopserver.dtos.transafer.TransferShopDto;
import com.flamelab.shopserver.dtos.update.UpdateShopDto;
import com.flamelab.shopserver.enums.ProductName;
import com.flamelab.shopserver.internal_data.Product;
import org.bson.types.ObjectId;

import java.util.List;

public interface ShopsService {

    TransferShopDto createShop(CreateShopDto createShopDto);

    TransferShopDto getShopById(ObjectId shopId);

    List<TransferShopDto> getAllShops();

    List<Product> getAllProductsInTheShop(ObjectId shopId);

    Product getProductData(ObjectId shopId, ProductName productName);

    TransferShopDto updateShopData(ObjectId shopId, UpdateShopDto updateShopDto);

    TransferShopDto addWalletToShop(ObjectId shopId, ObjectId walletId);

    TransferShopDto buyProductsFromTheStock(ObjectId shopId, ProductName productName, double price, int amount);

    TransferShopDto decreaseProductsAmount(ObjectId shopId, ProductName productName, int amount);

    TransferShopDto setProductPrice(ObjectId shopId, ProductName productName, double price);

    void deleteShop(ObjectId shopId);
}
