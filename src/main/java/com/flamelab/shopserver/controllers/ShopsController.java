package com.flamelab.shopserver.controllers;

import com.flamelab.shopserver.dtos.create.CreateShopDto;
import com.flamelab.shopserver.enums.Roles;
import com.flamelab.shopserver.managers.AuthManager;
import com.flamelab.shopserver.managers.ShopsManager;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/shops")
@RequiredArgsConstructor
public class ShopsController {

    private final AuthManager authManager;
    private final ShopsManager shopsManager;

    @PostMapping
    public ResponseEntity<?> createShop(@RequestHeader("Authorization") String authorization, @RequestBody CreateShopDto createShopDto) {
        return ResponseEntity
                .status(CREATED)
                .body(shopsManager.createShop(
                        authManager.validateAuthToken(authorization, Roles.MERCHANT()),
                        createShopDto));
    }

    @GetMapping("/{shopId}")
    public ResponseEntity<?> getShopById(@RequestHeader("Authorization") String authorization, @PathVariable String shopId) {
        return ResponseEntity
                .status(OK)
                .body(shopsManager.getShopById(
                        authManager.validateAuthToken(authorization, Roles.ADMIN_MERCHANT()),
                        shopId));
    }

    @GetMapping
    public ResponseEntity<?> getAllShops(@RequestHeader("Authorization") String authorization) {
        return ResponseEntity
                .status(OK)
                .body(shopsManager.getAllShops(
                        authManager.validateAuthToken(authorization, Roles.ADMIN_MERCHANT())));
    }

    @GetMapping("/byOwnerId")
    public ResponseEntity<?> getAllShopsByOwnerId(@RequestHeader("Authorization") String authorization, @RequestParam String ownerId) {
        return ResponseEntity
                .status(OK)
                .body(shopsManager.getAllShopsByOwnerId(
                        authManager.validateAuthToken(authorization, Roles.ADMIN_MERCHANT()),
                        ownerId));
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchShops(@RequestHeader("Authorization") String authorization, String text) {
        return ResponseEntity
                .status(OK)
                .body(shopsManager.getAllShopsByTextInParameters(
                        authManager.validateAuthToken(authorization, Roles.ADMIN_MERCHANT()),
                        text));
    }

    @GetMapping("/products/{shopId}")
    public ResponseEntity<?> getAllProductsInTheShop(@RequestHeader("Authorization") String authorization, String shopId) {
        return ResponseEntity
                .status(OK)
                .body(shopsManager.getAllProductsInTheShop(
                        authManager.validateAuthToken(authorization, Roles.ADMIN_MERCHANT()),
                        shopId));
    }

    @PutMapping("/renameShop/{shopId}")
    public ResponseEntity<?> renameShop(@RequestHeader("Authorization") String authorization, @PathVariable String shopId, @RequestParam String newName) {
        return ResponseEntity
                .status(OK)
                .body(shopsManager.renameShop(
                        authManager.validateAuthToken(authorization, Roles.MERCHANT()),
                        shopId, newName));
    }

    @PutMapping("/renameProduct/{productId}")
    public ResponseEntity<?> renameProduct(@RequestHeader("Authorization") String authorization, @PathVariable String productId, @RequestParam String newName) {
        return ResponseEntity
                .status(OK)
                .body(shopsManager.renameProduct(
                        authManager.validateAuthToken(authorization, Roles.MERCHANT()),
                        productId, newName));
    }

    @PutMapping("/buy/stock/{shopId}")
    public ResponseEntity<?> buyProductsFromTheStock(@RequestHeader("Authorization") String authorization, @PathVariable String shopId, @RequestParam String productName, @RequestParam int productAmount, @RequestParam double price) {
        return ResponseEntity
                .status(OK)
                .body(shopsManager.buyProductsFromTheStock(
                        authManager.validateAuthToken(authorization, Roles.MERCHANT()),
                        shopId, productName, productAmount, price));
    }

    @PutMapping("/buy/{shopId}")
    public ResponseEntity<?> buyExistsProductsFromTheStock(@RequestHeader("Authorization") String authorization, @PathVariable String shopId, @RequestParam String productId, @RequestParam double productCost, @RequestParam int productAmount) {
        return ResponseEntity
                .status(OK)
                .body(shopsManager.buyExistsProductsFromTheStock(
                        authManager.validateAuthToken(authorization, Roles.MERCHANT()),
                        shopId, productId, productCost, productAmount));
    }

    @PutMapping("/buy/{shopId}/{productId}")
    public ResponseEntity<?> buyProductsByUser(@RequestHeader("Authorization") String authorization, @PathVariable String shopId, @PathVariable String productId, @RequestParam int productAmount) {
        return ResponseEntity
                .status(OK)
                .body(shopsManager.buyProductsFromTheShop(
                        authManager.validateAuthToken(authorization, Roles.MERCHANT()),
                        shopId, productId, productAmount));
    }

    @PutMapping("/productPrice/{productId}")
    public ResponseEntity<?> setProductPrice(@RequestHeader("Authorization") String authorization, @PathVariable String productId, @RequestParam double newPrice) {
        return ResponseEntity
                .status(OK)
                .body(shopsManager.setProductPrice(
                        authManager.validateAuthToken(authorization, Roles.ADMIN_MERCHANT()),
                        productId, newPrice));
    }

    @PutMapping("/product/price/{productId}")
    public ResponseEntity<?> setProductAmount(@RequestHeader("Authorization") String authorization, @PathVariable String productId, @RequestParam int newAmount) {
        return ResponseEntity
                .status(OK)
                .body(shopsManager.setProductAmount(
                        authManager.validateAuthToken(authorization, Roles.ADMIN()),
                        productId, newAmount));
    }

    @DeleteMapping("/product/amount/{productId}")
    public ResponseEntity<?> deleteProductAmount(@RequestHeader("Authorization") String authorization, @PathVariable String productId, @RequestParam int amount) {
        return ResponseEntity
                .status(OK)
                .body(shopsManager.deleteProductAmount(
                        authManager.validateAuthToken(authorization, Roles.ADMIN()),
                        productId, amount));
    }

        @DeleteMapping("/{shopId}")
    public ResponseEntity<?> deleteShop(@RequestHeader("Authorization") String authorization, @PathVariable String shopId) {
        shopsManager.deleteShop(authManager.validateAuthToken(authorization, Roles.ADMIN_MERCHANT()), shopId);
        return ResponseEntity
                .status(OK)
                .build();
    }

}
