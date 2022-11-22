package com.flamelab.shopserver.controllers;

import com.flamelab.shopserver.dtos.create.CreateUserDto;
import com.flamelab.shopserver.dtos.transafer.TransferUserDto;
import com.flamelab.shopserver.dtos.update.UpdateUserDto;
import com.flamelab.shopserver.enums.ProductName;
import com.flamelab.shopserver.managers.UsersManager;
import com.flamelab.shopserver.utiles.naming.FieldNames;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.web.bind.annotation.RequestMethod.OPTIONS;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UsersController {

    private final UsersManager usersManager;

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
    public ResponseEntity<TransferUserDto> createUser(@RequestBody CreateUserDto createUserDto) {
        return ResponseEntity
                .status(CREATED)
                .body(usersManager.createUser(createUserDto));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable("userId") ObjectId userId) {
        return ResponseEntity
                .status(OK)
                .body(usersManager.getUserById(userId));
    }

    @GetMapping
    public ResponseEntity<?> getUserBy(@RequestParam Map<FieldNames, Object> criterias) {
        return ResponseEntity
                .status(OK)
                .body(usersManager.getUserBy(criterias));
    }

    @GetMapping("/all")
    public ResponseEntity<List<TransferUserDto>> getAllUsers() {
        return ResponseEntity
                .status(OK)
                .body(usersManager.getAllUsers());
    }

    @GetMapping("/wallet/{userId}")
    public ResponseEntity<?> getUserWallet(@PathVariable("userId") ObjectId userId) {
        return ResponseEntity
                .status(OK)
                .body(usersManager.getUserWallet(userId));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<?> updateUserData(@PathVariable("userId") ObjectId userId, @RequestBody UpdateUserDto updateUserDto) {
        return ResponseEntity
                .status(OK)
                .body(usersManager.updateUserData(userId, updateUserDto));
    }

    @PutMapping("/{userId}/{amount}")
    public ResponseEntity<?> deposit(@PathVariable("userId") ObjectId userId, @PathVariable("amount") int amount) {
        return ResponseEntity
                .status(OK)
                .body(usersManager.deposit(userId, amount));
    }

    @PutMapping("/buyProducts")
    public ResponseEntity<?> buyProducts(@RequestParam ObjectId userId, @RequestParam ObjectId shopId, @RequestParam ProductName productName, @RequestParam int amount) {
        return ResponseEntity
                .status(OK)
                .body(usersManager.buyProducts(userId, shopId, productName, amount));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable("userId") ObjectId userId) {
        usersManager.deleteUser(userId);
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .build();
    }

}
