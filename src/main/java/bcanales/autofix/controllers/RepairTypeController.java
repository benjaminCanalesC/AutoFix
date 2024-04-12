package bcanales.autofix.controllers;

import bcanales.autofix.entities.RepairTypeEntity;
import bcanales.autofix.services.RepairTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/repairTypes")
@CrossOrigin("*")
public class RepairTypeController {
    @Autowired
    RepairTypeService repairTypeService;

    @PostMapping("/")
    public ResponseEntity<RepairTypeEntity> saveRepairType(@RequestBody RepairTypeEntity repairType) {
        RepairTypeEntity newRepairType = repairTypeService.saveRepairType(repairType);
        return ResponseEntity.ok(newRepairType);
    }

    @GetMapping("/")
    public ResponseEntity<List<RepairTypeEntity>> getRepairTypes() {
        List<RepairTypeEntity> repairTypes = repairTypeService.getRepairTypes();
        return ResponseEntity.ok(repairTypes);
    }
}
