package bcanales.autofix.services;

import bcanales.autofix.entities.*;
import bcanales.autofix.repositories.RepairRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class RepairServiceTest {
    @Autowired
    private EntityManager entityManager;

    @Autowired
    private RepairService repairService;

    @MockBean
    private RepairRepository repairRepository;

    @MockBean
    private SurchargeService surchargeService;

    @Test
    public void saveRepair_shouldReturnSavedRepair() throws Exception {
        VehicleBrandEntity teslaBrand = new VehicleBrandEntity(null, "Tesla");
        entityManager.persist(teslaBrand);

        VehicleTypeEntity sedanType = new VehicleTypeEntity(null, "Sedan");
        entityManager.persist(sedanType);

        VehicleEngineEntity electricType = new VehicleEngineEntity(null, "Electrico");
        entityManager.persist(electricType);

        VehicleEntity vehicle = new VehicleEntity();
        vehicle.setPlate("DOEI98");
        vehicle.setModel("Model S");
        vehicle.setMileage(15000);
        vehicle.setSeats(5);
        vehicle.setFabricationYear(2012);
        vehicle.setVehicleBrand(teslaBrand);
        vehicle.setVehicleType(sedanType);
        vehicle.setVehicleEngine(electricType);

        entityManager.persist(vehicle);


        RepairTypeEntity suspensionRepairType = new RepairTypeEntity(null,
                "Reparaciones de la Suspensión y la Dirección",
                "Reemplazo de amortiguadores, brazos de control, rótulas y reparación del sistema de dirección asistida.",
                180000,
                180000,
                210000,
                250000
        );
        entityManager.persist(suspensionRepairType);

        BrandDiscountEntity brandDiscount = new BrandDiscountEntity(null, 5, 70000, teslaBrand);

        entityManager.persist(brandDiscount);

        entityManager.flush();

        RepairEntity repair = new RepairEntity();
        repair.setEntryDateTime(LocalDateTime.parse("2024-03-15T10:20:00"));
        repair.setExitDateTime(LocalDateTime.parse("2024-03-17T14:00:00"));
        repair.setPickupDateTime(LocalDateTime.parse("2024-03-17T18:00:00"));
        repair.setBonusDiscount(true);
        repair.setRepairType(suspensionRepairType);
        repair.setVehicle(vehicle);

        entityManager.flush();

        when(repairRepository.save(any(RepairEntity.class))).thenReturn(repair);

        RepairEntity saveRepair = repairService.saveRepair(repair);

        assertEquals(suspensionRepairType.getRepairType(), saveRepair.getRepairType().getRepairType());
    }

    @Test
    public void saveRepair_shouldReturnSavedRepair2() throws Exception {
        VehicleBrandEntity toyotaBrand = new VehicleBrandEntity(null, "Toyota");
        entityManager.persist(toyotaBrand);

        VehicleTypeEntity pickupType = new VehicleTypeEntity(null, "Hatchback");
        entityManager.persist(pickupType);

        VehicleEngineEntity gasolineEngine = new VehicleEngineEntity(null, "Gasolina");
        entityManager.persist(gasolineEngine);

        VehicleEntity vehicle = new VehicleEntity();
        vehicle.setPlate("SIQU36");
        vehicle.setModel("Tercel");
        vehicle.setMileage(15000);
        vehicle.setSeats(5);
        vehicle.setFabricationYear(2012);
        vehicle.setVehicleBrand(toyotaBrand);
        vehicle.setVehicleType(pickupType);
        vehicle.setVehicleEngine(gasolineEngine);

        entityManager.persist(vehicle);


        RepairTypeEntity suspensionRepairType = new RepairTypeEntity(null,
                "Reparaciones de la Suspensión y la Dirección",
                "Reemplazo de amortiguadores, brazos de control, rótulas y reparación del sistema de dirección asistida.",
                180000,
                180000,
                210000,
                250000
        );
        entityManager.persist(suspensionRepairType);

        BrandDiscountEntity brandDiscount = new BrandDiscountEntity(null, 5, 70000, toyotaBrand);

        entityManager.persist(brandDiscount);

        entityManager.flush();

        RepairEntity repair = new RepairEntity();
        repair.setEntryDateTime(LocalDateTime.parse("2024-03-15T10:20:00"));
        repair.setExitDateTime(LocalDateTime.parse("2024-03-17T14:00:00"));
        repair.setPickupDateTime(LocalDateTime.parse("2024-03-17T18:00:00"));
        repair.setBonusDiscount(true);
        repair.setRepairType(suspensionRepairType);
        repair.setVehicle(vehicle);

        entityManager.flush();

        when(repairRepository.save(any(RepairEntity.class))).thenReturn(repair);

        RepairEntity saveRepair = repairService.saveRepair(repair);

        assertEquals(suspensionRepairType.getRepairType(), saveRepair.getRepairType().getRepairType());
    }

    @Test
    public void saveRepair_shouldReturnSavedRepair3() throws Exception {
        VehicleBrandEntity fordBrand = new VehicleBrandEntity(null, "Ford");
        entityManager.persist(fordBrand);

        VehicleTypeEntity pickupType = new VehicleTypeEntity(null, "Pickup");
        entityManager.persist(pickupType);

        VehicleEngineEntity dieselEngine = new VehicleEngineEntity(null, "Diesel");
        entityManager.persist(dieselEngine);

        VehicleEntity vehicle = new VehicleEntity();
        vehicle.setPlate("OSDU41");
        vehicle.setModel("150");
        vehicle.setMileage(15000);
        vehicle.setSeats(5);
        vehicle.setFabricationYear(2012);
        vehicle.setVehicleBrand(fordBrand);
        vehicle.setVehicleType(pickupType);
        vehicle.setVehicleEngine(dieselEngine);

        entityManager.persist(vehicle);


        RepairTypeEntity suspensionRepairType = new RepairTypeEntity(null,
                "Reparaciones de la Suspensión y la Dirección",
                "Reemplazo de amortiguadores, brazos de control, rótulas y reparación del sistema de dirección asistida.",
                180000,
                180000,
                210000,
                250000
        );
        entityManager.persist(suspensionRepairType);

        BrandDiscountEntity brandDiscount = new BrandDiscountEntity(null, 5, 70000, fordBrand);

        entityManager.persist(brandDiscount);

        entityManager.flush();

        RepairEntity repair = new RepairEntity();
        repair.setEntryDateTime(LocalDateTime.parse("2024-03-15T10:20:00"));
        repair.setExitDateTime(LocalDateTime.parse("2024-03-17T14:00:00"));
        repair.setPickupDateTime(LocalDateTime.parse("2024-03-17T18:00:00"));
        repair.setBonusDiscount(true);
        repair.setRepairType(suspensionRepairType);
        repair.setVehicle(vehicle);

        entityManager.flush();

        when(repairRepository.save(any(RepairEntity.class))).thenReturn(repair);

        RepairEntity saveRepair = repairService.saveRepair(repair);

        assertEquals(suspensionRepairType.getRepairType(), saveRepair.getRepairType().getRepairType());
    }

    @Test
    public void saveRepair_shouldReturnSavedRepair4() throws Exception {
        VehicleBrandEntity subaruBrand = new VehicleBrandEntity(null, "Subaru");
        entityManager.persist(subaruBrand);

        VehicleTypeEntity suvType = new VehicleTypeEntity(null, "SUV");
        entityManager.persist(suvType);

        VehicleEngineEntity hybridEngine = new VehicleEngineEntity(null, "Hibrido");
        entityManager.persist(hybridEngine);

        VehicleEntity vehicle = new VehicleEntity();
        vehicle.setPlate("SDIQ90");
        vehicle.setModel("Forester");
        vehicle.setMileage(15000);
        vehicle.setSeats(5);
        vehicle.setFabricationYear(2012);
        vehicle.setVehicleBrand(subaruBrand);
        vehicle.setVehicleType(suvType);
        vehicle.setVehicleEngine(hybridEngine);

        entityManager.persist(vehicle);


        RepairTypeEntity suspensionRepairType = new RepairTypeEntity(null,
                "Reparaciones de la Suspensión y la Dirección",
                "Reemplazo de amortiguadores, brazos de control, rótulas y reparación del sistema de dirección asistida.",
                180000,
                180000,
                210000,
                250000
        );
        entityManager.persist(suspensionRepairType);

        BrandDiscountEntity brandDiscount = new BrandDiscountEntity(null, 5, 70000, subaruBrand);

        entityManager.persist(brandDiscount);

        entityManager.flush();

        RepairEntity repair = new RepairEntity();
        repair.setEntryDateTime(LocalDateTime.parse("2024-03-15T10:20:00"));
        repair.setExitDateTime(LocalDateTime.parse("2024-03-17T14:00:00"));
        repair.setPickupDateTime(LocalDateTime.parse("2024-03-17T18:00:00"));
        repair.setBonusDiscount(true);
        repair.setRepairType(suspensionRepairType);
        repair.setVehicle(vehicle);

        entityManager.flush();

        when(repairRepository.save(any(RepairEntity.class))).thenReturn(repair);

        RepairEntity saveRepair = repairService.saveRepair(repair);

        assertEquals(suspensionRepairType.getRepairType(), saveRepair.getRepairType().getRepairType());
    }

    @Test
    public void deleteRepair_shouldReturnTrueWhenSuccessful() {
        Long repairId = 1L;

        try {
            when(repairRepository.existsById(repairId)).thenReturn(true);
            doNothing().when(repairRepository).deleteById(repairId);
            boolean result = repairService.deleteRepair(repairId);
            assertTrue(result);
        } catch (Exception e) {
            fail("Should not have thrown any exception");
        }
    }

    @Test
    public void updateRepair_shouldUpdateAndReturnRepair() {
        Long repairId = 1L;
        RepairEntity existingRepair = new RepairEntity();
        existingRepair.setId(repairId);
        existingRepair.setBaseRepairCost(100000);
        existingRepair.setSurcharge(20000);
        existingRepair.setDiscount(32000);
        existingRepair.setRepairCost(88000);

        RepairEntity updatedRepair = new RepairEntity();
        updatedRepair.setId(repairId);
        updatedRepair.setPickupDateTime(LocalDateTime.now());

        when(repairRepository.findById(repairId)).thenReturn(Optional.of(existingRepair));
        when(surchargeService.surchargeByPickupDelay(existingRepair)).thenReturn(0.1);
        when(repairRepository.save(any(RepairEntity.class))).thenReturn(existingRepair);

        RepairEntity result = repairService.updateRepair(updatedRepair);

        assertNotNull(result);
        assertEquals(100000, result.getBaseRepairCost());
        assertEquals(32000, result.getDiscount());
        assertEquals(30000, result.getSurcharge()); // Suma el recargo inicial + el 10% por demora
        assertEquals(18620, result.getIva());
        assertEquals(116620, result.getRepairCost());
    }

    @Test
    public void getRepairById_shouldReturnRepairWhenExists() {
        // Arrange
        Long repairId = 1L;
        RepairEntity expectedRepair = new RepairEntity();
        expectedRepair.setId(repairId);
        expectedRepair.setRepairCost(1200);

        when(repairRepository.findById(repairId)).thenReturn(Optional.of(expectedRepair));

        // Act
        Optional<RepairEntity> actualRepair = repairService.getRepairById(repairId);

        // Assert
        assertTrue(actualRepair.isPresent());
        assertEquals(expectedRepair, actualRepair.get());
        assertEquals(repairId, actualRepair.get().getId());
    }

    @Test
    public void getRepairs_shouldReturnAllRepairs() {
        // Arrange
        RepairEntity repair1 = new RepairEntity();
        repair1.setId(1L);
        repair1.setRepairCost(1000);

        RepairEntity repair2 = new RepairEntity();
        repair2.setId(2L);
        repair2.setRepairCost(1500);

        ArrayList<RepairEntity> expectedRepairs = new ArrayList<>(Arrays.asList(repair1, repair2));
        when(repairRepository.findAll()).thenReturn(expectedRepairs);

        // Act
        ArrayList<RepairEntity> actualRepairs = repairService.getRepairs();

        // Assert
        assertNotNull(actualRepairs);
        assertEquals(2, actualRepairs.size());
        assertEquals(Long.valueOf(1L), actualRepairs.get(0).getId());
        assertEquals(1000, actualRepairs.get(0).getRepairCost());
        assertEquals(Long.valueOf(2L), actualRepairs.get(1).getId());
        assertEquals(1500, actualRepairs.get(1).getRepairCost());
    }
}
