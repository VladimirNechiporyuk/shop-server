package com.flamelab.shopserver.exceptions;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

public class DifferentClassesException extends ResourceException {
    public DifferentClassesException(String message) {
        super(INTERNAL_SERVER_ERROR, message);
    }
}
