package com.flamelab.shopserver.exceptions;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class WrongCriteriaNameProvidedException extends ResourceException {
    public WrongCriteriaNameProvidedException(String message) {
        super(BAD_REQUEST, message);
    }
}
