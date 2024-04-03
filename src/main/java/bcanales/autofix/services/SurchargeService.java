package bcanales.autofix.services;

import bcanales.autofix.entities.VehicleEntity;
import bcanales.autofix.repositories.RepairRepository;
import bcanales.autofix.repositories.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Year;

@Service
public class SurchargeService {
    @Autowired
    VehicleRepository vehicleRepository;

    public double surchargeByMileage(VehicleEntity vehicle) throws Exception {
        String vehicleType = vehicle.getVehicleType().getType();
        int mileage = vehicle.getMileage();

        if (vehicleType.equals("Sedan") || vehicleType.equals("Hatchback")) {
            if (mileage <= 5000) { return 0; }
            else if (mileage <= 12000) { return 0.03; }
            else if (mileage <= 25000) { return 0.07; }
            else if (mileage <= 40000) { return 0.12; }
            else { return 0.2; }
        } else if (vehicleType.equals("SUV") || vehicleType.equals("Pickup") || vehicleType.equals("Van")) {
            if (mileage <= 5000) { return 0; }
            else if (mileage <= 12000) { return 0.05; }
            else if (mileage <= 25000) { return 0.09; }
            else if (mileage <= 40000) { return 0.12; }
            else { return 0.2; }
        } else {
            throw new Exception("Invalid Vehicle Type");
        }
    }

    public double surchargeByVehicleYears(VehicleEntity vehicle) throws Exception {
        int fabricationYear = vehicle.getFabricationYear();
        int actualYear = Year.now().getValue();

        int vehicleYears = actualYear - fabricationYear;

        String vehicleType = vehicle.getVehicleType().getType();

        if (vehicleType.equals("Sedan") || vehicleType.equals("Hatchback")) {
            if (vehicleYears <= 5) { return 0; }
            else if (vehicleYears <= 10) { return 0.05; }
            else if (vehicleYears <= 15) { return 0.09; }
            else { return 0.15; }
        } else if (vehicleType.equals("SUV") || vehicleType.equals("Pickup") || vehicleType.equals("Van")) {
            if (vehicleYears <= 5) { return 0; }
            else if (vehicleYears <= 10) { return 0.07; }
            else if (vehicleYears <= 15) { return 0.11; }
            else { return 0.2; }
        } else {
            throw new Exception("Invalid Vehicle Type");
        }
    }
}
