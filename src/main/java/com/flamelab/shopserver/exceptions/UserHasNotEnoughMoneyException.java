package com.flamelab.shopserver.exceptions;

import static org.springframework.http.HttpStatus.NO_CONTENT;

public class UserHasNotEnoughMoneyException extends ResourceException {
    public UserHasNotEnoughMoneyException(String message) {
        super(NO_CONTENT, message);
    }
}
