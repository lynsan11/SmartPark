package com.smartpark.api.exceptions;

public class LotNotFoundException extends RuntimeException
{
    private static final long serialVersionUID = 1;

    public LotNotFoundException(String message) {
        super(message);
    }
}
