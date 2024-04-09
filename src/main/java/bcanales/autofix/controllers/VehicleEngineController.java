package bcanales.autofix.controllers;

import bcanales.autofix.entities.VehicleEngineEntity;
import bcanales.autofix.services.VehicleEngineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehicleEngines")
@CrossOrigin("*")
public class VehicleEngineController {
    @Autowired
    VehicleEngineService vehicleEngineService;

    @PostMapping("/")
    public ResponseEntity<VehicleEngineEntity> saveVehicleEngine(@RequestBody VehicleEngineEntity vehicleEngine) {
        VehicleEngineEntity newVehicleEngine = vehicleEngineService.saveVehicleEngine(vehicleEngine);
        return ResponseEntity.ok(newVehicleEngine);
    }

    @GetMapping("/")
    public ResponseEntity<List<VehicleEngineEntity>> getVehicleEngines() {
        List<VehicleEngineEntity> vehicleEngines = vehicleEngineService.getVehicleEngines();
        return ResponseEntity.ok(vehicleEngines);
    }
}
