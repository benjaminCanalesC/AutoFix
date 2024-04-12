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
    public List<VehicleBrandRepairAverageDto> getAverageRepairTimeByBrand() {
        List<Object[]> results = repairService.getAverageRepairTimeByBrand();
        List<VehicleBrandRepairAverageDto> dtoList = new ArrayList<>();
        for (Object[] result : results) {
            String brand = (String) result[0];
            BigDecimal averageRepairTimeBD = (BigDecimal) result[1];
            Double averageRepairTime = averageRepairTimeBD.doubleValue();
            dtoList.add(new VehicleBrandRepairAverageDto(brand, averageRepairTime));
        }
        return dtoList;
    }

    public List<VehicleTypeMotorRepairsDto> getRepairsEngineType() {
        List<Object[]> results = repairService.getRepairsEngineType();
        List<VehicleTypeMotorRepairsDto> dtoList = new ArrayList<>();
        for (Object[] result : results) {
            String repairType = (String) result[0];
            String engineType = (String) result[1];
            Long numberOfVehicles = (Long) result[2];
            Long totalAmount = (Long) result[3];
            dtoList.add(new VehicleTypeMotorRepairsDto(repairType, engineType, numberOfVehicles, totalAmount));
        }
        return dtoList;
    }

    public List<RepairDetailsDto> getRepairDetails() {
        List<Object[]> results = repairService.getRepairDetails();
        List<RepairDetailsDto> dtoList = new ArrayList<>();
        for (Object[] result : results) {
            String plate = (String) result[0];
            Integer baseRepairCost = (Integer) result[1];
            Integer discount = (Integer) result[2];
            Integer surcharge = (Integer) result[3];
            Integer iva = (Integer) result[4];
            Integer totalCost = (Integer) result[5];
            dtoList.add(new RepairDetailsDto(plate, baseRepairCost, discount, surcharge, iva, totalCost));
        }
        return dtoList;
    }

    public List<VehicleTypeRepairsDto> getRepairsVehicleType() {
        List<Object[]> results = repairService.getRepairsVehicleType();
        List<VehicleTypeRepairsDto> dtoList = new ArrayList<>();
        for (Object[] result : results) {
            String repairType = (String) result[0];
            String vehicleType = (String) result[1];
            Long vehicleCount = (Long) result[2];
            Long totalCost = (Long) result[3];
            dtoList.add(new VehicleTypeRepairsDto(repairType, vehicleType, vehicleCount, totalCost));
        }
        return dtoList;
    }
}
