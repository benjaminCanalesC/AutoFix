package bcanales.autofix.repositories;

import bcanales.autofix.entities.vehicleTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface vehicleTypeRepository extends JpaRepository<vehicleTypeEntity, Long> {
}
