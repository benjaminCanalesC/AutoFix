package bcanales.autofix.controllers;

import bcanales.autofix.entities.RepairEntity;
import bcanales.autofix.services.RepairService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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

    @GetMapping("/{id}")
    public ResponseEntity<RepairEntity> getRepairById(@PathVariable Long id) {
        RepairEntity repair = repairService.getRepairById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Repair not found"));
        return  ResponseEntity.ok(repair);
    }

    @PutMapping("/")
    public ResponseEntity<RepairEntity> updateRepair(@RequestBody RepairEntity repair) {
        RepairEntity newRepair = repairService.updateRepair(repair);
        return ResponseEntity.ok(newRepair);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<RepairEntity> deleteRepair(@PathVariable Long id) throws Exception {
        repairService.deleteRepair(id);
        return ResponseEntity.noContent().build();
    }
}
