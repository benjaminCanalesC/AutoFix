package bcanales.autofix.services;

import bcanales.autofix.entities.VehicleBrandEntity;
import bcanales.autofix.entities.VehicleEngineEntity;
import bcanales.autofix.repositories.VehicleEngineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class VehicleEngineService {
    @Autowired
    VehicleEngineRepository vehicleEngineRepository;

    public VehicleEngineEntity saveVehicleEngine(VehicleEngineEntity vehicleEngine) {
        return vehicleEngineRepository.save(vehicleEngine);
    }

    public ArrayList<VehicleEngineEntity> getVehicleEngines() {
        return (ArrayList<VehicleEngineEntity>) vehicleEngineRepository.findAll();
    }
}
