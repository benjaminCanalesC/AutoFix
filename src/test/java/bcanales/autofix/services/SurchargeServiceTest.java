package bcanales.autofix.services;

import bcanales.autofix.entities.VehicleEntity;
import bcanales.autofix.entities.VehicleTypeEntity;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.Year;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class SurchargeServiceTest {
    @Autowired
    private SurchargeService surchargeService;

    private VehicleEntity createVehicle(String type, int mileage, int year) {
        VehicleTypeEntity vehicleType = new VehicleTypeEntity();
        vehicleType.setType(type);

        VehicleEntity vehicle = new VehicleEntity();
        vehicle.setVehicleType(vehicleType);
        vehicle.setMileage(mileage);
        vehicle.setFabricationYear(year);

        return vehicle;
    }

    @Test
    public void testSurchargeByMileage() throws Exception {
        assertAll("Vehicle surcharges by type and mileage",
                () -> assertEquals(0.0, surchargeService.surchargeByMileage(createVehicle("Sedan", 1000, 2024))),
                () -> assertEquals(0.03, surchargeService.surchargeByMileage(createVehicle("Sedan", 6000, 2024))),
                () -> assertEquals(0.07, surchargeService.surchargeByMileage(createVehicle("Sedan", 20000, 2024))),
                () -> assertEquals(0.12, surchargeService.surchargeByMileage(createVehicle("Sedan", 30000, 2024))),
                () -> assertEquals(0.2, surchargeService.surchargeByMileage(createVehicle("Sedan", 50000, 2024))),
                () -> assertEquals(0.0, surchargeService.surchargeByMileage(createVehicle("Hatchback", 4999, 2024))),
                () -> assertEquals(0.05, surchargeService.surchargeByMileage(createVehicle("SUV", 6000, 2024))),
                () -> assertEquals(0.09, surchargeService.surchargeByMileage(createVehicle("SUV", 20000, 2024))),
                () -> assertEquals(0.12, surchargeService.surchargeByMileage(createVehicle("Pickup", 30000, 2024))),
                () -> assertEquals(0.2, surchargeService.surchargeByMileage(createVehicle("Van", 50000, 2024))),
                () -> assertThrows(Exception.class, () -> surchargeService.surchargeByMileage(createVehicle("Motorcycle", 10000, 2024)), "Invalid Vehicle Type")
        );
    }

    @Test
    public void testSurchargeByVehicleYears() {
        int currentYear = Year.now().getValue();
        assertAll("Vehicle surcharges by type and age",
                () -> assertEquals(0.0, surchargeService.surchargeByVehicleYears(createVehicle("Sedan", 1000, currentYear)), "No surcharge for new Sedan"),
                () -> assertEquals(0.05, surchargeService.surchargeByVehicleYears(createVehicle("Sedan", 1000,currentYear - 7)), "5% surcharge for 7-year-old Sedan"),
                () -> assertEquals(0.09, surchargeService.surchargeByVehicleYears(createVehicle("Sedan", 1000,currentYear - 12)), "9% surcharge for 12-year-old Sedan"),
                () -> assertEquals(0.15, surchargeService.surchargeByVehicleYears(createVehicle("Sedan", 1000,currentYear - 20)), "15% surcharge for 20-year-old Sedan"),
                () -> assertEquals(0.0, surchargeService.surchargeByVehicleYears(createVehicle("SUV", 1000,currentYear)), "No surcharge for new SUV"),
                () -> assertEquals(0.07, surchargeService.surchargeByVehicleYears(createVehicle("SUV", 1000,currentYear - 7)), "7% surcharge for 7-year-old SUV"),
                () -> assertEquals(0.11, surchargeService.surchargeByVehicleYears(createVehicle("SUV", 1000,currentYear - 12)), "11% surcharge for 12-year-old SUV"),
                () -> assertEquals(0.2, surchargeService.surchargeByVehicleYears(createVehicle("SUV", 1000,currentYear - 20)), "20% surcharge for 20-year-old SUV"),
                () -> assertThrows(Exception.class, () -> surchargeService.surchargeByVehicleYears(createVehicle("Motorcycle", 1000,currentYear - 10)), "Invalid Vehicle Type")
        );
    }
}
