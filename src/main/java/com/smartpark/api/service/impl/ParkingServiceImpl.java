package com.smartpark.api.service.impl;

import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.smartpark.api.dto.LotDto;
import com.smartpark.api.dto.VehicleDto;
import com.smartpark.api.exceptions.*;
import com.smartpark.api.models.Lot;
import com.smartpark.api.models.Vehicle;
import com.smartpark.api.repository.LotRepository;
import com.smartpark.api.repository.VehicleRepository;
import com.smartpark.api.service.ParkingService;
import com.smartpark.api.enums.Type;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ParkingServiceImpl implements ParkingService {
    private final String PLATE_REGEX = "^[a-zA-Z0-9-]+$";
    private final String NAME_REGEX = "^[a-zA-Z ]+$";
    LotRepository lotRepository;
    VehicleRepository vehicleRepository;


    @Autowired
    public ParkingServiceImpl(LotRepository lotRepository, VehicleRepository vehicleRepository) {
        this.lotRepository = lotRepository;
        this.vehicleRepository = vehicleRepository;
    }

    @Override
    public LotDto registerLot(LotDto lotDto) throws InvalidLotRegistrationException
    {
        if (Objects.isNull(lotDto) || !isValidLotId(lotDto.getId()) || StringUtils.isBlank(lotDto.getLocation())
            || lotDto.getCapacity() < 1)
        {
            throw new InvalidLotRegistrationException(ExceptionConstants.INVALID_LOT);
        }

        Lot lot = mapToLotEntity(lotDto);
        Lot newLot = lotRepository.save(lot);

        return mapToLotDto(newLot);
    }

    @Override
    public VehicleDto registerVehicle(VehicleDto vehicleDto) throws InvalidVehicleRegistrationException {
        if (Objects.isNull(vehicleDto) || !isValidPlate(vehicleDto.getPlate()) || !isValidType(vehicleDto.getType())
                || !isValidName(vehicleDto.getOwnerName()))
        {
            throw new InvalidVehicleRegistrationException(ExceptionConstants.INVALID_VEHICLE);
        }

        Vehicle vehicle = mapToVehicleEntity(vehicleDto);
        Vehicle newVehicle = vehicleRepository.save(vehicle);

        return mapToVehicleDto(newVehicle);
    }

    @Override
    public VehicleDto checkInVehicle(String lotId, String plate) throws LotNotFoundException, VehicleNotFoundException, VehicleCheckInException
    {
        Lot lot = lotRepository.findById(lotId).orElseThrow(() -> new LotNotFoundException(ExceptionConstants.LOT_NOT_FOUND));
        Vehicle vehicle = vehicleRepository.findById(plate).orElseThrow(() -> new VehicleNotFoundException(ExceptionConstants.VEHICLE_NOT_FOUND));

        List<Vehicle> registeredVehicles = lot.getVehicles();

        if (Objects.nonNull(vehicle.getLot()))
        {
            throw new VehicleCheckInException(ExceptionConstants.INVALID_CHECKIN);
        }
        else if (lot.getCapacity() == lot.getOccupiedSpaces())
        {
            throw new FullParkingLotException(ExceptionConstants.FULL_PARKING_LOT);
        }

        long occupiedSpaces = lot.getOccupiedSpaces() + 1;
        lot.setOccupiedSpaces(occupiedSpaces);
        registeredVehicles.add(vehicle);
        lot.setVehicles(registeredVehicles);
        lotRepository.save(lot);

        vehicle.setLot(lot);
        vehicleRepository.save(vehicle);

        return mapToVehicleDto(vehicle);
    }

    @Override
    public VehicleDto checkOutVehicle(String lotId, String plate) throws LotNotFoundException, VehicleNotFoundException, VehicleCheckOutException
    {
        Lot lot = lotRepository.findById(lotId).orElseThrow(() -> new LotNotFoundException(ExceptionConstants.LOT_NOT_FOUND));
        Vehicle vehicle = vehicleRepository.findById(plate).orElseThrow(() -> new VehicleNotFoundException(ExceptionConstants.VEHICLE_NOT_FOUND));

        List<Vehicle> registeredVehicles = lot.getVehicles();

        if (!registeredVehicles.contains(vehicle))
        {
            throw new VehicleCheckOutException(ExceptionConstants.INVALID_CHECKOUT);
        }

        long occupiedSpaces = lot.getOccupiedSpaces() - 1;
        lot.setOccupiedSpaces(occupiedSpaces);
        registeredVehicles.remove(vehicle);
        lot.setVehicles(registeredVehicles);
        lotRepository.save(lot);

        vehicle.setLot(null);
        vehicleRepository.save(vehicle);

        return mapToVehicleDto(vehicle);
    }

    public LotDto viewLotById(String id) throws LotNotFoundException
    {
        Lot lot = lotRepository.findById(id).orElseThrow(() -> new LotNotFoundException(ExceptionConstants.LOT_NOT_FOUND));

        LotDto lotDto = mapToLotDto(lot);
        lotDto.setAvailable(lotDto.getOccupiedSpaces() < lot.getCapacity());
        return lotDto;
    }

    @Override
    public List<VehicleDto> viewVehiclesByLotId(String id) throws LotNotFoundException
    {
        Lot lot = lotRepository.findById(id).orElseThrow(() -> new LotNotFoundException(ExceptionConstants.LOT_NOT_FOUND));

        return lot.getVehicles().stream().map(this::mapToVehicleDto).toList();
    }

    private boolean isValidLotId(String lotId)
    {
        return StringUtils.isNotBlank(lotId) && (lotId.length() <= 50);
    }

    private boolean isValidPlate(String plate)
    {
        if (StringUtils.isBlank(plate))
        {
            return false;
        }

        Pattern pattern = Pattern.compile(PLATE_REGEX);
        Matcher matcher = pattern.matcher(plate);

        return matcher.find();
    }

    private boolean isValidName(String name)
    {
        if (StringUtils.isBlank(name))
        {
            return false;
        }

        Pattern pattern = Pattern.compile(NAME_REGEX);
        Matcher matcher = pattern.matcher(name);

        return matcher.find();
    }

    private boolean isValidType(Type type)
    {
        if (Objects.isNull(type))
        {
            return false;
        }

        return Type.Car.equals(type) || Type.Motorcycle.equals(type) || Type.Truck.equals(type);
    }

    private LotDto mapToLotDto(Lot lot)
    {
        LotDto lotDto = new LotDto();
        lotDto.setId(lot.getId());
        lotDto.setLocation(lot.getLocation());
        lotDto.setCapacity(lot.getCapacity());
        lotDto.setOccupiedSpaces(lot.getOccupiedSpaces());

        return lotDto;
    }

    private Lot mapToLotEntity(LotDto lotDto)
    {
        Lot lot = new Lot();
        lot.setId(lotDto.getId());
        lot.setLocation(lotDto.getLocation());
        lot.setCapacity(lotDto.getCapacity());
        lot.setOccupiedSpaces(lotDto.getOccupiedSpaces());

        return lot;
    }

    private VehicleDto mapToVehicleDto(Vehicle vehicle)
    {
        VehicleDto vehicleDto = new VehicleDto();
        vehicleDto.setPlate(vehicle.getPlate());
        vehicleDto.setType(vehicle.getType());
        vehicleDto.setOwnerName(vehicle.getOwnerName());

        return vehicleDto;
    }

    private Vehicle mapToVehicleEntity(VehicleDto vehicleDto)
    {
        Vehicle vehicle = new Vehicle();
        vehicle.setPlate(vehicleDto.getPlate());
        vehicle.setType(vehicleDto.getType());
        vehicle.setOwnerName(vehicleDto.getOwnerName());

        return vehicle;
    }
}
