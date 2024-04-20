package bcanales.autofix.services;

import bcanales.autofix.entities.*;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class BrandDiscountServiceTest {
    @Autowired
    private EntityManager entityManager;

    @Autowired
    private BrandDiscountService brandDiscountService;

    @Test
    public void whenGetBrandDiscount_thenCorrect() {
        VehicleBrandEntity toyotaBrand = new VehicleBrandEntity(null, "Toyota");
        entityManager.persist(toyotaBrand);

        VehicleEntity vehicle = new VehicleEntity();
        vehicle.setPlate("LCNQ47");
        vehicle.setVehicleBrand(toyotaBrand);

        entityManager.persist(vehicle);

        BrandDiscountEntity brandDiscount = new BrandDiscountEntity(null, 5, 70000, toyotaBrand);

        entityManager.persist(brandDiscount);
        entityManager.flush();

        RepairEntity repair = new RepairEntity();
        repair.setBonusDiscount(true);
        repair.setVehicle(vehicle);

        entityManager.persist(repair);
        entityManager.flush();

        int result = brandDiscountService.calculateBrandDiscount(repair);

        assertEquals(brandDiscount.getAmount(), result);
    }
}
