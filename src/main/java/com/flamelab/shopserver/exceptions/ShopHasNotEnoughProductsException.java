package com.flamelab.shopserver.exceptions;

import static org.springframework.http.HttpStatus.NO_CONTENT;

public class ShopHasNotEnoughProductsException extends ResourceException {
    public ShopHasNotEnoughProductsException(String message) {
        super(NO_CONTENT, message);
    }
}
