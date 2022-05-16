package org.retail.store.services;

import org.retail.store.exception.BillNotFoundException;
import org.retail.store.exception.UserNotFoundException;
import org.retail.store.model.RetailStoreErrorResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ControllerAdvice
@RestController
public class RetailStoreCustomExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGenericExceptions(Exception e, WebRequest request){
        RetailStoreErrorResponse response = new RetailStoreErrorResponse(e.getMessage(),request.getDescription(false), new Date(), null);

        return  new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(BillNotFoundException.class)
    public final ResponseEntity<Object> handleBillNotFoundException(BillNotFoundException e, WebRequest request) {
        RetailStoreErrorResponse response = new RetailStoreErrorResponse( e.getMessage(), request.getDescription(false), new Date(), null);
        return new ResponseEntity(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public final ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException e, WebRequest request) {
        RetailStoreErrorResponse response = new RetailStoreErrorResponse( e.getMessage(), request.getDescription(false), new Date(), null);
        return new ResponseEntity(response, HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> errors = new ArrayList<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }
        for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
        }

        RetailStoreErrorResponse response = new RetailStoreErrorResponse( "Validation failed",
                "Check Error details", new Date(), errors);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}

