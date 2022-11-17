package com.flamelab.shopserver.exceptions;

import static org.springframework.http.HttpStatus.NO_CONTENT;

public class ShopHasNotEnoughMoneyException extends ResourceException {
    public ShopHasNotEnoughMoneyException(String message) {
        super(NO_CONTENT, message);
    }
}
