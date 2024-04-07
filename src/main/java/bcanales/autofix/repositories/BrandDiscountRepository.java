package bcanales.autofix.repositories;

import bcanales.autofix.entities.BrandDiscountEntity;
import bcanales.autofix.entities.VehicleBrandEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BrandDiscountRepository extends JpaRepository<BrandDiscountEntity, Long> {
    Optional<BrandDiscountEntity> findBrandDiscountEntityByVehicleBrand(VehicleBrandEntity VehicleBrand);
}
