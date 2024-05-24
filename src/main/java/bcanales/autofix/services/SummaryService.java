package bcanales.autofix.services;

import bcanales.autofix.dtos.RepairDetailsDto;
import bcanales.autofix.dtos.VehicleBrandRepairAverageDto;
import bcanales.autofix.dtos.VehicleTypeMotorRepairsDto;
import bcanales.autofix.dtos.VehicleTypeRepairsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class SummaryService {
    @Autowired
    RepairService repairService;
}
