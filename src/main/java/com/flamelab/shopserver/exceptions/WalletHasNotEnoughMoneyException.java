package com.flamelab.shopserver.exceptions;

import static org.springframework.http.HttpStatus.NO_CONTENT;

public class WalletHasNotEnoughMoneyException extends ResourceException {
    public WalletHasNotEnoughMoneyException(String message) {
        super(NO_CONTENT, message);
    }
}
