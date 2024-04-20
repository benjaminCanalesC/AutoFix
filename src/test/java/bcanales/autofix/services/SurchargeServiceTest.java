package bcanales.autofix.services;

import bcanales.autofix.entities.RepairEntity;
import bcanales.autofix.entities.VehicleEntity;
import bcanales.autofix.entities.VehicleTypeEntity;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.Year;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class SurchargeServiceTest {
    @Autowired
    private EntityManager entityManager;

    @Autowired
    private SurchargeService surchargeService;

    @Test
    public void whenSurchargeForSedanOverTenYears_thenCorrect() throws Exception {
        VehicleTypeEntity sedanType = new VehicleTypeEntity(null, "Sedan");
        entityManager.persist(sedanType);

        VehicleEntity vehicle = new VehicleEntity();
        vehicle.setVehicleType(sedanType);
        vehicle.setPlate("SED123");
        vehicle.setFabricationYear(Year.now().getValue() - 11);
        entityManager.persist(vehicle);
        entityManager.flush();

        double surcharge = surchargeService.surchargeByVehicleYears(vehicle);

        assertEquals(0.09, surcharge);
    }

    @Test
    public void whenSurchargeForSUVJustUnderFiveYears_thenCorrect() throws Exception {
        VehicleTypeEntity suvType = new VehicleTypeEntity(null, "SUV");
        entityManager.persist(suvType);

        VehicleEntity vehicle = new VehicleEntity();
        vehicle.setVehicleType(suvType);
        vehicle.setPlate("SUV123");
        vehicle.setFabricationYear(Year.now().getValue() - 5);
        entityManager.persist(vehicle);
        entityManager.flush();

        double surcharge = surchargeService.surchargeByVehicleYears(vehicle);

        assertEquals(0.0, surcharge);
    }

    @Test
    public void whenSurchargeForSUVJustOverTenYears_thenCorrect() throws Exception {
        VehicleTypeEntity suvType = new VehicleTypeEntity(null, "SUV");
        entityManager.persist(suvType);

        VehicleEntity vehicle = new VehicleEntity();
        vehicle.setVehicleType(suvType);
        vehicle.setPlate("SUV456");
        vehicle.setFabricationYear(Year.now().getValue() - 11);
        entityManager.persist(vehicle);
        entityManager.flush();

        double surcharge = surchargeService.surchargeByVehicleYears(vehicle);

        assertEquals(0.11, surcharge);
    }

    @Test
    public void whenSurchargeForPickupJustUnderFifteenYears_thenCorrect() throws Exception {
        VehicleTypeEntity pickupType = new VehicleTypeEntity(null, "Pickup");
        entityManager.persist(pickupType);

        VehicleEntity vehicle = new VehicleEntity();
        vehicle.setVehicleType(pickupType);
        vehicle.setPlate("PCK789");
        vehicle.setFabricationYear(Year.now().getValue() - 15);
        entityManager.persist(vehicle);
        entityManager.flush();

        double surcharge = surchargeService.surchargeByVehicleYears(vehicle);

        assertEquals(0.11, surcharge);
    }

    @Test
    public void whenSurchargeForPickupJustUnderTwentyYears_thenCorrect() throws Exception {
        VehicleTypeEntity pickupType = new VehicleTypeEntity(null, "Pickup");
        entityManager.persist(pickupType);

        VehicleEntity vehicle = new VehicleEntity();
        vehicle.setVehicleType(pickupType);
        vehicle.setPlate("QOEU12");
        vehicle.setFabricationYear(Year.now().getValue() - 20);
        entityManager.persist(vehicle);
        entityManager.flush();

        double surcharge = surchargeService.surchargeByVehicleYears(vehicle);

        assertEquals(0.2, surcharge);
    }

    @Test
    public void whenSurchargeForSedanWithIntermediateMileage_thenCorrect() throws Exception {
        VehicleTypeEntity sedanType = new VehicleTypeEntity(null, "Sedan");
        entityManager.persist(sedanType);

        VehicleEntity vehicle = new VehicleEntity();
        vehicle.setVehicleType(sedanType);
        vehicle.setPlate("SED123");
        vehicle.setMileage(18000);
        entityManager.persist(vehicle);
        entityManager.flush();

        double surcharge = surchargeService.surchargeByMileage(vehicle);

        assertEquals(0.07, surcharge);
    }

    @Test
    public void whenSurchargeForSedanJustUnderFirstThreshold_thenCorrect() throws Exception {
        VehicleTypeEntity sedanType = new VehicleTypeEntity(null, "Sedan");
        entityManager.persist(sedanType);

        VehicleEntity vehicle = new VehicleEntity();
        vehicle.setVehicleType(sedanType);
        vehicle.setPlate("SED124");
        vehicle.setMileage(45000);
        entityManager.persist(vehicle);
        entityManager.flush();

        double surcharge = surchargeService.surchargeByMileage(vehicle);

        assertEquals(0.2, surcharge);
    }

    @Test
    public void whenSurchargeForSedanJustOverSecondThreshold_thenCorrect() throws Exception {
        VehicleTypeEntity sedanType = new VehicleTypeEntity(null, "Sedan");
        entityManager.persist(sedanType);

        VehicleEntity vehicle = new VehicleEntity();
        vehicle.setVehicleType(sedanType);
        vehicle.setPlate("SED125");
        vehicle.setMileage(8000);
        entityManager.persist(vehicle);
        entityManager.flush();

        double surcharge = surchargeService.surchargeByMileage(vehicle);

        assertEquals(0.03, surcharge);
    }

    @Test
    public void whenSurchargeForSUVHighMileage_thenCorrect() throws Exception {
        VehicleTypeEntity suvType = new VehicleTypeEntity(null, "SUV");
        entityManager.persist(suvType);

        VehicleEntity vehicle = new VehicleEntity();
        vehicle.setVehicleType(suvType);
        vehicle.setPlate("SUV789");
        vehicle.setMileage(45000);
        entityManager.persist(vehicle);
        entityManager.flush();

        double surcharge = surchargeService.surchargeByMileage(vehicle);

        assertEquals(0.2, surcharge);
    }

    @Test
    public void whenSurchargeForNormalPickupDelay_thenCorrect() {
        RepairEntity repair = new RepairEntity();
        repair.setExitDateTime(LocalDateTime.of(2024, Month.MARCH, 10, 12, 0));
        repair.setPickupDateTime(LocalDateTime.of(2024, Month.MARCH, 15, 12, 0));

        double surcharge = surchargeService.surchargeByPickupDelay(repair);

        assertEquals(0.25, surcharge);
    }


}
