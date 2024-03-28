package bcanales.autofix.repositories;

import bcanales.autofix.entities.vehicleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface vehicleRepository extends JpaRepository<vehicleEntity, Long> {
}
