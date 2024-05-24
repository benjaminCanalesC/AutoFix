package bcanales.autofix.services;

import bcanales.autofix.entities.RepairDetailEntity;
import bcanales.autofix.entities.RepairTypeEntity;
import bcanales.autofix.entities.VehicleEntity;
import bcanales.autofix.repositories.RepairDetailRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RepairDetailService {
    @Autowired
    private RepairDetailRepository repairDetailRepository;
    @Autowired
    private VehicleService vehicleService;
    @Autowired
    private RepairTypeService repairTypeService;

    public RepairDetailEntity saveRepairDetail(RepairDetailEntity repairDetail) throws Exception {
        String vehiclePlate = repairDetail.getVehiclePlate();
        VehicleEntity vehicle = vehicleService.getVehicleByPlate(vehiclePlate)
                .orElseThrow(() -> new EntityNotFoundException("Vehicle with plate " + vehiclePlate + "does not exist."));

        String vehicleEngine = vehicle.getVehicleEngine().getEngine();

        int baseRepairCost;

        if (vehicleEngine.equals("Gasolina")) {
            Long repairTypeId = repairDetail.getRepairType().getId();
            RepairTypeEntity repairType = repairTypeService.getRepairTypeById(repairTypeId).get();
            baseRepairCost = repairType.getGasolineCost();
        } else if (vehicleEngine.equals("Diesel")) {
            Long repairTypeId = repairDetail.getRepairType().getId();
            RepairTypeEntity repairType = repairTypeService.getRepairTypeById(repairTypeId).get();
            baseRepairCost = repairType.getDieselCost();
        } else if (vehicleEngine.equals("Hibrido")) {
            Long repairTypeId = repairDetail.getRepairType().getId();
            RepairTypeEntity repairType = repairTypeService.getRepairTypeById(repairTypeId).get();
            baseRepairCost = repairType.getHybridCost();
        } else if (vehicleEngine.equals("Electrico")) {
            Long repairTypeId = repairDetail.getRepairType().getId();
            RepairTypeEntity repairType = repairTypeService.getRepairTypeById(repairTypeId).get();
            baseRepairCost = repairType.getElectricCost();
        } else {
            throw new Exception("Invalid Engine");
        }

        repairDetail.setRepairCost(baseRepairCost);

        return repairDetailRepository.save(repairDetail);
    }
}
