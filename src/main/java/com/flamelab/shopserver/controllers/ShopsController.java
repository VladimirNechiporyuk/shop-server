package com.flamelab.shopserver.controllers;

import com.flamelab.shopserver.dtos.create.CreateShopDto;
import com.flamelab.shopserver.dtos.transafer.TransferShopDto;
import com.flamelab.shopserver.dtos.update.UpdateShopDto;
import com.flamelab.shopserver.enums.ProductName;
import com.flamelab.shopserver.managers.ShopsManager;
import com.flamelab.shopserver.utiles.naming.FieldMapper;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static com.flamelab.shopserver.utiles.naming.FieldMapper.mapCriterias;
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
                .body(shopsManager.createShop(createShopDto));
    }

    @GetMapping("/{shopId}")
    public ResponseEntity<?> getShopById(@PathVariable ObjectId shopId) {
        return ResponseEntity
                .status(OK)
                .body(shopsManager.getShopById(shopId));
    }

    @GetMapping
    public ResponseEntity<?> getShopBy(@RequestParam Map<String, Object> criterias) {
        return ResponseEntity
                .status(OK)
                .body(shopsManager.getShopBy(mapCriterias(criterias)));
    }

    @GetMapping("/all")
    public ResponseEntity<List<TransferShopDto>> getAllShops() {
        return ResponseEntity
                .status(OK)
                .body(shopsManager.getAllShops());
    }

    @GetMapping("/allBy")
    public ResponseEntity<List<TransferShopDto>> getAllShopsBy(@RequestParam Map<String, Object> criterias) {
        return ResponseEntity
                .status(OK)
                .body(shopsManager.getAllShopsByCriterias(mapCriterias(criterias)));
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
