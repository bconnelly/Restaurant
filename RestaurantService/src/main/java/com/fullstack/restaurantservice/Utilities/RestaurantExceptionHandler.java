package com.fullstack.restaurantservice.Utilities;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice
public class RestaurantExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> entityNotFoundHandler(EntityNotFoundException exception){
        log.error(exception.getCause() + ", " + exception.getMessage());
        if(exception.getMessage() == null || exception.getCause() == null || exception.getMessage().isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("entity not found");
        else return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getCause() + ", " + exception.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> exceptionHandler(Exception exception){
        log.error(exception.getCause() + ", " + exception.getMessage());
        if(exception.getMessage() == null || exception.getCause() == null || exception.getMessage().isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("unknown exception");
        else return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getCause() + ", " + exception.getMessage());
    }
}