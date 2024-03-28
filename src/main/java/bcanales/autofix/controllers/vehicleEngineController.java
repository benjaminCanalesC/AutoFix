package bcanales.autofix.controllers;

import bcanales.autofix.entities.vehicleEngineEntity;
import bcanales.autofix.services.vehicleEngineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vehicleEngines")
@CrossOrigin("*")
public class vehicleEngineController {
    @Autowired
    vehicleEngineService vehicleEngineService;

    @PostMapping("/")
    public ResponseEntity<vehicleEngineEntity> saveVehicleEngine(@RequestBody vehicleEngineEntity vehicleEngine) {
        vehicleEngineEntity newVehicleEngine = vehicleEngineService.saveVehicleEngine(vehicleEngine);
        return ResponseEntity.ok(newVehicleEngine);
    }
}
