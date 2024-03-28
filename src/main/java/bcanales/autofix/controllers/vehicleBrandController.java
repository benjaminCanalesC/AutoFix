package bcanales.autofix.controllers;

import bcanales.autofix.entities.vehicleBrandEntity;
import bcanales.autofix.services.vehicleBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vehicleBrands")
@CrossOrigin("*")
public class vehicleBrandController {
    @Autowired
    vehicleBrandService vehicleBrandService;

    @PostMapping("/")
    public ResponseEntity<vehicleBrandEntity> saveVehicleBrand(@RequestBody vehicleBrandEntity vehicleBrand) {
        vehicleBrandEntity newVehicleBrand = vehicleBrandService.saveVehicleBrand(vehicleBrand);
        return ResponseEntity.ok(newVehicleBrand);
    }
}
