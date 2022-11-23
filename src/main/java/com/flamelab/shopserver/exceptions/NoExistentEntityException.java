package com.flamelab.shopserver.exceptions;

import static org.springframework.http.HttpStatus.NO_CONTENT;

public class NoExistentEntityException extends ResourceException {

    public NoExistentEntityException(String message) {
        super(NO_CONTENT, message);
    }
}
