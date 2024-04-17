package bcanales.autofix.services;

import bcanales.autofix.entities.*;
import bcanales.autofix.repositories.RepairRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;

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

    @Autowired
    private RepairService repairService;

    @Autowired
    private RepairRepository repairRepository;

    @Test
    public void whenGetDiscountForElectricVehicleWithOneRepair_thenCorrect() throws Exception {
        VehicleBrandEntity bmwBrand = new VehicleBrandEntity(null, "BMW");
        entityManager.persist(bmwBrand);

        VehicleTypeEntity suvType = new VehicleTypeEntity(null, "SUV");
        entityManager.persist(suvType);

        VehicleEngineEntity electricEngine = new VehicleEngineEntity(null, "Electrico");
        entityManager.persist(electricEngine);

        VehicleEntity vehicle = new VehicleEntity();
        vehicle.setPlate("EUQO04");
        vehicle.setModel("IX");
        vehicle.setMileage(500);
        vehicle.setSeats(5);
        vehicle.setFabricationYear(2024);
        vehicle.setVehicleBrand(bmwBrand);
        vehicle.setVehicleType(suvType);
        vehicle.setVehicleEngine(electricEngine);

        entityManager.persist(vehicle);

        RepairTypeEntity airRepairType = new RepairTypeEntity(null,
                "Reparación del Sistema de Aire Acondicionado y Calefacción",
                "Incluye la recarga de refrigerante, reparación o reemplazo del compresor, y solución de problemas del sistema de calefacción.",
                150000,
                150000,
                180000,
                180000
        );
        entityManager.persist(airRepairType);

        entityManager.flush();

        RepairEntity repair = new RepairEntity();
        repair.setEntryDateTime(LocalDateTime.parse("2024-03-15T10:20:00"));
        repair.setBonusDiscount(false);
        repair.setRepairType(airRepairType);
        repair.setVehicle(vehicle);
        repairService.saveRepair(repair);

        repair.setExitDateTime(LocalDateTime.parse("2024-03-17T14:00:00"));
        repair.setPickupDateTime(LocalDateTime.parse("2024-03-17T18:00:00"));

        repairService.updateRepair(repair);

        entityManager.flush();

        double discountPercentage = discountService.discountByRepairs(vehicle);

        assertEquals(0.08, discountPercentage);
    }

    @Test
    public void whenGetDiscountForGasolineVehicleWithNoRepairs_thenCorrect() throws Exception {
        VehicleBrandEntity bmwBrand = new VehicleBrandEntity(null, "BMW");
        entityManager.persist(bmwBrand);

        VehicleTypeEntity suvType = new VehicleTypeEntity(null, "SUV");
        entityManager.persist(suvType);

        VehicleEngineEntity gasolineType = new VehicleEngineEntity(null, "Gasolina");
        entityManager.persist(gasolineType);

        VehicleEntity vehicle = new VehicleEntity();
        vehicle.setPlate("CUQO84");
        vehicle.setModel("X5");
        vehicle.setMileage(2000);
        vehicle.setSeats(5);
        vehicle.setFabricationYear(2012);
        vehicle.setVehicleBrand(bmwBrand);
        vehicle.setVehicleType(suvType);
        vehicle.setVehicleEngine(gasolineType);

        entityManager.persist(vehicle);

        entityManager.flush();

        double discountPercentage = discountService.discountByRepairs(vehicle);

        assertEquals(0.0, discountPercentage);
    }

    @Test
    public void testDiscountByRepairsEdgeCases() throws Exception {
        // Setup for Hybrid vehicle
        VehicleEngineEntity hybridEngine = new VehicleEngineEntity(1L, "Hibrido");
        VehicleEntity hybridVehicle = new VehicleEntity();
        hybridVehicle.setId(1L); // Assuming the ID is set when saved
        hybridVehicle.setVehicleEngine(hybridEngine);

        // Test cases for edge conditions
        int[] repairCounts = {2, 5, 9, 10};  // Edge cases for discount changes including one above last edge
        double[] expectedDiscounts = {0.1, 0.15, 0.2, 0.25};  // Expected discounts for hybrid engine

        for (int i = 0; i < repairCounts.length; i++) {
            int count = repairCounts[i];
            double expected = expectedDiscounts[i];

            // Mocking the repository to return specific counts
            given(repairRepository.countByVehicleId(hybridVehicle.getId())).willReturn(count);

            // Call the method to calculate discount
            double discountPercentage = discountService.discountByRepairs(hybridVehicle);

            // Assert that the calculated discount matches the expected discount
            assertEquals(expected, discountPercentage, 0.001,
                    "Failed at repair count: " + count + " with expected discount: " + expected);
        }
    }


    @Test
    public void whenGetDiscountByAttentionDays_thenCorrect() throws Exception {
        VehicleTypeEntity sedanType = new VehicleTypeEntity(null, "Sedan");
        entityManager.persist(sedanType);

        VehicleEngineEntity gasolineEngine = new VehicleEngineEntity(null, "Gasolina");
        entityManager.persist(gasolineEngine);

        VehicleEntity vehicle = new VehicleEntity();
        vehicle.setPlate("COQK35");
        vehicle.setVehicleType(sedanType);
        vehicle.setVehicleEngine(gasolineEngine);

        entityManager.persist(vehicle);

        RepairTypeEntity fuelRepairType = new RepairTypeEntity(null,
                "Reparaciones del Sistema de Combustible",
                "Limpieza o reemplazo de inyectores de combustible, reparación o reemplazo de la bomba de combustible y solución de problemas de suministro de combustible.",
                130000,
                140000,
                220000,
                0
        );
        entityManager.persist(fuelRepairType);

        entityManager.flush();

        RepairEntity repair = new RepairEntity();
        repair.setEntryDateTime(LocalDateTime.parse("2024-03-18T10:20:00"));
        repair.setRepairType(fuelRepairType);
        repair.setVehicle(vehicle);
        repairService.saveRepair(repair);

        entityManager.flush();

        double discountPercentage = discountService.discountByAttentionDays(repair);

        assertEquals(0.1, discountPercentage);
    }
}
