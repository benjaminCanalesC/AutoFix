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
}
