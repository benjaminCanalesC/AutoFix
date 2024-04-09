package bcanales.autofix.services;

import bcanales.autofix.entities.VehicleBrandEntity;
import bcanales.autofix.repositories.VehicleBrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class VehicleBrandService {
    @Autowired
    VehicleBrandRepository vehicleBrandRepository;

    public VehicleBrandEntity saveVehicleBrand(VehicleBrandEntity vehicleBrand) {
        return vehicleBrandRepository.save(vehicleBrand);
    }

    public ArrayList<VehicleBrandEntity> getVehicleBrands() {
        return (ArrayList<VehicleBrandEntity>) vehicleBrandRepository.findAll();
    }

    public Optional<VehicleBrandEntity> getVehicleBrandById(Long id) {
        return vehicleBrandRepository.findById(id);
    }
}
