package com.flamelab.shopserver.controllers;

import com.flamelab.shopserver.dtos.create.CreateShopDto;
import com.flamelab.shopserver.dtos.transafer.TransferShopDto;
import com.flamelab.shopserver.dtos.update.UpdateShopDto;
import com.flamelab.shopserver.enums.ProductName;
import com.flamelab.shopserver.managers.ShopsManager;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.web.bind.annotation.RequestMethod.OPTIONS;

@RestController
@RequestMapping("/shops")
@RequiredArgsConstructor
public class ShopsController {

    private final ShopsManager shopsManager;

    @RequestMapping(method = OPTIONS)
    public ResponseEntity<?> returnOptionHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Access-Control-Allow-Origin", "*");
        httpHeaders.add("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS");
        httpHeaders.add("Access-Control-Allow-Headers", "Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");
        return ResponseEntity
                .status(NO_CONTENT)
                .headers(httpHeaders)
                .build();
    }

    @PostMapping
    public ResponseEntity<TransferShopDto> createShop(@RequestBody CreateShopDto createShopDto) {
        return ResponseEntity
                .status(CREATED)
                .body(shopsManager.createShop(createShopDto));
    }

    @GetMapping("/{shopId}")
    public ResponseEntity<?> getShopById(@PathVariable ObjectId shopId) {
        return ResponseEntity
                .status(OK)
                .body(shopsManager.getShopById(shopId));
    }

    @GetMapping("/all")
    public ResponseEntity<List<TransferShopDto>> getAllShops() {
        return ResponseEntity
                .status(OK)
                .body(shopsManager.getAllShops());
    }

    @GetMapping("/productsInTheShop/{shopId}")
    public ResponseEntity<?> getAllProductsInTheShop(@PathVariable("id") ObjectId shopId) {
        return ResponseEntity
                .status(OK)
                .body(shopsManager.getAllProductsInTheShop(shopId));
    }

    @GetMapping("/wallet/{shopId}")
    public ResponseEntity<?> getUserWallet(@PathVariable("shopId") ObjectId shopId) {
        return ResponseEntity
                .status(OK)
                .body(shopsManager.getShopWallet(shopId));
    }

    @PutMapping("/{shopId}")
    public ResponseEntity<?> updateShopData(@PathVariable("id") ObjectId shopId, @RequestBody UpdateShopDto updateShopDto) {
        return ResponseEntity
                .status(OK)
                .body(shopsManager.updateShopData(shopId, updateShopDto));
    }

    @PutMapping("/buyProductsFromTheStock")
    public ResponseEntity<?> buyProductsFromTheStock(@RequestParam ObjectId shopId, @RequestParam ProductName productName, @RequestParam double price, @RequestParam int amount) {
        return ResponseEntity
                .status(OK)
                .body(shopsManager.buyProductsFromTheStock(shopId, productName, price, amount));
    }

    @PutMapping("/setProductPrice")
    public ResponseEntity<?> setProductPrice(@RequestParam ObjectId shopId, @RequestParam ProductName productName, @RequestParam double price) {
        return ResponseEntity
                .status(OK)
                .body(shopsManager.setProductPrice(shopId, productName, price));
    }

    @DeleteMapping("/{shopId}")
    public ResponseEntity<?> deleteShop(@PathVariable("id") ObjectId shopId) {
        shopsManager.deleteShop(shopId);
        return ResponseEntity
                .status(ACCEPTED)
                .build();
    }

}
