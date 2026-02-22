package com.smartpark.api.exceptions;

public class VehicleCheckInException extends RuntimeException
{
    private static final long serialVersionUID = 1;

    public VehicleCheckInException(String message)
    {
        super(message);
    }
}
