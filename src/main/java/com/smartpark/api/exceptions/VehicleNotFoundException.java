package com.smartpark.api.exceptions;

public class VehicleNotFoundException extends RuntimeException
{
    private static final long serialVersionUID = 1;

    public VehicleNotFoundException(String message) {
        super(message);
    }
}
