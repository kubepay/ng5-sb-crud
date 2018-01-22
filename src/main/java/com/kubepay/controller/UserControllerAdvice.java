package com.kubepay.controller;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.kubepay.service.exception.UserNotFoundException;
import com.kubepay.service.exception.UserServiceException;

@ControllerAdvice
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class UserControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> notFoundException(final UserNotFoundException e) {
        return error(e, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserServiceException.class)
    public ResponseEntity<String> userServiceException(final UserServiceException e) {
        if (e.isValidation())
            return error(e, HttpStatus.BAD_REQUEST);
        return error(e, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<String> error(final Exception exception, final HttpStatus httpStatus) {
        final String message = Optional.of(exception.getMessage()).orElse(exception.getClass().getSimpleName());
        return new ResponseEntity<>(message, httpStatus);
    }

}
