package com.srivath.product.controllerAdvices;

import com.srivath.product.DTOs.ExceptionDTO;
import com.srivath.product.exceptions.ProductNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlers {

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ExceptionDTO> handleProductNotFoundException(Exception e)
    {
        return new ResponseEntity<>(new ExceptionDTO(e.getMessage()),HttpStatus.NOT_FOUND);
    }
}
