package bcanales.autofix.controllers;

import bcanales.autofix.entities.vehicleEntity;
import bcanales.autofix.services.vehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehicles")
@CrossOrigin("*")
public class vehicleController {
    @Autowired
    vehicleService vehicleService;

    @PostMapping("/")
    public ResponseEntity<vehicleEntity> saveVehicle(@RequestBody vehicleEntity vehicle) throws Exception {
        vehicleEntity newVehicle = vehicleService.saveVehicle(vehicle);
        return ResponseEntity.ok(newVehicle);
    }

    @GetMapping("/")
    public ResponseEntity<List<vehicleEntity>> getVehicles() {
        List<vehicleEntity> vehicles = vehicleService.getVehicles();
        return ResponseEntity.ok(vehicles);
    }
}
