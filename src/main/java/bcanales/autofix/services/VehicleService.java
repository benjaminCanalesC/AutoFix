package bcanales.autofix.services;

import bcanales.autofix.entities.VehicleEntity;
import bcanales.autofix.repositories.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class VehicleService {
    @Autowired
    VehicleRepository vehicleRepository;
    public VehicleEntity saveVehicle(VehicleEntity vehicle) throws Exception {
        //Correctly plate analyze
        String plate = vehicle.getPlate();
        if (plate.length() != 6) {
            throw new Exception("Plate length does not match");
        }

        for (int i = 0; i < plate.length(); i++) {
            char c = plate.charAt(i);
            if (i < 4) {
                if (!Character.isLetter(c)) {
                    throw new Exception("Invalid plate format");
                }
            } else {
                if (!Character.isDigit(c)) {
                    throw new Exception("Invalid plate format");
                }
            }
        }

        return vehicleRepository.save(vehicle);
    }

    public ArrayList<VehicleEntity> getVehicles() {
        return (ArrayList<VehicleEntity>) vehicleRepository.findAll();
    }
}
