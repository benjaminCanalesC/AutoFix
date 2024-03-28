package bcanales.autofix.repositories;

import bcanales.autofix.entities.vehicleBrandEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface vehicleBrandRepository extends JpaRepository<vehicleBrandEntity, Long> {
}
