package bcanales.autofix.services;

import bcanales.autofix.entities.VehicleEngineEntity;
import bcanales.autofix.entities.VehicleTypeEntity;
import bcanales.autofix.repositories.VehicleTypeRepository;
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
public class VehicleTypeServiceTest {
    @Autowired
    private VehicleTypeService vehicleTypeService;

    @MockBean
    private VehicleTypeRepository vehicleTypeRepository;

    @Test
    public void saveVehicleType_shouldReturnSavedVehicleType() {
        VehicleTypeEntity vehicleType = new VehicleTypeEntity(null, "SUV");

        when(vehicleTypeRepository.save(any(VehicleTypeEntity.class))).thenReturn(vehicleType);

        VehicleTypeEntity savedVehicleType = vehicleTypeService.saveVehicleType(vehicleType);

        assertEquals("SUV", savedVehicleType.getType());
    }

    @Test
    public void getVehicleTypes_ShouldReturnAllVehicleTypes() {
        List<VehicleTypeEntity> mockVehicleTypes = new ArrayList<>();
        mockVehicleTypes.add(new VehicleTypeEntity(1L, "SUV"));
        mockVehicleTypes.add(new VehicleTypeEntity(2L, "Sedan"));
        when(vehicleTypeRepository.findAll()).thenReturn(mockVehicleTypes);

        List<VehicleTypeEntity> vehicleTypes = vehicleTypeService.getVehicleTypes();

        assertNotNull(vehicleTypes);
        assertEquals(2, vehicleTypes.size());
        assertEquals("SUV", vehicleTypes.get(0).getType());
        assertEquals("Sedan", vehicleTypes.get(1).getType());
    }
}
