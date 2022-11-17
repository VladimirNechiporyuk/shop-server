package com.flamelab.shopserver.exceptions;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class MoreThanOneEntityExistsByQueryException extends ResourceException {

    public MoreThanOneEntityExistsByQueryException(String message) {
        super(BAD_REQUEST, message);
    }
}
