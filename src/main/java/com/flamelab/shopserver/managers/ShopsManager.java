package com.flamelab.shopserver.managers;

import com.flamelab.shopserver.dtos.create.external.CreateShopDto;
import com.flamelab.shopserver.dtos.transfer.TransferAuthTokenDto;
import com.flamelab.shopserver.dtos.transfer.TransferShopDto;
import com.flamelab.shopserver.dtos.transfer.TransferWalletDto;
import com.flamelab.shopserver.dtos.update.UpdateShopDto;
import com.flamelab.shopserver.enums.ProductName;
import com.flamelab.shopserver.internal_data.Product;
import com.flamelab.shopserver.utiles.naming.FieldNames;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.Map;

public interface ShopsManager {

    TransferShopDto createShop(TransferAuthTokenDto authToken, CreateShopDto createShopDto);

    TransferShopDto getShopById(ObjectId id);

    List<TransferShopDto> getAllShops();

    List<TransferShopDto> getAllShopsByCriterias(Map<FieldNames, Object> criterias);

    List<Product> getAllProductsInTheShop(ObjectId shopId);

    TransferWalletDto getShopWallet(TransferAuthTokenDto authToken, ObjectId shopId);

    TransferShopDto updateShopData(TransferAuthTokenDto authToken, ObjectId shopId, UpdateShopDto updateShopDto);

    TransferShopDto buyProductsFromTheStock(TransferAuthTokenDto authToken, ObjectId shopId, ProductName productName, double price, int amount);

    TransferShopDto setProductPrice(TransferAuthTokenDto authToken, ObjectId shopId, ProductName productName, double price);

    void deleteShop(TransferAuthTokenDto authToken, ObjectId shopId);
}
