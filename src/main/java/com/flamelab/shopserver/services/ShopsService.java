package com.flamelab.shopserver.services;

import com.flamelab.shopserver.dtos.create.CreateShopDto;
import com.flamelab.shopserver.entities.Product;
import com.flamelab.shopserver.entities.Shop;

import java.util.List;

public interface ShopsService {

    Shop createShop(CreateShopDto createShopDto, String walletId, String userId);

    Shop getShopById(String shopId);

    List<Shop> getAllShops();

    List<Shop> getAllShopsByOwnerId(String ownerId);

    List<Shop> getAllShopsByTextInName(String text);

    Shop renameShop(String shopId, String newName);

    void deleteShop(String shopId);

    void deleteShops(List<String> shopIds);
}
