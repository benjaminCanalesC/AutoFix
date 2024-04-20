package bcanales.autofix.services;

import bcanales.autofix.entities.VehicleBrandEntity;
import bcanales.autofix.repositories.VehicleBrandRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
public class VehicleBrandServiceTest {
    @Autowired
    private VehicleBrandService vehicleBrandService;

    @MockBean
    private VehicleBrandRepository vehicleBrandRepository;

    @Test
    public void saveVehicleBrand_shouldReturnSavedVehicleBrand() {
        VehicleBrandEntity vehicleBrand = new VehicleBrandEntity(null, "Toyota");

        when(vehicleBrandRepository.save(any(VehicleBrandEntity.class))).thenReturn(vehicleBrand);

        VehicleBrandEntity savedVehicleBrand = vehicleBrandService.saveVehicleBrand(vehicleBrand);

        assertEquals("Toyota", savedVehicleBrand.getBrand());
    }

    @Test
    public void getVehicleBrands_ShouldReturnAllVehicleBrands() {
        List<VehicleBrandEntity> mockVehicleBrands = new ArrayList<>();
        mockVehicleBrands.add(new VehicleBrandEntity(1L, "Toyota"));
        mockVehicleBrands.add(new VehicleBrandEntity(2L, "Ford"));
        when(vehicleBrandRepository.findAll()).thenReturn(mockVehicleBrands);

        List<VehicleBrandEntity> vehicleBrands = vehicleBrandService.getVehicleBrands();

        assertNotNull(vehicleBrands);
        assertEquals(2, vehicleBrands.size());
        assertEquals("Toyota", vehicleBrands.get(0).getBrand());
        assertEquals("Ford", vehicleBrands.get(1).getBrand());
    }

    @Test
    public void getVehicleBrandById_ShouldReturnVehicleBrandWhenFound() {
        Long id = 1L;
        Optional<VehicleBrandEntity> mockVehicleBrand = Optional.of(new VehicleBrandEntity(id, "Toyota"));
        when(vehicleBrandRepository.findById(id)).thenReturn(mockVehicleBrand);

        Optional<VehicleBrandEntity> vehicleBrand = vehicleBrandService.getVehicleBrandById(id);

        assertTrue(vehicleBrand.isPresent());
        assertEquals("Toyota", vehicleBrand.get().getBrand());
    }
}
