package com.smartpark.api.models;

import com.smartpark.api.enums.Type;
import jakarta.persistence.*;

@Entity
@Table(name = "vehicles")
public class Vehicle
{
    @Id
    @Column(length = 50)
    private String plate;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private Type type;
    private String ownerName;
    @ManyToOne(fetch =  FetchType.LAZY)
    @JoinColumn(name = "lot_id")
    private Lot lot;

    public Vehicle()
    {
    }

    public Vehicle(String plate, Type type, String ownerName)
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

    public Lot getLot()
    {
        return lot;
    }

    public void setLot(Lot lot)
    {
        this.lot = lot;
    }
}
