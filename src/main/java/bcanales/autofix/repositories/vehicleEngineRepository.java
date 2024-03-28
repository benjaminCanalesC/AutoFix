package bcanales.autofix.repositories;

import bcanales.autofix.entities.vehicleEngineEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface vehicleEngineRepository extends JpaRepository<vehicleEngineEntity, Long> {
}
