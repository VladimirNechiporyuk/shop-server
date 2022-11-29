package com.flamelab.shopserver.managers;

import com.flamelab.shopserver.dtos.create.CreateShopDto;
import com.flamelab.shopserver.dtos.transafer.TransferShopDto;
import com.flamelab.shopserver.dtos.transafer.TransferWalletDto;
import com.flamelab.shopserver.dtos.update.UpdateShopDto;
import com.flamelab.shopserver.enums.AmountActionType;
import com.flamelab.shopserver.enums.ProductName;
import com.flamelab.shopserver.internal_data.Product;
import com.flamelab.shopserver.utiles.naming.FieldNames;
import org.bson.types.ObjectId;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

public interface ShopsManager {
    TransferShopDto createShop(CreateShopDto createShopDto);

    TransferShopDto getShopById(ObjectId id);

    TransferShopDto getShopBy(Map<FieldNames, Object> criterias);

    List<TransferShopDto> getAllShops();

    List<TransferShopDto> getAllShopsByCriterias(Map<FieldNames, Object> criterias);

    List<Product> getAllProductsInTheShop(ObjectId id);

    TransferWalletDto getShopWallet(ObjectId shopId);

    TransferShopDto updateShopData(ObjectId id, UpdateShopDto updateShopDto);

    TransferShopDto buyProductsFromTheStock(ObjectId shopId, ProductName productName, double price, int amount);

    TransferShopDto setProductPrice(ObjectId shopId, ProductName productName, double price);

    void deleteShop(ObjectId id);
}
