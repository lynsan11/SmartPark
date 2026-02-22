package com.smartpark.api.exceptions;

public class VehicleCheckOutException extends RuntimeException
{
    private static final long serialVersionUID = 1;

    public VehicleCheckOutException(String message)
    {
        super(message);
    }
}
