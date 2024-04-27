package bcanales.autofix.services;

import bcanales.autofix.entities.RepairEntity;
import bcanales.autofix.entities.RepairTypeEntity;
import bcanales.autofix.entities.VehicleEntity;
import bcanales.autofix.repositories.RepairRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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
    @Autowired
    BrandDiscountService brandDiscountService;

    public RepairEntity saveRepair(RepairEntity repair) throws Exception {
        int repairCost = calculateRepairCost(repair);
        repair.setRepairCost(repairCost);
        return repairRepository.save(repair);
    }

    public Optional<RepairEntity> getRepairById(Long id) {
        return repairRepository.findById(id);
    }

    public ArrayList<RepairEntity> getRepairs() {
        return (ArrayList<RepairEntity>) repairRepository.findAll();
    }

    public List<Object[]> getAverageRepairTimeByBrand() { return repairRepository.findAverageRepairTimeByBrand(); }

    public List<Object[]> getRepairsEngineType() { return repairRepository.findRepairsByEngineType(); }

    public List<Object[]> getRepairDetails() { return repairRepository.findAllRepairDetails(); }

    public List<Object[]> getRepairsVehicleType() { return repairRepository.findRepairsByVehicleTypes(); }

    public boolean deleteRepair(Long id) throws Exception {
        try {
            repairRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public RepairEntity updateRepair(RepairEntity repair) {
        RepairEntity existingRepair = repairRepository.findById(repair.getId())
                .orElseThrow(() -> new EntityNotFoundException("Repair with id " + repair.getId() + " does not exist."));

        if (repair.getExitDateTime() != existingRepair.getExitDateTime()) {
            existingRepair.setExitDateTime(repair.getExitDateTime());
        }

        if (repair.getPickupDateTime() != existingRepair.getPickupDateTime()) {
            existingRepair.setPickupDateTime(repair.getPickupDateTime());

            double surchargeByPickupDelayPercentage = surchargeService.surchargeByPickupDelay(existingRepair);

            int surchargeByPickupDelay = (int) (surchargeByPickupDelayPercentage * existingRepair.getBaseRepairCost());

            // Se actualizan el recargo de la reparación
            int totalSurcharge = existingRepair.getSurcharge() + surchargeByPickupDelay;
            existingRepair.setSurcharge(totalSurcharge);

            // Se actualiza el costo total
            int totalCost = existingRepair.getRepairCost() + surchargeByPickupDelay;

            // Aplicación del IVa
            int iva = (int) (totalCost * 0.19);
            existingRepair.setIva(iva);

            existingRepair.setRepairCost(totalCost + iva);
        }

        return repairRepository.save(existingRepair);
    }

    public int calculateRepairCost(RepairEntity repair) throws Exception {
        String vehiclePlate = repair.getVehicle().getPlate();
        VehicleEntity vehicle = vehicleService.getVehicleByPlate(vehiclePlate)
                .orElseThrow(() -> new EntityNotFoundException("Vehicle with plate " + vehiclePlate + "does not exist."));

        repair.setVehicle(vehicle);

        String vehicleEngine = vehicle.getVehicleEngine().getEngine();

        int baseRepairCost;

        if (vehicleEngine.equals("Gasolina")) {
            Long repairTypeId = repair.getRepairType().getId();
            RepairTypeEntity repairType = repairTypeService.getRepairTypeById(repairTypeId).get();
            baseRepairCost = repairType.getGasolineCost();
        } else if (vehicleEngine.equals("Diesel")) {
            Long repairTypeId = repair.getRepairType().getId();
            RepairTypeEntity repairType = repairTypeService.getRepairTypeById(repairTypeId).get();
            baseRepairCost = repairType.getDieselCost();
        } else if (vehicleEngine.equals("Hibrido")) {
            Long repairTypeId = repair.getRepairType().getId();
            RepairTypeEntity repairType = repairTypeService.getRepairTypeById(repairTypeId).get();
            baseRepairCost = repairType.getHybridCost();
        } else if (vehicleEngine.equals("Electrico")) {
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
