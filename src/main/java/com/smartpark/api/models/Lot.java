package com.smartpark.api.models;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "lot")
public class Lot
{
    @Id
    @Column(length = 50)
    private String id;
    private String location;
    private long capacity;
    private long occupiedSpaces;

    @OneToMany(mappedBy = "lot", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Vehicle> vehicles = new ArrayList<Vehicle>();

    public Lot()
    {
    }

    public Lot(String id, String location, long capacity)
    {
        this.id = id;
        this.location = location;
        this.capacity = capacity;
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

    public List<Vehicle> getVehicles()
    {
        return vehicles;
    }

    public void setVehicles(List<Vehicle> vehicles)
    {
        this.vehicles = vehicles;
    }
}
