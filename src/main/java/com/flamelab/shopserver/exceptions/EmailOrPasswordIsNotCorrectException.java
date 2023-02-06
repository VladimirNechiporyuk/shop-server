package com.flamelab.shopserver.exceptions;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class EmailOrPasswordIsNotCorrectException extends ResourceException {
    public EmailOrPasswordIsNotCorrectException(String message) {
        super(BAD_REQUEST, message);
    }
}
