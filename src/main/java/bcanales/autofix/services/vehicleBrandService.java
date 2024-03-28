package bcanales.autofix.services;

import bcanales.autofix.entities.vehicleBrandEntity;
import bcanales.autofix.repositories.vehicleBrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class vehicleBrandService {
    @Autowired
    vehicleBrandRepository vehicleBrandRepository;

    public vehicleBrandEntity saveVehicleBrand(vehicleBrandEntity vehicleBrand) {
        return vehicleBrandRepository.save(vehicleBrand);
    }
}
