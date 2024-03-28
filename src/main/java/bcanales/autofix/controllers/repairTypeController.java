package bcanales.autofix.controllers;

import bcanales.autofix.entities.repairTypeEntity;
import bcanales.autofix.services.repairTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/repairTypes")
@CrossOrigin("*")
public class repairTypeController {
    @Autowired
    repairTypeService repairTypeService;

    @PostMapping("/")
    public ResponseEntity<repairTypeEntity> saveRepairType(@RequestBody repairTypeEntity repairType) {
        repairTypeEntity newRepairType = repairTypeService.saveRepairType(repairType);
        return ResponseEntity.ok(newRepairType);
    }
}
