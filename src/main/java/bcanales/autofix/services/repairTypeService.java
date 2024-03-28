package bcanales.autofix.services;

import bcanales.autofix.entities.repairTypeEntity;
import bcanales.autofix.repositories.repairTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class repairTypeService {
    @Autowired
    repairTypeRepository repairTypeRepository;

    public repairTypeEntity saveRepairType(repairTypeEntity repairType) {
        return repairTypeRepository.save(repairType);
    }
}
