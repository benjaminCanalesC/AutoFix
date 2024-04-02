package bcanales.autofix.services;

import bcanales.autofix.entities.RepairTypeEntity;
import bcanales.autofix.repositories.RepairTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RepairTypeService {
    @Autowired
    RepairTypeRepository repairTypeRepository;

    public RepairTypeEntity saveRepairType(RepairTypeEntity repairType) {
        return repairTypeRepository.save(repairType);
    }
}
