package com.flamelab.shopserver.controllers;

import com.flamelab.shopserver.dtos.create.CreateUserDto;
import com.flamelab.shopserver.dtos.transafer.TransferUserDto;
import com.flamelab.shopserver.dtos.update.UpdateUserDto;
import com.flamelab.shopserver.dtos.update.UpdateUserPasswordDto;
import com.flamelab.shopserver.enums.ProductName;
import com.flamelab.shopserver.enums.Roles;
import com.flamelab.shopserver.exceptions.ResourceException;
import com.flamelab.shopserver.managers.AuthManager;
import com.flamelab.shopserver.managers.UsersManager;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.flamelab.shopserver.enums.Roles.ADMIN;
import static com.flamelab.shopserver.enums.Roles.CUSTOMER;
import static com.flamelab.shopserver.utiles.naming.FieldMapper.mapCriterias;
import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UsersController {

    private final UsersManager usersManager;
    private final AuthManager authManager;

    @PostMapping
    public ResponseEntity<TransferUserDto> createUser(@RequestBody CreateUserDto createUserDto) {
        if (createUserDto.getRole().equals(ADMIN)) {
            throw new ResourceException(UNAUTHORIZED, "Can't create ADMIN user by this api.");
        }
        return ResponseEntity
                .status(CREATED)
                .body(usersManager.createUser(createUserDto));
    }

    @PostMapping("/admin")
    public ResponseEntity<TransferUserDto> createAdmin(@RequestHeader String authorization, @RequestBody CreateUserDto createUserDto) {
        if (createUserDto.getRole().equals(ADMIN)) {
            authManager.isAuthorized(authorization, List.of(ADMIN));
        }
        return ResponseEntity
                .status(CREATED)
                .body(usersManager.createUser(createUserDto));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserById(@RequestHeader String authorization, @PathVariable("userId") ObjectId userId) {
        authManager.isAuthorized(authorization, List.of(ADMIN, CUSTOMER));
        return ResponseEntity
                .status(OK)
                .body(usersManager.getUserById(userId));
    }

    @GetMapping
    public ResponseEntity<?> getUserBy(@RequestHeader String authorization, @RequestParam Map<String, Object> criterias) {
        authManager.isAuthorized(authorization, List.of(ADMIN, CUSTOMER));
        return ResponseEntity
                .status(OK)
                .body(usersManager.getUserBy(mapCriterias(criterias)));
    }

    @GetMapping("/all")
    public ResponseEntity<List<TransferUserDto>> getAllUsers(@RequestHeader String authorization) {
        authManager.isAuthorized(authorization, List.of(ADMIN));
        return ResponseEntity
                .status(OK)
                .body(usersManager.getAllUsers());
    }

    @GetMapping("/allBy")
    public ResponseEntity<List<TransferUserDto>> getAllUsersBy(@RequestHeader String authorization, @RequestParam Map<String, Object> criterias) {
        authManager.isAuthorized(authorization, List.of(ADMIN));
        return ResponseEntity
                .status(OK)
                .body(usersManager.getAllUsersByCriterias(mapCriterias(criterias)));
    }

    @GetMapping("/wallet/{userId}")
    public ResponseEntity<?> getUserWallet(@RequestHeader String authorization, @PathVariable("userId") ObjectId userId) {
        authManager.isAuthorized(authorization, List.of(ADMIN, CUSTOMER));
        return ResponseEntity
                .status(OK)
                .body(usersManager.getUserWallet(userId));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<?> updateUserData(@RequestHeader String authorization, @PathVariable("userId") ObjectId userId, @RequestBody UpdateUserDto updateUserDto) {
        authManager.isAuthorized(authorization, List.of(ADMIN, CUSTOMER));
        return ResponseEntity
                .status(OK)
                .body(usersManager.updateUserData(userId, updateUserDto));
    }

    @PutMapping("/updatePassword")
    public ResponseEntity<?> updatePassword(@RequestHeader String authorization, @RequestBody UpdateUserPasswordDto updateUserPasswordDto) {
        authManager.isAuthorized(authorization, List.of(ADMIN, CUSTOMER));
        usersManager.updateUserPassword(updateUserPasswordDto);
        return ResponseEntity.status(OK).build();
    }

    @PutMapping("/{userId}/{amount}")
    public ResponseEntity<?> deposit(@RequestHeader String authorization, @PathVariable("userId") ObjectId userId, @PathVariable("amount") int amount) {
        authManager.isAuthorized(authorization, List.of(ADMIN, CUSTOMER));
        return ResponseEntity
                .status(OK)
                .body(usersManager.deposit(userId, amount));
    }

    @PutMapping("/buyProducts")
    public ResponseEntity<?> buyProducts(@RequestHeader String authorization, @RequestParam ObjectId userId, @RequestParam ObjectId shopId, @RequestParam ProductName productName, @RequestParam int amount) {
        authManager.isAuthorized(authorization, List.of(ADMIN, CUSTOMER));
        return ResponseEntity
                .status(OK)
                .body(usersManager.buyProducts(userId, shopId, productName, amount));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@RequestHeader String authorization, @PathVariable("userId") ObjectId userId) {
        authManager.isAuthorized(authorization, List.of(ADMIN));
        usersManager.deleteUser(userId);
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .build();
    }

}
