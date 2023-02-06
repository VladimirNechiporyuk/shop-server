package com.flamelab.shopserver.exceptions;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

public class UnauthorizedUserException extends ResourceException {
    public UnauthorizedUserException(String message) {
        super(UNAUTHORIZED, message);
    }
}
