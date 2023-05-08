package com.flamelab.shopserver.exceptions.hendlers;

import com.flamelab.shopserver.exceptions.ResourceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Arrays;

@ControllerAdvice
@Slf4j
public class ExceptionHandlerAdvice {

    @ExceptionHandler(ResourceException.class)
    public ResponseEntity<?> handleException(ResourceException e) {
        log.trace(Arrays.toString(e.getStackTrace()));
        e.printStackTrace();
        return ResponseEntity
                .status(e.getHttpStatus())
                .header("Exception message", e.getMessage())
                .body(e.getMessage());
    }

}
