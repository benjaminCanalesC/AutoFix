package bcanales.autofix.controllers;

import bcanales.autofix.entities.VehicleTypeEntity;
import bcanales.autofix.services.VehicleTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehicleTypes")
@CrossOrigin("*")
public class VehicleTypeController {
    @Autowired
    VehicleTypeService vehicleTypeService;

    @PostMapping("/")
    public ResponseEntity<VehicleTypeEntity> saveVehicleEngine(@RequestBody VehicleTypeEntity vehicleType) {
        VehicleTypeEntity newVehicleType = vehicleTypeService.saveVehicleType(vehicleType);
        return ResponseEntity.ok(newVehicleType);
    }

    @GetMapping("/")
    public ResponseEntity<List<VehicleTypeEntity>> getVehicleTypes() {
        List<VehicleTypeEntity> vehicleTypes = vehicleTypeService.getVehicleTypes();
        return ResponseEntity.ok(vehicleTypes);
    }
}
