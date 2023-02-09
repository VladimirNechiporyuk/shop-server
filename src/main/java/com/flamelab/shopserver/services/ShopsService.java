package com.flamelab.shopserver.services;

import com.flamelab.shopserver.dtos.create.external.CreateShopDto;
import com.flamelab.shopserver.dtos.create.internal.InternalCreateShop;
import com.flamelab.shopserver.dtos.update.UpdateShopDto;
import com.flamelab.shopserver.entities.Shop;
import com.flamelab.shopserver.enums.ProductName;
import com.flamelab.shopserver.internal_data.Product;
import org.bson.types.ObjectId;

import java.util.List;

public interface ShopsService extends CommonService<InternalCreateShop, Shop, UpdateShopDto> {

    List<Product> getAllProductsInTheShop(ObjectId shopId);

    Product getProductData(ObjectId shopId, ProductName productName);

    boolean isShopContainsProduct(ObjectId shopId, ProductName productName);

    Shop addWalletToShop(ObjectId shopId, ObjectId walletId);

    Shop addProductsToTheStore(ObjectId shopId, boolean isShopContainsProduct, ProductName productName, double price, int amount);

    Shop decreaseProductsAmount(ObjectId shopId, ProductName productName, int amount);

    Shop setProductPrice(ObjectId shopId, ProductName productName, double price);

}
