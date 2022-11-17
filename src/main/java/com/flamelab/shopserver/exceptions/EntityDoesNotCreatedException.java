package com.flamelab.shopserver.exceptions;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

public class EntityDoesNotCreatedException extends ResourceException {
    public EntityDoesNotCreatedException(String message) {
        super(INTERNAL_SERVER_ERROR, message);
    }
}
