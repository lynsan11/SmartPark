package com.smartpark.api.service;

import java.util.List;

import com.smartpark.api.dto.LotDto;
import com.smartpark.api.dto.VehicleDto;
import com.smartpark.api.exceptions.LotNotFoundException;

public interface ParkingService
{
    LotDto registerLot(LotDto pokemonDto);
    VehicleDto registerVehicle(VehicleDto vehicleDto);
    VehicleDto checkInVehicle(String lotId, String plate);
    VehicleDto checkOutVehicle(String lotId, String plate);
    LotDto viewLotById(String id);
    List<VehicleDto> viewVehiclesByLotId(String id);
}
