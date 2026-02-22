package com.smartpark.api.controllers;

import java.util.List;

import com.smartpark.api.dto.LotDto;
import com.smartpark.api.dto.VehicleDto;
import com.smartpark.api.exceptions.*;
import com.smartpark.api.service.ParkingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/")
public class ParkingController
{
    private ParkingService parkingService;

    @Autowired
    public ParkingController(ParkingService parkingService)
    {
        this.parkingService = parkingService;
    }

    @PostMapping("lot/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<LotDto> registerParkingLot(@RequestBody LotDto lotDto) throws InvalidLotRegistrationException
    {
        return new ResponseEntity<>(parkingService.registerLot(lotDto), HttpStatus.CREATED);
    }

    @PostMapping("vehicle/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<VehicleDto> registerVehicle(@RequestBody VehicleDto vehicleDto) throws InvalidVehicleRegistrationException
    {
        return new ResponseEntity<>(parkingService.registerVehicle(vehicleDto), HttpStatus.CREATED);
    }

    @PutMapping("vehicle/checkin/{id}/{plate}")
    public ResponseEntity<VehicleDto> checkInVehicle(@PathVariable("id") String id,
                                                     @PathVariable("plate") String plate)
            throws LotNotFoundException, VehicleNotFoundException, VehicleCheckInException, FullParkingLotException
    {
        return new ResponseEntity<>(parkingService.checkInVehicle(id, plate), HttpStatus.OK);
    }

    @PutMapping("vehicle/checkout/{id}/{plate}")
    public ResponseEntity<VehicleDto> checkOutVehicle(@PathVariable("id") String id,
                                                      @PathVariable("plate") String plate)
            throws LotNotFoundException, VehicleNotFoundException, VehicleCheckOutException
    {
        return new ResponseEntity<>(parkingService.checkOutVehicle(id, plate), HttpStatus.OK);
    }

    @GetMapping("lot/view/{id}")
    public ResponseEntity<LotDto> viewLotAvailability(@PathVariable("id") String id)
            throws LotNotFoundException
    {
        return new ResponseEntity<>(parkingService.viewLotById(id), HttpStatus.OK);
    }

    @GetMapping("vehicle/view/{id}")
    public ResponseEntity<List<VehicleDto>> viewVehiclesByLotId(@PathVariable("id") String id)
            throws LotNotFoundException
    {
        return new ResponseEntity<>(parkingService.viewVehiclesByLotId(id), HttpStatus.OK);
    }
}
