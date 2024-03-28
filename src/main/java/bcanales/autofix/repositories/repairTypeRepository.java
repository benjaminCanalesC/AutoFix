package bcanales.autofix.repositories;

import bcanales.autofix.entities.repairTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface repairTypeRepository extends JpaRepository<repairTypeEntity, Long> {
}
