package bcanales.autofix.controllers;

import bcanales.autofix.entities.VehicleEntity;
import bcanales.autofix.services.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/vehicles")
@CrossOrigin("*")
public class VehicleController {
    @Autowired
    VehicleService vehicleService;

    @PostMapping("/")
    public ResponseEntity<VehicleEntity> saveVehicle(@RequestBody VehicleEntity vehicle) throws Exception {
        VehicleEntity newVehicle = vehicleService.saveVehicle(vehicle);
        return ResponseEntity.ok(newVehicle);
    }

    @GetMapping("/")
    public ResponseEntity<List<VehicleEntity>> getVehicles() {
        List<VehicleEntity> vehicles = vehicleService.getVehicles();
        return ResponseEntity.ok(vehicles);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VehicleEntity> getVehicleById(@PathVariable Long id) {
        VehicleEntity vehicle = vehicleService.getVehicleById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Vehicle not found"));
        return  ResponseEntity.ok(vehicle);
    }

    @PutMapping("/")
    public ResponseEntity<VehicleEntity> updateVehicle(@RequestBody VehicleEntity vehicle) {
        VehicleEntity newVehicle = vehicleService.updateVehicle(vehicle);
        return  ResponseEntity.ok(newVehicle);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteVehicleById(@PathVariable Long id) throws Exception {
        vehicleService.deleteVehicle(id);
        return ResponseEntity.noContent().build();
    }
}
