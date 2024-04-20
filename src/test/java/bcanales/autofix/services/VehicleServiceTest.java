package bcanales.autofix.services;

import bcanales.autofix.entities.VehicleBrandEntity;
import bcanales.autofix.entities.VehicleEngineEntity;
import bcanales.autofix.entities.VehicleEntity;
import bcanales.autofix.entities.VehicleTypeEntity;
import bcanales.autofix.repositories.VehicleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
public class VehicleServiceTest {
    @Autowired
    private VehicleService vehicleService;

    @MockBean
    private VehicleRepository vehicleRepository;

    @Test
    public void saveVehicle_shouldReturnSavedVehicle() throws Exception {
        VehicleEntity vehicle = new VehicleEntity();
        vehicle.setPlate("QOSP38");

        when(vehicleRepository.save(any(VehicleEntity.class))).thenReturn(vehicle);

        VehicleEntity saveVehicle = vehicleService.saveVehicle(vehicle);

        assertEquals(vehicle.getPlate(), saveVehicle.getPlate());
    }

    @Test
    public void saveVehicleWithInvalidPlate_shouldReturnException() {
        VehicleEntity vehicle = new VehicleEntity();
        vehicle.setPlate("ABCDE8");

        try {
            vehicleService.saveVehicle(vehicle);
            fail("Expected an Exception to be thrown");
        } catch (Exception e) {
            assertEquals("Invalid plate format", e.getMessage());
        }
    }

    @Test
    public void getVehicles_shouldReturnAllVehicles() {
        VehicleEntity vehicle1 = new VehicleEntity();
        vehicle1.setPlate("QOSP38");
        vehicle1.setModel("Model S");

        VehicleEntity vehicle2 = new VehicleEntity();
        vehicle2.setPlate("ZXTY44");
        vehicle2.setModel("Model X");

        ArrayList<VehicleEntity> expectedVehicles = new ArrayList<>(Arrays.asList(vehicle1, vehicle2));
        when(vehicleRepository.findAll()).thenReturn(expectedVehicles);

        ArrayList<VehicleEntity> actualVehicles = vehicleService.getVehicles();

        assertNotNull(actualVehicles);
        assertEquals(2, actualVehicles.size());
        assertEquals("QOSP38", actualVehicles.get(0).getPlate());
        assertEquals("Model X", actualVehicles.get(1).getModel());
        assertSame(expectedVehicles, actualVehicles);
    }

    @Test
    public void updateVehicle_shouldUpdateAndReturnVehicle() {
        VehicleEntity existingVehicle = new VehicleEntity();
        existingVehicle.setId(1L);
        existingVehicle.setPlate("QOSP38");
        existingVehicle.setVehicleBrand(new VehicleBrandEntity(null, "Toyota"));
        existingVehicle.setVehicleEngine(new VehicleEngineEntity(null, "Electrico"));
        existingVehicle.setVehicleType(new VehicleTypeEntity(null, "SUV"));
        existingVehicle.setModel("RAV4");
        existingVehicle.setMileage(25000);
        existingVehicle.setSeats(5);
        existingVehicle.setFabricationYear(2019);

        VehicleEntity updatedVehicle = new VehicleEntity();
        updatedVehicle.setId(1L);
        updatedVehicle.setVehicleBrand(new VehicleBrandEntity(null, "Honda"));
        updatedVehicle.setVehicleEngine(new VehicleEngineEntity(null, "Diesel"));
        updatedVehicle.setVehicleType(new VehicleTypeEntity(null, "Sedan"));
        updatedVehicle.setModel("Accord");
        updatedVehicle.setMileage(30000);
        updatedVehicle.setSeats(4);
        updatedVehicle.setFabricationYear(2020);

        when(vehicleRepository.findById(1L)).thenReturn(Optional.of(existingVehicle));
        when(vehicleRepository.save(any(VehicleEntity.class))).thenReturn(updatedVehicle);

        VehicleEntity result = vehicleService.updateVehicle(updatedVehicle);

        assertEquals("Honda", result.getVehicleBrand().getBrand());
        assertEquals("Diesel", result.getVehicleEngine().getEngine());
        assertEquals("Sedan", result.getVehicleType().getType());
        assertEquals("Accord", result.getModel());
        assertEquals(30000, result.getMileage());
        assertEquals(4, result.getSeats());
        assertEquals(2020, result.getFabricationYear());
    }

    @Test
    public void deleteVehicle_shouldReturnTrueWhenSuccessful() {
        Long vehicleId = 1L;

        try {
            when(vehicleRepository.existsById(vehicleId)).thenReturn(true);
            doNothing().when(vehicleRepository).deleteById(vehicleId);
            boolean result = vehicleService.deleteVehicle(vehicleId);
            assertTrue(result);
        } catch (Exception e) {
            fail("Should not have thrown any exception");
        }
    }
}
