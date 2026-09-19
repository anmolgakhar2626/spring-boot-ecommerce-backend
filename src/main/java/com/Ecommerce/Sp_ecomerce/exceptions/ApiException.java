package com.Ecommerce.Sp_ecomerce.exceptions;

public class ApiException extends RuntimeException{
    public ApiException() {
    }

    private final static Long SerialNo=1L;

    public ApiException(String message) {
        super(message);
    }
}
