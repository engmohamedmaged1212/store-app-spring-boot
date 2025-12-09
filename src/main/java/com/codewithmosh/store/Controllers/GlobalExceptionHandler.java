package com.codewithmosh.store.Controllers;

import com.codewithmosh.store.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String , String>> handleValidationErrors(
            MethodArgumentNotValidException exception
    ){
        var errors = new HashMap<String ,String>();
        exception.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(error.getField() , error.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(CartNotFoundException.class)
    public ResponseEntity<Map<String , String>> handleCartNotFound(CartNotFoundException ex) {

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(
                        Map.of("error" , ex.getMessage())
                );
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<Map<String , String>> handleProductNotFound(ProductNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(Map.of("error" ,ex.getMessage()));
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map<String , String>> handleUserNotFound(UserNotFoundException ex){
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(Map.of("error" , ex.getMessage() ));
    }
    @ExceptionHandler(EmailAlreadyRegisteredException.class)
    public ResponseEntity<Map<String , String>> handleEmailAlreadyRegistered(EmailAlreadyRegisteredException ex){
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.of("error" , ex.getMessage() ));
    }

    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<Map<String , String>> handleInvalidPassword(InvalidPasswordException ex){
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("error" , ex.getMessage() ));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Map<String , String>> handleBadCredentialsException(BadCredentialsException ex){
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("error" , "The password or email isn't correct" ));
    }

    //CartEmptyException
    @ExceptionHandler(CartEmptyException.class)
    public ResponseEntity<Map<String , String>> handleCartEmptyException(CartEmptyException ex){
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.of("error" , ex.getMessage() ));
    }
}


