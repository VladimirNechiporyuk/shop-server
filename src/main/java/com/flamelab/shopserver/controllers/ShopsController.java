package com.flamelab.shopserver.controllers;

import com.flamelab.shopserver.dtos.create.CreateShopDto;
import com.flamelab.shopserver.dtos.transafer.TransferShopDto;
import com.flamelab.shopserver.dtos.update.UpdateShopDto;
import com.flamelab.shopserver.enums.ProductName;
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

    @PostMapping
    public ResponseEntity<TransferShopDto> createShop(@RequestHeader String authorization, @RequestBody CreateShopDto createShopDto) {
        authManager.isAuthorized(authorization, List.of(ADMIN, MERCHANT));
        return ResponseEntity
                .status(CREATED)
                .body(shopsManager.createShop(createShopDto));
    }

    @GetMapping("/{shopId}")
    public ResponseEntity<?> getShopById(@RequestHeader String authorization, @PathVariable("shopId") ObjectId shopId) {
        authManager.isAuthorized(authorization, List.of(ADMIN, MERCHANT, CUSTOMER));
        return ResponseEntity
                .status(OK)
                .body(shopsManager.getShopById(shopId));
    }

    @GetMapping
    public ResponseEntity<?> getShopBy(@RequestHeader String authorization, @RequestParam Map<String, Object> criterias) {
        authManager.isAuthorized(authorization, List.of(ADMIN, MERCHANT, CUSTOMER));
        return ResponseEntity
                .status(OK)
                .body(shopsManager.getShopBy(mapCriterias(criterias)));
    }

    @GetMapping("/all")
    public ResponseEntity<List<TransferShopDto>> getAllShops(@RequestHeader String authorization) {
        authManager.isAuthorized(authorization, List.of(ADMIN, MERCHANT, CUSTOMER));
        return ResponseEntity
                .status(OK)
                .body(shopsManager.getAllShops());
    }

    @GetMapping("/allBy")
    public ResponseEntity<List<TransferShopDto>> getAllShopsBy(@RequestHeader String authorization, @RequestParam Map<String, Object> criterias) {
        authManager.isAuthorized(authorization, List.of(ADMIN, MERCHANT, CUSTOMER));
        return ResponseEntity
                .status(OK)
                .body(shopsManager.getAllShopsByCriterias(mapCriterias(criterias)));
    }

    @GetMapping("/productsInTheShop/{shopId}")
    public ResponseEntity<?> getAllProductsInTheShop(@RequestHeader String authorization, @PathVariable("shopId") ObjectId shopId) {
        authManager.isAuthorized(authorization, List.of(ADMIN, MERCHANT, CUSTOMER));
        return ResponseEntity
                .status(OK)
                .body(shopsManager.getAllProductsInTheShop(shopId));
    }

    @GetMapping("/wallet/{shopId}")
    public ResponseEntity<?> getShopWallet(@RequestHeader String authorization, @PathVariable("shopId") ObjectId shopId) {
        authManager.isAuthorized(authorization, List.of(ADMIN, MERCHANT));
        return ResponseEntity
                .status(OK)
                .body(shopsManager.getShopWallet(shopId));
    }

    @PutMapping("/{shopId}")
    public ResponseEntity<?> updateShopData(@RequestHeader String authorization, @PathVariable("shopId") ObjectId shopId, @RequestBody UpdateShopDto updateShopDto) {
        authManager.isAuthorized(authorization, List.of(ADMIN, MERCHANT));
        return ResponseEntity
                .status(OK)
                .body(shopsManager.updateShopData(shopId, updateShopDto));
    }

    @PutMapping("/buyProductsFromTheStock")
    public ResponseEntity<?> buyProductsFromTheStock(@RequestHeader String authorization, @RequestParam ObjectId shopId, @RequestParam ProductName productName, @RequestParam double price, @RequestParam int amount) {
        authManager.isAuthorized(authorization, List.of(ADMIN, MERCHANT));
        return ResponseEntity
                .status(OK)
                .body(shopsManager.buyProductsFromTheStock(shopId, productName, price, amount));
    }

    @PutMapping("/setProductPrice")
    public ResponseEntity<?> setProductPrice(@RequestHeader String authorization, @RequestParam ObjectId shopId, @RequestParam ProductName productName, @RequestParam double price) {
        authManager.isAuthorized(authorization, List.of(ADMIN, MERCHANT));
        return ResponseEntity
                .status(OK)
                .body(shopsManager.setProductPrice(shopId, productName, price));
    }

    @DeleteMapping("/{shopId}")
    public ResponseEntity<?> deleteShop(@RequestHeader String authorization, @PathVariable("shopId") ObjectId shopId) {
        authManager.isAuthorized(authorization, List.of(ADMIN));
        shopsManager.deleteShop(shopId);
        return ResponseEntity
                .status(ACCEPTED)
                .build();
    }

}
