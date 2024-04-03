package bcanales.autofix.controllers;

import bcanales.autofix.entities.RepairEntity;
import bcanales.autofix.services.RepairService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/repairs")
@CrossOrigin("*")
public class RepairController {
    @Autowired
    RepairService repairService;

    @PostMapping("/")
    public ResponseEntity<RepairEntity> saveRepair(@RequestBody RepairEntity repair) throws Exception {
        RepairEntity newRepair = repairService.saveRepair(repair);
        return  ResponseEntity.ok(newRepair);
    }

    @GetMapping("/")
    public ResponseEntity<List<RepairEntity>> getRepairs() {
        List<RepairEntity> repairs = repairService.getRepairs();
        return ResponseEntity.ok(repairs);
    }
}
