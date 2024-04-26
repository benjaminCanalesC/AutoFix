package bcanales.autofix.services;

import bcanales.autofix.entities.*;
import bcanales.autofix.repositories.RepairRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class DiscountServiceTest {
    @Autowired
    private EntityManager entityManager;

    @Autowired
    private DiscountService discountService;

    @Test
    public void whenGetDiscountWithOneRepair_thenCorrect() {
        VehicleEngineEntity gasolineType = new VehicleEngineEntity(null, "Gasolina");
        entityManager.persist(gasolineType);

        VehicleEntity vehicle = new VehicleEntity();
        vehicle.setVehicleEngine(gasolineType);
        vehicle.setPlate("EUQO04");

        entityManager.persist(vehicle);
        entityManager.flush();

        RepairEntity repair = new RepairEntity();
        repair.setVehicle(vehicle);

        entityManager.persist(repair);
        entityManager.flush();

        double discountPercentage = discountService.discountByRepairs(vehicle);

        assertEquals(0.05, discountPercentage);
    }

    @Test
    public void whenGetDiscountWithNoRepairs_thenCorrect() {
        VehicleEngineEntity gasolineType = new VehicleEngineEntity(null, "Diesel");
        entityManager.persist(gasolineType);

        VehicleEntity vehicle = new VehicleEntity();
        vehicle.setPlate("CUQO84");
        vehicle.setVehicleEngine(gasolineType);

        entityManager.persist(vehicle);
        entityManager.flush();

        double discountPercentage = discountService.discountByRepairs(vehicle);

        assertEquals(0.0, discountPercentage);
    }

    @Test
    public void whenGetDiscountWithElevenRepairs_thenCorrect() {
        VehicleEngineEntity dieselType = new VehicleEngineEntity(null, "Gasolina");
        entityManager.persist(dieselType);

        VehicleEntity vehicle = new VehicleEntity();
        vehicle.setVehicleEngine(dieselType);
        vehicle.setPlate("OCUQ51");

        entityManager.persist(vehicle);
        entityManager.flush();

        for (int i = 0; i < 11; i++) {
            RepairEntity repair = new RepairEntity();
            repair.setVehicle(vehicle);
            entityManager.persist(repair);
        }
        entityManager.flush();

        double discountPercentage = discountService.discountByRepairs(vehicle);

        assertEquals(0.2, discountPercentage);
    }

    @Test
    public void whenGetDiscountWithThreeRepairs_thenCorrect() {
        VehicleEngineEntity dieselType = new VehicleEngineEntity(null, "Diesel");
        entityManager.persist(dieselType);

        VehicleEntity vehicle = new VehicleEntity();
        vehicle.setVehicleEngine(dieselType);
        vehicle.setPlate("DUQO84");

        entityManager.persist(vehicle);
        entityManager.flush();

        for (int i = 0; i < 3; i++) {
            RepairEntity repair = new RepairEntity();
            repair.setVehicle(vehicle);
            entityManager.persist(repair);
        }
        entityManager.flush();

        double discountPercentage = discountService.discountByRepairs(vehicle);

        assertEquals(0.12, discountPercentage);
    }

    @Test
    public void whenGetDiscountWithTenRepairs_thenCorrect() {
        VehicleEngineEntity hybridType = new VehicleEngineEntity(null, "Hibrido");
        entityManager.persist(hybridType);

        VehicleEntity vehicle = new VehicleEntity();
        vehicle.setVehicleEngine(hybridType);
        vehicle.setPlate("HUQO99");

        entityManager.persist(vehicle);
        entityManager.flush();

        for (int i = 0; i < 10; i++) {
            RepairEntity repair = new RepairEntity();
            repair.setVehicle(vehicle);
            entityManager.persist(repair);
        }
        entityManager.flush();

        double discountPercentage = discountService.discountByRepairs(vehicle);

        assertEquals(0.25, discountPercentage);
    }

    @Test
    public void whenGetDiscountWithSixRepairs_thenCorrect() {
        VehicleEngineEntity unknownType = new VehicleEngineEntity(null, "Electrico");
        entityManager.persist(unknownType);

        VehicleEntity vehicle = new VehicleEntity();
        vehicle.setVehicleEngine(unknownType);
        vehicle.setPlate("EQO104");

        entityManager.persist(vehicle);
        entityManager.flush();

        for (int i = 0; i < 6; i++) {
            RepairEntity repair = new RepairEntity();
            repair.setVehicle(vehicle);
            entityManager.persist(repair);
        }
        entityManager.flush();

        double discountPercentage = discountService.discountByRepairs(vehicle);

        assertEquals(0.18, discountPercentage);
    }

    @Test
    public void whenGetDiscountByAttentionDays_thenCorrect() {
        VehicleEntity vehicle = new VehicleEntity();
        vehicle.setPlate("COQK35");

        entityManager.persist(vehicle);
        entityManager.flush();

        RepairEntity repair = new RepairEntity();
        repair.setEntryDateTime(LocalDateTime.parse("2024-03-18T10:20:00"));
        repair.setVehicle(vehicle);

        entityManager.persist(repair);
        entityManager.flush();

        double discountPercentage = discountService.discountByAttentionDays(repair);

        assertEquals(0.1, discountPercentage);
    }
}
