package com.flamelab.shopserver.controllers;

import com.flamelab.shopserver.dtos.create.CreateUserAuthToken;
import com.flamelab.shopserver.dtos.transafer.TransferAuthTokenDto;
import com.flamelab.shopserver.managers.AuthManager;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
