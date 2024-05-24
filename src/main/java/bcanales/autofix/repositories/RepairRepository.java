package bcanales.autofix.repositories;

import bcanales.autofix.dtos.RepairDetailsDto;
import bcanales.autofix.entities.RepairEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RepairRepository extends JpaRepository<RepairEntity, Long> {
    int countByVehicleId(Long vehicleId);

    List<RepairEntity> getRepairsByVehicleId(Long vehicleId);
}
