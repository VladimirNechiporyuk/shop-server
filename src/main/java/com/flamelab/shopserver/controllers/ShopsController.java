package com.flamelab.shopserver.controllers;

import com.flamelab.shopserver.dtos.create.CreateShopDto;
import com.flamelab.shopserver.dtos.transafer.TransferShopDto;
import com.flamelab.shopserver.dtos.update.UpdateShopDto;
import com.flamelab.shopserver.enums.ProductName;
import com.flamelab.shopserver.managers.ShopsManager;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/shops")
@RequiredArgsConstructor
public class ShopsController {

    private final ShopsManager shopsManager;

    @PostMapping
    public ResponseEntity<TransferShopDto> createShop(@RequestBody CreateShopDto createShopDto) {
        return ResponseEntity
                .status(CREATED)
                .header("Access-Control-Allow-Origin", "http://localhost:8052")
                .body(shopsManager.createShop(createShopDto));
    }

    @GetMapping("/{shopId}")
    public ResponseEntity<?> getShopById(@PathVariable ObjectId shopId) {
        return ResponseEntity
                .status(OK)
                .header("Access-Control-Allow-Origin", "http://localhost:8052")
                .body(shopsManager.getShopById(shopId));
    }

    @GetMapping("/all")
    public ResponseEntity<List<TransferShopDto>> getAllShops() {
        return ResponseEntity
                .status(OK)
                .header("Access-Control-Allow-Origin", "http://localhost:8052")
                .body(shopsManager.getAllShops());
    }

    @GetMapping("/productsInTheShop/{shopId}")
    public ResponseEntity<?> getAllProductsInTheShop(@PathVariable("id") ObjectId shopId) {
        return ResponseEntity
                .status(OK)
                .header("Access-Control-Allow-Origin", "http://localhost:8052")
                .body(shopsManager.getAllProductsInTheShop(shopId));
    }

    @GetMapping("/wallet/{shopId}")
    public ResponseEntity<?> getUserWallet(@PathVariable("shopId") ObjectId shopId) {
        return ResponseEntity
                .status(OK)
                .header("Access-Control-Allow-Origin", "http://localhost:8052")
                .body(shopsManager.getShopWallet(shopId));
    }

    @PutMapping("/{shopId}")
    public ResponseEntity<?> updateShopData(@PathVariable("id") ObjectId shopId, @RequestBody UpdateShopDto updateShopDto) {
        return ResponseEntity
                .status(OK)
                .header("Access-Control-Allow-Origin", "http://localhost:8052")
                .body(shopsManager.updateShopData(shopId, updateShopDto));
    }

    @PutMapping("/buyProductsFromTheStock")
    public ResponseEntity<?> buyProductsFromTheStock(@RequestParam ObjectId shopId, @RequestParam ProductName productName, @RequestParam double price, @RequestParam int amount) {
        return ResponseEntity
                .status(OK)
                .header("Access-Control-Allow-Origin", "http://localhost:8052")
                .body(shopsManager.buyProductsFromTheStock(shopId, productName, price, amount));
    }

    @PutMapping("/setProductPrice")
    public ResponseEntity<?> setProductPrice(@RequestParam ObjectId shopId, @RequestParam ProductName productName, @RequestParam double price) {
        return ResponseEntity
                .status(OK)
                .header("Access-Control-Allow-Origin", "http://localhost:8052")
                .body(shopsManager.setProductPrice(shopId, productName, price));
    }

    @DeleteMapping("/{shopId}")
    public ResponseEntity<?> deleteShop(@PathVariable("id") ObjectId shopId) {
        shopsManager.deleteShop(shopId);
        return ResponseEntity
                .status(ACCEPTED)
                .header("Access-Control-Allow-Origin", "http://localhost:8052")
                .build();
    }

}
