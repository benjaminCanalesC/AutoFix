package bcanales.autofix.services;

import bcanales.autofix.entities.*;
import bcanales.autofix.repositories.BrandDiscountRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class BrandDiscountServiceTest {
    @Autowired
    private EntityManager entityManager;

    @Autowired
    private BrandDiscountService brandDiscountService;

    @Test
    public void whenGetBrandDiscount_thenCorrect() throws Exception {
        VehicleBrandEntity toyotaBrand = new VehicleBrandEntity(null, "Toyota");
        entityManager.persist(toyotaBrand);

        VehicleEntity vehicle = new VehicleEntity();
        vehicle.setPlate("SKQI34");
        vehicle.setVehicleBrand(toyotaBrand);

        entityManager.persist(vehicle);

        BrandDiscountEntity brandDiscount = new BrandDiscountEntity(null, 5, 70000, toyotaBrand);

        entityManager.persist(brandDiscount);
        entityManager.flush();

        RepairEntity repair = new RepairEntity();
        repair.setBonusDiscount(true);
        repair.setVehicle(vehicle);

        int result = brandDiscountService.calculateBrandDiscount(repair);

        assertEquals(brandDiscount.getAmount(), result);
    }

    @Test
    public void whenGetBrandDiscountUsed_thenCorrect() {
        VehicleBrandEntity toyotaBrand = new VehicleBrandEntity(null, "Toyota");
        entityManager.persist(toyotaBrand);

        VehicleEntity vehicle = new VehicleEntity();
        vehicle.setPlate("SKQI34");
        vehicle.setVehicleBrand(toyotaBrand);

        entityManager.persist(vehicle);

        BrandDiscountEntity brandDiscount = new BrandDiscountEntity(null, 5, 70000, toyotaBrand);

        entityManager.persist(brandDiscount);
        entityManager.flush();

        RepairEntity repair = new RepairEntity();
        repair.setBonusDiscount(true);
        repair.setVehicle(vehicle);

        entityManager.persist(repair);
        try {
            brandDiscountService.calculateBrandDiscount(repair);
            fail("Expected an Exception to be thrown");

        } catch (Exception e) {
            assertEquals("Vehicle with plate " + vehicle.getPlate() + " already used his discount.", e.getMessage());
        }
    }

    @Test
    public void deleteBrandDiscount_shouldReturnTrueWhenSuccessful() {
        Long brandDiscountId = 1L;

        BrandDiscountRepository brandDiscountRepository = mock(BrandDiscountRepository.class);

        when(brandDiscountRepository.existsById(brandDiscountId)).thenReturn(true);
        doNothing().when(brandDiscountRepository).deleteById(brandDiscountId);

        try {
            boolean result = brandDiscountService.deleteBrandDiscount(brandDiscountId);
            assertTrue(result);
        } catch (Exception e) {
            fail("Should not have thrown any exception");
        }
    }
}
