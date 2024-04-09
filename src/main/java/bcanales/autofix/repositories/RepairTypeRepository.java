package bcanales.autofix.repositories;

import bcanales.autofix.entities.RepairTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RepairTypeRepository extends JpaRepository<RepairTypeEntity, Long> {
}
