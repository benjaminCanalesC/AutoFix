package bcanales.autofix.services;

import bcanales.autofix.entities.*;
import bcanales.autofix.repositories.BrandDiscountRepository;
import bcanales.autofix.repositories.RepairRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BrandDiscountService {
    @Autowired
    BrandDiscountRepository brandDiscountRepository;
    @Autowired
    VehicleService vehicleService;
    @Autowired
    VehicleBrandService vehicleBrandService;
    @Autowired
    RepairRepository repairRepository;

    public BrandDiscountEntity saveBrandDiscount(BrandDiscountEntity brandDiscount) {
        return brandDiscountRepository.save(brandDiscount);
    }

    public ArrayList<BrandDiscountEntity> getBrandDiscounts() {
        return (ArrayList<BrandDiscountEntity>) brandDiscountRepository.findAll();
    }

    public boolean deleteBrandDiscount(Long id) throws Exception {
        try {
            brandDiscountRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public int calculateBrandDiscount(RepairEntity repair) throws Exception {
        if (repair.isBonusDiscount()) {
            VehicleEntity vehicle = vehicleService.getVehicleById(repair.getVehicle().getId()).get();

            Long brandId = vehicle.getVehicleBrand().getId();

            VehicleBrandEntity vehicleBrand = vehicleBrandService.getVehicleBrandById(brandId)
                    .orElseThrow(() -> new EntityNotFoundException("Vehicle brand with id " + brandId + " does not exist."));

            BrandDiscountEntity brandDiscount = brandDiscountRepository.findBrandDiscountEntityByVehicleBrand(vehicleBrand)
                    .orElseThrow(() -> new EntityNotFoundException("Brand discount for " + vehicleBrand.getBrand() + " does not exist."));

            Long vehicleId = vehicle.getId();

            List<RepairEntity> repairs = repairRepository.getRepairsByVehicleId(vehicleId);

            for (RepairEntity rep : repairs) {
                if (rep.isBonusDiscount()) {
                    throw new Exception("Vehicle with plate " + vehicle.getPlate() + " already used his discount.");
                }
            }

            if (brandDiscount.getQuantity() != 0) {
                brandDiscount.setQuantity(brandDiscount.getQuantity() - 1);

                int brandDiscountAmount = brandDiscount.getAmount();

                if (brandDiscount.getQuantity() == 0) {
                    deleteBrandDiscount(brandDiscount.getId());
                }

                return brandDiscountAmount;
            }
        }
        return 0;
    }
}
