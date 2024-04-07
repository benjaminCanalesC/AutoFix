package bcanales.autofix.services;

import bcanales.autofix.entities.BrandDiscountEntity;
import bcanales.autofix.entities.RepairEntity;
import bcanales.autofix.entities.VehicleBrandEntity;
import bcanales.autofix.entities.VehicleEntity;
import bcanales.autofix.repositories.BrandDiscountRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BrandDiscountService {
    @Autowired
    BrandDiscountRepository brandDiscountRepository;
    @Autowired
    VehicleService vehicleService;
    @Autowired
    VehicleBrandService vehicleBrandService;

    public BrandDiscountEntity saveBrandDiscount(BrandDiscountEntity brandDiscount) {
        return brandDiscountRepository.save(brandDiscount);
    }

    public int calculateBrandDiscount(RepairEntity repair) {
        if (repair.isBonusDiscount()) {
            VehicleEntity vehicle = vehicleService.getVehicleById(repair.getVehicle().getId()).get();

            Long brandId = vehicle.getVehicleBrand().getId();

            VehicleBrandEntity vehicleBrand = vehicleBrandService.getVehicleBrandById(brandId)
                    .orElseThrow(() -> new EntityNotFoundException("Vehicle brand with id " + brandId + "does not exist."));

            BrandDiscountEntity brandDiscount = brandDiscountRepository.findBrandDiscountEntityByVehicleBrand(vehicleBrand)
                    .orElseThrow(() -> new EntityNotFoundException("Brand discount for " + vehicleBrand.getBrand() + "does not exist."));

            if (brandDiscount.getQuantity() != 0) {
                brandDiscount.setQuantity(brandDiscount.getQuantity() - 1);
                return brandDiscount.getAmount();
            }
        }
        return 0;
    }
}
