package bcanales.autofix.services;

import bcanales.autofix.entities.RepairEntity;
import bcanales.autofix.entities.RepairTypeEntity;
import bcanales.autofix.entities.VehicleEntity;
import bcanales.autofix.repositories.RepairRepository;
import bcanales.autofix.repositories.VehicleEngineRepository;
import bcanales.autofix.repositories.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

@Service
public class RepairService {
    @Autowired
    RepairRepository repairRepository;
    @Autowired
    VehicleEngineService vehicleEngineService;
    @Autowired
    VehicleService vehicleService;
    @Autowired
    RepairTypeService repairTypeService;
    @Autowired
    DiscountService discountService;
    @Autowired
    SurchargeService surchargeService;

    public RepairEntity saveRepair(RepairEntity repair) throws Exception {
        int repairCost = calculateRepairCost(repair);
        repair.setRepairCost(repairCost);
        return repairRepository.save(repair);
    }

    public ArrayList<RepairEntity> getRepairs() {
        return (ArrayList<RepairEntity>) repairRepository.findAll();
    }

    public int countByVehicleId(Long vehicleId) {
        return repairRepository.countByVehicleId(vehicleId);
    }

    public int calculateRepairCost(RepairEntity repair) throws Exception {
        Long vehicleId = repair.getVehicle().getId();
        VehicleEntity vehicle = vehicleService.getVehicleById(vehicleId).get();

        String vehicleEngine = vehicle.getVehicleEngine().getEngine();

        int repairCost;

        if (vehicleEngine.equals("Gasoline")) {
            Long repairTypeId = repair.getRepairType().getId();
            RepairTypeEntity repairType = repairTypeService.getRepairTypeById(repairTypeId).get();
            repairCost = repairType.getGasolineCost();
        } else if (vehicleEngine.equals("Diesel")) {
            Long repairTypeId = repair.getRepairType().getId();
            RepairTypeEntity repairType = repairTypeService.getRepairTypeById(repairTypeId).get();
            repairCost = repairType.getDieselCost();
        } else if (vehicleEngine.equals("Hybrid")) {
            Long repairTypeId = repair.getRepairType().getId();
            RepairTypeEntity repairType = repairTypeService.getRepairTypeById(repairTypeId).get();
            repairCost = repairType.getHybridCost();
        } else if (vehicleEngine.equals("Electric")) {
            Long repairTypeId = repair.getRepairType().getId();
            RepairTypeEntity repairType = repairTypeService.getRepairTypeById(repairTypeId).get();
            repairCost = repairType.getElectricCost();
        } else {
            throw new Exception("Invalid Engine");
        }

        double discount = discountService.discountByRepairs(vehicle) +
                discountService.discountByAttentionDays(repair);

        double surcharge = surchargeService.surchargeByMileage(vehicle) +
                surchargeService.surchargeByVehicleYears(vehicle);

        int totalCost = (int) Math.round(repairCost - (discount * repairCost) + (surcharge * repairCost));

        return totalCost;

        //totalCost = [ repair.cost - (discount * repair.cost) + (recharge * repair.cost) ] * (IVA * repair.cost)
    }
}
