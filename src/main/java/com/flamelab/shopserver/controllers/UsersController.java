package com.flamelab.shopserver.controllers;

import com.flamelab.shopserver.dtos.create.external.CreateUserDto;
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
    private final List<Roles> ADMIN_AND_CUSTOMER_ROLES = List.of(ADMIN, CUSTOMER);
    private final List<Roles> ADMIN_ROLE = List.of(ADMIN);

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
            authManager.validateAuthToken(authorization, ADMIN_ROLE);
        }
        return ResponseEntity
                .status(CREATED)
                .body(usersManager.createUser(createUserDto));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserById(@RequestHeader String authorization, @PathVariable("userId") ObjectId userId) {
        return ResponseEntity
                .status(OK)
                .body(usersManager.getUserById(
                        authManager.validateAuthToken(authorization, ADMIN_AND_CUSTOMER_ROLES),
                        userId));
    }

    @GetMapping("/all")
    public ResponseEntity<List<TransferUserDto>> getAllUsers(@RequestHeader String authorization) {
        return ResponseEntity
                .status(OK)
                .body(usersManager.getAllUsers(authManager.validateAuthToken(authorization, ADMIN_AND_CUSTOMER_ROLES)));
    }

    @GetMapping("/allBy")
    public ResponseEntity<List<TransferUserDto>> getAllUsersBy(@RequestHeader String authorization, @RequestParam Map<String, Object> criterias) {
        return ResponseEntity
                .status(OK)
                .body(usersManager.getAllUsersByCriterias(
                        authManager.validateAuthToken(authorization, ADMIN_ROLE),
                        mapCriterias(criterias)));
    }

    @GetMapping("/wallet/{userId}")
    public ResponseEntity<?> getUserWallet(@RequestHeader String authorization, @PathVariable("userId") ObjectId userId) {
        return ResponseEntity
                .status(OK)
                .body(usersManager.getUserWallet(
                        authManager.validateAuthToken(authorization, ADMIN_AND_CUSTOMER_ROLES),
                        userId));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<?> updateUserData(@RequestHeader String authorization, @PathVariable("userId") ObjectId userId, @RequestBody UpdateUserDto updateUserDto) {
        return ResponseEntity
                .status(OK)
                .body(usersManager.updateUserData(
                        authManager.validateAuthToken(authorization, ADMIN_AND_CUSTOMER_ROLES),
                        userId, updateUserDto));
    }

    @PutMapping("/updatePassword")
    public ResponseEntity<?> updatePassword(@RequestHeader String authorization, @RequestBody UpdateUserPasswordDto updateUserPasswordDto) {
        usersManager.updateUserPassword(authManager.validateAuthToken(authorization, ADMIN_AND_CUSTOMER_ROLES), updateUserPasswordDto);
        return ResponseEntity.status(OK).build();
    }

    @PutMapping("/{amount}")
    public ResponseEntity<?> deposit(@RequestHeader String authorization, @PathVariable("amount") int amount) {
        return ResponseEntity
                .status(OK)
                .body(usersManager.deposit(authManager.validateAuthToken(authorization, ADMIN_AND_CUSTOMER_ROLES), amount));
    }

    @PutMapping("/buyProducts")
    public ResponseEntity<?> buyProducts(@RequestHeader String authorization, @RequestParam ObjectId shopId, @RequestParam ProductName productName, @RequestParam int amount) {
        return ResponseEntity
                .status(OK)
                .body(usersManager.buyProducts(
                        authManager.validateAuthToken(authorization, ADMIN_AND_CUSTOMER_ROLES),
                        shopId,
                        productName,
                        amount));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@RequestHeader String authorization, @PathVariable("userId") ObjectId userId) {
        usersManager.deleteUser(authManager.validateAuthToken(authorization, ADMIN_AND_CUSTOMER_ROLES), userId);
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .build();
    }

}
