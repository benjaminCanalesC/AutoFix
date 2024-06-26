package bcanales.autofix.services;

import bcanales.autofix.entities.RepairTypeEntity;
import bcanales.autofix.repositories.RepairTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class RepairTypeService {
    @Autowired
    RepairTypeRepository repairTypeRepository;

    public RepairTypeEntity saveRepairType(RepairTypeEntity repairType) {
        return repairTypeRepository.save(repairType);
    }

    public ArrayList<RepairTypeEntity> getRepairTypes() {
        return (ArrayList<RepairTypeEntity>) repairTypeRepository.findAll();
    }

    public Optional<RepairTypeEntity> getRepairTypeById(Long id) {
        return repairTypeRepository.findById(id);
    }
}
