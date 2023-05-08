package com.flamelab.shopserver.services;

import com.flamelab.shopserver.dtos.create.CreateShopDto;
import com.flamelab.shopserver.entities.Shop;

import java.util.List;

public interface ShopsService {

    Shop createShop(CreateShopDto createShopDto, String walletId, String userId);

    Shop getShopById(String shopId);

    Shop getShopByName(String shopName);

    List<Shop> getAllShops();

    List<Shop> getAllShopsByOwnerId(String ownerId);

    List<Shop> getAllShopsByTextInName(String text);

    Shop renameShop(String shopId, String newName);

    boolean isUserOwnerOfShop(String userId, String shopId);

    boolean isShopExistsWithName(String shopName);

    void deleteShop(String shopId);

    void deleteShops(List<String> shopIds);
}
