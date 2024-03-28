package bcanales.autofix.controllers;

import bcanales.autofix.entities.vehicleTypeEntity;
import bcanales.autofix.services.vehicleTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vehicleTypes")
@CrossOrigin("*")
public class vehicleTypeController {
    @Autowired
    vehicleTypeService vehicleTypeService;

    @PostMapping("/")
    public ResponseEntity<vehicleTypeEntity> saveVehicleEngine(@RequestBody vehicleTypeEntity vehicleType) {
        vehicleTypeEntity newVehicleType = vehicleTypeService.saveVehicleType(vehicleType);
        return ResponseEntity.ok(newVehicleType);
    }
}
