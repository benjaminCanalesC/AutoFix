package bcanales.autofix.repositories;

import bcanales.autofix.entities.*;
import bcanales.autofix.services.RepairService;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class RepairRepositoryTest {
    @Autowired
    private EntityManager entityManager;

    @Autowired
    private RepairRepository repairRepository;

    @Test
    public void whenCountVehicleRepairs_thenCorrect() {
        VehicleEntity vehicle = new VehicleEntity();
        vehicle.setPlate("BBYS15");
        entityManager.persist(vehicle);

        RepairEntity repair = new RepairEntity(null, 0, 0, 0, 0, 0, false, null, null, null, null, vehicle);
        entityManager.persist(repair);

        entityManager.flush();

        int results = repairRepository.countByVehicleId(vehicle.getId());
        assertEquals(1, results);
    }

    @Test
    public void whenFindRepairsByVehicleType_thenReturnRepairs() {
        VehicleTypeEntity vehicleType = new VehicleTypeEntity(null, "Sedan");
        entityManager.persist(vehicleType);

        VehicleEntity vehicle = new VehicleEntity();
        vehicle.setPlate("BBYS15");
        vehicle.setVehicleType(vehicleType);

        entityManager.persist(vehicle);

        entityManager.flush();

        RepairTypeEntity repairType = new RepairTypeEntity(null, "Reparación", null, 0, 0, 0, 0);
        entityManager.persist(repairType);

        RepairEntity repair = new RepairEntity(null, 100000, 0, 0, 0, 0, false, null, null, null, repairType, vehicle);
        entityManager.persist(repair);

        entityManager.flush();

        List<Object[]> results = repairRepository.findRepairsByVehicleTypes();

        assertEquals(1, results.size());
        Object[] result = results.get(0);
        assertEquals(repairType.getRepairType(), result[0]);
        assertEquals(0L, result[1]);
        assertEquals(1L, result[3]);
        assertTrue(((Number) result[6]).longValue() > 0);
    }

    @Test
    public void whenFindAverageRepairTimeByBrand_thenReturnAverageTime() {
        VehicleBrandEntity mitsubishiBrand = new VehicleBrandEntity(null, "Mitsubishi");
        entityManager.persist(mitsubishiBrand);

        VehicleEntity vehicle = new VehicleEntity();
        vehicle.setPlate("USAD94");
        vehicle.setVehicleBrand(mitsubishiBrand);

        entityManager.persist(vehicle);

        entityManager.flush();

        RepairEntity repair = new RepairEntity();
        repair.setEntryDateTime(LocalDateTime.parse("2023-12-10T12:00:00"));
        repair.setExitDateTime(LocalDateTime.parse("2023-12-12T12:00:00"));
        repair.setPickupDateTime(LocalDateTime.parse("2023-12-13T11:00:00"));
        repair.setVehicle(vehicle);
        entityManager.persist(repair);

        entityManager.flush();

        List<Object[]> results = repairRepository.findAverageRepairTimeByBrand();

        assertEquals(1, results.size());
        Object[] result = results.get(0);
        assertEquals(mitsubishiBrand.getBrand(), result[0]);
        BigDecimal averageRepairTimeBD = (BigDecimal) result[1];
        double averageRepairTime = averageRepairTimeBD.doubleValue();
        assertEquals(48.0, averageRepairTime);
    }

    @Test
    public void whenFindRepairsByEngineType_thenReturnRepairs() {
        VehicleEngineEntity hybridEngine = new VehicleEngineEntity(null, "Hibrido");
        entityManager.persist(hybridEngine);

        VehicleEntity vehicle = new VehicleEntity();
        vehicle.setPlate("DUWO41");
        vehicle.setVehicleEngine(hybridEngine);

        entityManager.persist(vehicle);

        RepairTypeEntity repairType = new RepairTypeEntity(null, "Reparación", null, 0, 0, 0, 0);
        entityManager.persist(repairType);

        RepairEntity repair = new RepairEntity();
        repair.setRepairType(repairType);
        repair.setVehicle(vehicle);
        repair.setRepairCost(100000);

        entityManager.persist(repair);

        entityManager.flush();

        List<Object[]> results = repairRepository.findRepairsByEngineType();

        assertEquals(1, results.size());
        Object[] result = results.get(0);
        assertEquals("Reparación", result[0]);
        assertEquals(1L, result[3]);
        assertTrue(((Number) result[3]).longValue() > 0);
    }

    @Test
    public void whenFindRepairDetailsByVehicle_thenReturnRepairsDetails() {
        VehicleEntity vehicle = new VehicleEntity();
        vehicle.setPlate("OWDS72");

        entityManager.persist(vehicle);

        entityManager.flush();

        RepairTypeEntity repairType = new RepairTypeEntity(null, "Reparación", null, 0, 0, 0, 0);

        entityManager.persist(repairType);

        RepairEntity repair = new RepairEntity();
        repair.setRepairType(repairType);
        repair.setBaseRepairCost(100000);
        repair.setDiscount(50000);
        repair.setSurcharge(10000);
        repair.setIva(11400);
        repair.setRepairCost(71400);
        repair.setBonusDiscount(false);
        repair.setVehicle(vehicle);

        entityManager.persist(repair);

        entityManager.flush();

        List<Object[]> results = repairRepository.findAllRepairDetails();

        assertEquals(1, results.size());
        Object[] result = results.get(0);
        assertEquals(vehicle.getPlate(), result[0]);
        assertEquals(100000, result[1]);
        assertEquals(50000, result[2]);
        assertEquals(10000, result[3]);
        assertEquals(11400, result[4]);
        assertEquals(71400, result[5]);
    }
}
