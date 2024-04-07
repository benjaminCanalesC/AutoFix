package bcanales.autofix.services;

import bcanales.autofix.entities.RepairEntity;
import bcanales.autofix.entities.RepairTypeEntity;
import bcanales.autofix.entities.VehicleEntity;
import bcanales.autofix.repositories.RepairRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

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
    @Autowired
    BrandDiscountService brandDiscountService;

    public RepairEntity saveRepair(RepairEntity repair) throws Exception {
        int repairCost = calculateRepairCost(repair);
        repair.setRepairCost(repairCost);
        return repairRepository.save(repair);
    }

    public ArrayList<RepairEntity> getRepairs() {
        return (ArrayList<RepairEntity>) repairRepository.findAll();
    }

    public RepairEntity updateRepair(RepairEntity repair) {
        RepairEntity existingRepair = repairRepository.findById(repair.getId())
                .orElseThrow(() -> new EntityNotFoundException("Repair with id " + repair.getId() + "does not exist."));

        if (repair.getExitDateTime() != null) {
            existingRepair.setExitDateTime(repair.getExitDateTime());
        }

        if (repair.getPickupDateTime() != null) {
            existingRepair.setPickupDateTime(repair.getPickupDateTime());

            double surchargeByPickupDelayPercentage = surchargeService.surchargeByPickupDelay(existingRepair);

            int surchargeByPickupDelay = (int) (surchargeByPickupDelayPercentage * existingRepair.getBaseRepairCost());

            // Se actualizan el recargo de la reparación
            int totalSurcharge = existingRepair.getSurcharge() + surchargeByPickupDelay;
            existingRepair.setSurcharge(totalSurcharge);

            // Se actualiza el costo total
            int totalCost = existingRepair.getRepairCost() + surchargeByPickupDelay;

            //Provisorio APLICACION DEL IVA
            int iva = (int) (totalCost * 0.19);
            existingRepair.setIva(iva);

            existingRepair.setRepairCost(totalCost + iva);
        }

        return repairRepository.save(existingRepair);
    }

    public int calculateRepairCost(RepairEntity repair) throws Exception {
        Long vehicleId = repair.getVehicle().getId();
        VehicleEntity vehicle = vehicleService.getVehicleById(vehicleId)
                .orElseThrow(() -> new EntityNotFoundException("Vehicle with id " + vehicleId + "does not exist."));

        String vehicleEngine = vehicle.getVehicleEngine().getEngine();

        int baseRepairCost;

        if (vehicleEngine.equals("Gasoline")) {
            Long repairTypeId = repair.getRepairType().getId();
            RepairTypeEntity repairType = repairTypeService.getRepairTypeById(repairTypeId).get();
            baseRepairCost = repairType.getGasolineCost();
        } else if (vehicleEngine.equals("Diesel")) {
            Long repairTypeId = repair.getRepairType().getId();
            RepairTypeEntity repairType = repairTypeService.getRepairTypeById(repairTypeId).get();
            baseRepairCost = repairType.getDieselCost();
        } else if (vehicleEngine.equals("Hybrid")) {
            Long repairTypeId = repair.getRepairType().getId();
            RepairTypeEntity repairType = repairTypeService.getRepairTypeById(repairTypeId).get();
            baseRepairCost = repairType.getHybridCost();
        } else if (vehicleEngine.equals("Electric")) {
            Long repairTypeId = repair.getRepairType().getId();
            RepairTypeEntity repairType = repairTypeService.getRepairTypeById(repairTypeId).get();
            baseRepairCost = repairType.getElectricCost();
        } else {
            throw new Exception("Invalid Engine");
        }

        repair.setBaseRepairCost(baseRepairCost);

        double discountPercentage = discountService.discountByRepairs(vehicle) +
                discountService.discountByAttentionDays(repair);
        int bonusDiscount = brandDiscountService.calculateBrandDiscount(repair);
        int discount = (int) Math.round(discountPercentage * baseRepairCost) + bonusDiscount;

        repair.setDiscount(discount);

        double surchargePercentage = surchargeService.surchargeByMileage(vehicle) +
                surchargeService.surchargeByVehicleYears(vehicle);
        int surcharge = (int) Math.round(surchargePercentage * baseRepairCost);

        repair.setSurcharge(surcharge);

        return baseRepairCost - discount + surcharge;
    }
}
