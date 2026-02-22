package com.smartpark.api.exceptions;

public class InvalidVehicleRegistrationException extends RuntimeException
{
    private static final long serialVersionUID = 1;

    public InvalidVehicleRegistrationException(String message)
    {
        super(message);
    }
}
