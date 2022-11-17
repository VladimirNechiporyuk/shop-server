package com.flamelab.shopserver.exceptions;

import static org.springframework.http.HttpStatus.FOUND;

public class EntityAlreadyExistsException extends ResourceException {
    public EntityAlreadyExistsException(String message) {
        super(FOUND, message);
    }
}
