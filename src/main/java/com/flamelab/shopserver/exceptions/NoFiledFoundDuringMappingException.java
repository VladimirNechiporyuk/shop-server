package com.flamelab.shopserver.exceptions;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

public class NoFiledFoundDuringMappingException extends ResourceException {
    public NoFiledFoundDuringMappingException(String message) {
        super(INTERNAL_SERVER_ERROR, message);
    }
}
