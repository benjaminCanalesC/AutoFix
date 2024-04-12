package bcanales.autofix.controllers;

import bcanales.autofix.dtos.RepairDetailsDto;
import bcanales.autofix.dtos.VehicleBrandRepairAverageDto;
import bcanales.autofix.dtos.VehicleTypeMotorRepairsDto;
import bcanales.autofix.dtos.VehicleTypeRepairsDto;
import bcanales.autofix.services.SummaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/summaries")
@CrossOrigin("*")
public class SummaryController {
    @Autowired
    SummaryService summaryService;
    @GetMapping("/averageTimeByBrandReport")
    public ResponseEntity<List<VehicleBrandRepairAverageDto>> getAverageTimeByBrandSummary() {
        List<VehicleBrandRepairAverageDto> repairs = summaryService.getAverageRepairTimeByBrand();
        return ResponseEntity.ok(repairs);
    }

    @GetMapping("/RepairsByEngineType")
    public ResponseEntity<List<VehicleTypeMotorRepairsDto>> getTypeMotorRepairsSummary() {
        List<VehicleTypeMotorRepairsDto> repairs = summaryService.getRepairsEngineType();
        return ResponseEntity.ok(repairs);
    }

    @GetMapping("/repairDetails")
    public ResponseEntity<List<RepairDetailsDto>> getRepairDetailsSummary() {
        List<RepairDetailsDto> repairs = summaryService.getRepairDetails();
        return ResponseEntity.ok(repairs);
    }

    @GetMapping("/vehicleTypeByRepairs")
    public ResponseEntity<List<VehicleTypeRepairsDto>> getRepairVehicleTypeSummary() {
        List<VehicleTypeRepairsDto> repairs = summaryService.getRepairsVehicleType();
        return ResponseEntity.ok(repairs);
    }
}
