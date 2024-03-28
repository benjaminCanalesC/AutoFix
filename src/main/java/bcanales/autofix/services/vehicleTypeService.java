package bcanales.autofix.services;

import bcanales.autofix.entities.vehicleTypeEntity;
import bcanales.autofix.repositories.vehicleTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class vehicleTypeService {
    @Autowired
    vehicleTypeRepository vehicleTypeRepository;

    public vehicleTypeEntity saveVehicleType(vehicleTypeEntity vehicleType) {
        return vehicleTypeRepository.save(vehicleType);
    }
}
