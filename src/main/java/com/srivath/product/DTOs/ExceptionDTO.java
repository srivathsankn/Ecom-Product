package com.srivath.product.DTOs;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExceptionDTO {
    private String errorMessage;
    //private Exception exception;

    public ExceptionDTO(String errorMessage) {
        this.errorMessage = errorMessage;
    }

}
