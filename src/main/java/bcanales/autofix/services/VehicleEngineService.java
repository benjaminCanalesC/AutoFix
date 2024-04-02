package bcanales.autofix.services;

import bcanales.autofix.entities.VehicleEngineEntity;
import bcanales.autofix.repositories.VehicleEngineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VehicleEngineService {
    @Autowired
    VehicleEngineRepository vehicleEngineRepository;

    public VehicleEngineEntity saveVehicleEngine(VehicleEngineEntity vehicleEngine) {
        return vehicleEngineRepository.save(vehicleEngine);
    }
}
