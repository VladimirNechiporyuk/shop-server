package com.flamelab.shopserver.controllers;

import com.flamelab.shopserver.dtos.create.CreateUserDto;
import com.flamelab.shopserver.dtos.transafer.TransferUserDto;
import com.flamelab.shopserver.dtos.update.UpdateUserDto;
import com.flamelab.shopserver.enums.ProductName;
import com.flamelab.shopserver.managers.UsersManager;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static com.flamelab.shopserver.utiles.naming.FieldMapper.mapCriterias;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UsersController {

    private final UsersManager usersManager;

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
    public ResponseEntity<?> getUserBy(@RequestParam Map<String, Object> criterias) {
        return ResponseEntity
                .status(OK)
                .body(usersManager.getUserBy(mapCriterias(criterias)));
    }

    @GetMapping("/all")
    public ResponseEntity<List<TransferUserDto>> getAllUsers() {
        return ResponseEntity
                .status(OK)
                .body(usersManager.getAllUsers());
    }

    @GetMapping("/allBy")
    public ResponseEntity<List<TransferUserDto>> getAllUsersBy(@RequestParam Map<String, Object> criterias) {
        return ResponseEntity
                .status(OK)
                .body(usersManager.getAllUsersByCriterias(mapCriterias(criterias)));
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
