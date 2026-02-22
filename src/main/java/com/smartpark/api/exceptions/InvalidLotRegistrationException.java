package com.smartpark.api.exceptions;

public class InvalidLotRegistrationException extends RuntimeException
{
    private static final long serialVersionUID = 1;

    public InvalidLotRegistrationException(String message)
    {
        super(message);
    }
}
