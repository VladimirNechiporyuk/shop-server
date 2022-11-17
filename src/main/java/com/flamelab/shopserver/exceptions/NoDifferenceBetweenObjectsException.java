package com.flamelab.shopserver.exceptions;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class NoDifferenceBetweenObjectsException extends ResourceException {
    public NoDifferenceBetweenObjectsException(String message) {
        super(BAD_REQUEST, message);
    }
}
