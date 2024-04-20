package bcanales.autofix.services;

import bcanales.autofix.entities.VehicleEngineEntity;
import bcanales.autofix.repositories.VehicleEngineRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
public class VehicleEngineServiceTest {
    @Autowired
    private VehicleEngineService vehicleEngineService;

    @MockBean
    private VehicleEngineRepository vehicleEngineRepository;

    @Test
    public void saveVehicleEngine_shouldReturnSavedVehicleEngine() {
        VehicleEngineEntity vehicleEngine = new VehicleEngineEntity(null, "Diesel");

        when(vehicleEngineRepository.save(any(VehicleEngineEntity.class))).thenReturn(vehicleEngine);

        VehicleEngineEntity savedVehicleEngine = vehicleEngineService.saveVehicleEngine(vehicleEngine);

        assertEquals("Diesel", savedVehicleEngine.getEngine());
    }

    @Test
    public void getVehicleEngines_ShouldReturnAllVehicleEngines() {
        List<VehicleEngineEntity> mockVehicleEngines = new ArrayList<>();
        mockVehicleEngines.add(new VehicleEngineEntity(1L, "Diesel"));
        mockVehicleEngines.add(new VehicleEngineEntity(2L, "Hybrid"));
        when(vehicleEngineRepository.findAll()).thenReturn(mockVehicleEngines);

        List<VehicleEngineEntity> vehicleEngines = vehicleEngineService.getVehicleEngines();

        assertNotNull(vehicleEngines);
        assertEquals(2, vehicleEngines.size());
        assertEquals("Diesel", vehicleEngines.get(0).getEngine());
        assertEquals("Hybrid", vehicleEngines.get(1).getEngine());
    }
}
