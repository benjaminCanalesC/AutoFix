package bcanales.autofix.services;

import bcanales.autofix.entities.vehicleEngineEntity;
import bcanales.autofix.repositories.vehicleEngineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class vehicleEngineService {
    @Autowired
    vehicleEngineRepository vehicleEngineRepository;

    public vehicleEngineEntity saveVehicleEngine(vehicleEngineEntity vehicleEngine) {
        return vehicleEngineRepository.save(vehicleEngine);
    }
}
