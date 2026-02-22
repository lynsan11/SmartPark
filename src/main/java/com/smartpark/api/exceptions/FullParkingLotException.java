package com.smartpark.api.exceptions;

public class FullParkingLotException extends RuntimeException
{
    private static final long serialVersionUID = 1;

    public FullParkingLotException(String message)
    {
        super(message);
    }
}
