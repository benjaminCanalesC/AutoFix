package bcanales.autofix.repositories;

import bcanales.autofix.entities.VehicleEngineEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VehicleEngineRepository extends JpaRepository<VehicleEngineEntity, Long> {
}
