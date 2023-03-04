package com.flamelab.shopserver.controllers;

import com.flamelab.shopserver.dtos.create.external.CreateUserAuthToken;
import com.flamelab.shopserver.dtos.transfer.TransferAuthTokenDto;
import com.flamelab.shopserver.enums.Roles;
import com.flamelab.shopserver.managers.AuthManager;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthManager authManager;

    @PostMapping("/token")
    public ResponseEntity<TransferAuthTokenDto> loginUser(@RequestBody CreateUserAuthToken createUserAuthToken) {
        return ResponseEntity
                .status(200)
                .body(authManager.login(createUserAuthToken));
    }

    @PostMapping("/logout")
    public ResponseEntity<TransferAuthTokenDto> logoutUser(@RequestHeader String authorization) {
        authManager.validateAuthToken(authorization, List.of(Roles.values()));
        authManager.logout(authorization);
        return ResponseEntity
                .status(200)
                .build();
    }

}
