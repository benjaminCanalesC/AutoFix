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

    @Autowired
    private RepairService repairService;

    @Test
    public void whenGetBrandDiscount_thenCorrect() throws Exception {
        // Persiste VehicleBrand
        VehicleBrandEntity toyotaBrand = new VehicleBrandEntity(null, "Toyota");
        entityManager.persist(toyotaBrand);

        // Persiste VehicleType
        VehicleTypeEntity sedanType = new VehicleTypeEntity(null, "Sedan");
        entityManager.persist(sedanType);

        // Persiste VehicleEngine
        VehicleEngineEntity gasolineType = new VehicleEngineEntity(null, "Gasolina");
        entityManager.persist(gasolineType);

        VehicleEntity vehicle = new VehicleEntity();
        vehicle.setPlate("LCNQ47");
        vehicle.setModel("Supra");
        vehicle.setMileage(5000);
        vehicle.setSeats(2);
        vehicle.setFabricationYear(2002);
        vehicle.setVehicleBrand(toyotaBrand);
        vehicle.setVehicleType(sedanType);
        vehicle.setVehicleEngine(gasolineType);

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
        repair.setBonusDiscount(true);
        repair.setRepairType(suspensionRepairType);
        repair.setVehicle(vehicle);
        repairService.saveRepair(repair);

        repair.setExitDateTime(LocalDateTime.parse("2024-03-17T14:00:00"));
        repair.setPickupDateTime(LocalDateTime.parse("2024-03-17T18:00:00"));

        repairService.updateRepair(repair);

        entityManager.flush();

        int result = brandDiscountService.calculateBrandDiscount(repair);

        assertEquals(brandDiscount.getAmount(), result);
    }
}
