package com.flamelab.shopserver.controllers;

import com.flamelab.shopserver.dtos.create.external.CreateShopDto;
import com.flamelab.shopserver.dtos.transafer.TransferShopDto;
import com.flamelab.shopserver.dtos.update.UpdateShopDto;
import com.flamelab.shopserver.enums.ProductName;
import com.flamelab.shopserver.enums.Roles;
import com.flamelab.shopserver.managers.AuthManager;
import com.flamelab.shopserver.managers.ShopsManager;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static com.flamelab.shopserver.enums.Roles.*;
import static com.flamelab.shopserver.utiles.naming.FieldMapper.mapCriterias;
import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/shops")
@RequiredArgsConstructor
public class ShopsController {

    private final ShopsManager shopsManager;
    private final AuthManager authManager;
    private final List<Roles> ADMIN_ROLE = List.of(ADMIN);
    private final List<Roles> ADMIN_AND_MERCHANT_ROLES = List.of(ADMIN, MERCHANT);
    private final List<Roles> ADMIN_AND_MERCHANT_AND_CUSTOMER_ROLES = List.of(ADMIN, MERCHANT, CUSTOMER);

    @PostMapping
    public ResponseEntity<TransferShopDto> createShop(@RequestHeader String authorization, @RequestBody CreateShopDto createShopDto) {
        return ResponseEntity
                .status(CREATED)
                .body(shopsManager.createShop(
                        authManager.validateAuthToken(authorization, ADMIN_AND_MERCHANT_ROLES),
                        createShopDto));
    }

    @GetMapping("/{shopId}")
    public ResponseEntity<?> getShopById(@RequestHeader String authorization, @PathVariable("shopId") ObjectId shopId) {
        authManager.validateAuthToken(authorization, ADMIN_AND_MERCHANT_AND_CUSTOMER_ROLES);
        return ResponseEntity
                .status(OK)
                .body(shopsManager.getShopById(shopId));
    }

    @GetMapping("/all")
    public ResponseEntity<List<TransferShopDto>> getAllShops(@RequestHeader String authorization) {
        authManager.validateAuthToken(authorization, ADMIN_AND_MERCHANT_AND_CUSTOMER_ROLES);
        return ResponseEntity
                .status(OK)
                .body(shopsManager.getAllShops());
    }

    @GetMapping("/allBy")
    public ResponseEntity<List<TransferShopDto>> getAllShopsBy(@RequestHeader String authorization, @RequestParam Map<String, Object> criterias) {
        authManager.validateAuthToken(authorization, ADMIN_AND_MERCHANT_AND_CUSTOMER_ROLES);
        return ResponseEntity
                .status(OK)
                .body(shopsManager.getAllShopsByCriterias(mapCriterias(criterias)));
    }

    @GetMapping("/productsInTheShop/{shopId}")
    public ResponseEntity<?> getAllProductsInTheShop(@RequestHeader String authorization, @PathVariable("shopId") ObjectId shopId) {
        authManager.validateAuthToken(authorization, ADMIN_AND_MERCHANT_AND_CUSTOMER_ROLES);
        return ResponseEntity
                .status(OK)
                .body(shopsManager.getAllProductsInTheShop(shopId));
    }

    @GetMapping("/wallet/{shopId}")
    public ResponseEntity<?> getShopWallet(@RequestHeader String authorization, @PathVariable("shopId") ObjectId shopId) {
        return ResponseEntity
                .status(OK)
                .body(shopsManager.getShopWallet(
                        authManager.validateAuthToken(authorization, ADMIN_AND_MERCHANT_ROLES),
                        shopId));
    }

    @PutMapping("/{shopId}")
    public ResponseEntity<?> updateShopData(@RequestHeader String authorization, @PathVariable("shopId") ObjectId shopId, @RequestBody UpdateShopDto updateShopDto) {
        return ResponseEntity
                .status(OK)
                .body(shopsManager.updateShopData(
                        authManager.validateAuthToken(authorization, ADMIN_AND_MERCHANT_ROLES),
                        shopId,
                        updateShopDto));
    }

    @PutMapping("/buyProductsFromTheStock")
    public ResponseEntity<?> buyProductsFromTheStock(@RequestHeader String authorization, @RequestParam ObjectId shopId, @RequestParam ProductName productName, @RequestParam double price, @RequestParam int amount) {
        return ResponseEntity
                .status(OK)
                .body(shopsManager.buyProductsFromTheStock(
                        authManager.validateAuthToken(authorization, ADMIN_AND_MERCHANT_ROLES),
                        shopId,
                        productName,
                        price,
                        amount));
    }

    @PutMapping("/setProductPrice")
    public ResponseEntity<?> setProductPrice(@RequestHeader String authorization, @RequestParam ObjectId shopId, @RequestParam ProductName productName, @RequestParam double price) {
        return ResponseEntity
                .status(OK)
                .body(shopsManager.setProductPrice(
                        authManager.validateAuthToken(authorization, ADMIN_AND_MERCHANT_ROLES),
                        shopId,
                        productName,
                        price));
    }

    @DeleteMapping("/{shopId}")
    public ResponseEntity<?> deleteShop(@RequestHeader String authorization, @PathVariable("shopId") ObjectId shopId) {
        shopsManager.deleteShop(authManager.validateAuthToken(authorization, ADMIN_ROLE), shopId);
        return ResponseEntity
                .status(ACCEPTED)
                .build();
    }

}
