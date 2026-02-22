package com.smartpark.api.dataloader;

import com.smartpark.api.enums.Type;
import com.smartpark.api.models.Lot;
import com.smartpark.api.models.Vehicle;
import com.smartpark.api.repository.LotRepository;
import com.smartpark.api.repository.VehicleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

@Component
public class DataLoader implements CommandLineRunner
{

    private LotRepository lotRepository;
    private VehicleRepository vehicleRepository;

    @Autowired
    public DataLoader(LotRepository lotRepository, VehicleRepository vehicleRepository)
    {
        this.lotRepository = lotRepository;
        this.vehicleRepository = vehicleRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        loadLotData();
        loadVehicleData();

        System.out.println("Initial data loaded into H2 database.");
    }

    private void loadLotData()
    {
        lotRepository.save(new Lot("A", "Quezon City", 1));
        lotRepository.save(new Lot("B", "Ortigas", 2));
        lotRepository.save(new Lot("C", "Makati", 3));
    }

    private void loadVehicleData()
    {
        vehicleRepository.save(new Vehicle("ABC123", Type.Car, "Lynette"));
        vehicleRepository.save(new Vehicle("DEF456", Type.Motorcycle,"Vinella"));
        vehicleRepository.save(new Vehicle("GHI789", Type.Truck,"Uni"));
    }
}
