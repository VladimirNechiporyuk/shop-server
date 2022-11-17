package com.flamelab.shopserver.exceptions;

import static org.springframework.http.HttpStatus.NO_CONTENT;

public class NoProductException extends ResourceException {
    public NoProductException(String message) {
        super(NO_CONTENT, message);
    }
}
