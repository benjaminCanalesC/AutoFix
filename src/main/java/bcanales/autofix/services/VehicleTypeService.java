package bcanales.autofix.services;

import bcanales.autofix.entities.VehicleBrandEntity;
import bcanales.autofix.entities.VehicleTypeEntity;
import bcanales.autofix.repositories.VehicleTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class VehicleTypeService {
    @Autowired
    VehicleTypeRepository vehicleTypeRepository;

    public VehicleTypeEntity saveVehicleType(VehicleTypeEntity vehicleType) {
        return vehicleTypeRepository.save(vehicleType);
    }

    public ArrayList<VehicleTypeEntity> getVehicleTypes() {
        return (ArrayList<VehicleTypeEntity>) vehicleTypeRepository.findAll();
    }
}
