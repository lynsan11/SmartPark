package com.smartpark.api.dto;

import com.smartpark.api.enums.Type;

public class VehicleDto
{
    private String plate;
    private Type type;
    private String ownerName;

    public VehicleDto()
    {
    }

    public VehicleDto(String plate, Type type, String ownerName)
    {
        this.plate = plate;
        this.type = type;
        this.ownerName = ownerName;
    }

    public String getPlate()
    {
        return plate;
    }

    public void setPlate(String plate)
    {
        this.plate = plate;
    }

    public Type getType()
    {
        return type;
    }

    public void setType(Type type)
    {
        this.type = type;
    }

    public String getOwnerName()
    {
        return ownerName;
    }

    public void setOwnerName(String ownerName)
    {
        this.ownerName = ownerName;
    }
}
