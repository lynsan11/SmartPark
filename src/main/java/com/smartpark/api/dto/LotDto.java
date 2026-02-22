package com.smartpark.api.dto;

public class LotDto
{
    private String id;
    private String location;
    private long capacity;
    private long occupiedSpaces;
    private boolean isAvailable;

    public LotDto()
    {
    }

    public LotDto(String id, String location, long capacity, long occupiedSpaces)
    {
        this.id = id;
        this.location = location;
        this.capacity = capacity;
        this.occupiedSpaces = occupiedSpaces;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getLocation()
    {
        return location;
    }

    public void setLocation(String location)
    {
        this.location = location;
    }

    public long getCapacity()
    {
        return capacity;
    }

    public void setCapacity(long capacity)
    {
        this.capacity = capacity;
    }

    public long getOccupiedSpaces()
    {
        return occupiedSpaces;
    }

    public void setOccupiedSpaces(long occupiedSpaces)
    {
        this.occupiedSpaces = occupiedSpaces;
    }

    public boolean isAvailable()
    {
        return isAvailable;
    }

    public void setAvailable(boolean available)
    {
        isAvailable = available;
    }
}
