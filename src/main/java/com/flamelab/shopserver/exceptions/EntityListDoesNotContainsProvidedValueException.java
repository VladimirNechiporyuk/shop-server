package com.flamelab.shopserver.exceptions;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class EntityListDoesNotContainsProvidedValueException extends ResourceException {
    public EntityListDoesNotContainsProvidedValueException(String message) {
        super(BAD_REQUEST, message);
    }
}
