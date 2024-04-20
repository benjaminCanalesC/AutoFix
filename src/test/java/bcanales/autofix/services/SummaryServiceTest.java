package bcanales.autofix.services;

import bcanales.autofix.dtos.RepairDetailsDto;
import bcanales.autofix.dtos.VehicleBrandRepairAverageDto;
import bcanales.autofix.dtos.VehicleTypeMotorRepairsDto;
import bcanales.autofix.dtos.VehicleTypeRepairsDto;
import bcanales.autofix.entities.*;
import bcanales.autofix.repositories.RepairRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
public class SummaryServiceTest {
    @MockBean
    private RepairService repairService;

    @Autowired
    private SummaryService summaryService;

    @Test
    public void getAverageRepairTimeByBrand_thenCorrect() {
        List<Object[]> expectedDtos = new ArrayList<>();
        expectedDtos.add(new Object[]{"Hyundai", BigDecimal.valueOf(120.50)});
        expectedDtos.add(new Object[]{"Toyota", BigDecimal.valueOf(90.75)});

        when(repairService.getAverageRepairTimeByBrand()).thenReturn(expectedDtos);

        List<VehicleBrandRepairAverageDto> result = summaryService.getAverageRepairTimeByBrand();

        for (Object[] obj : expectedDtos) {
            System.out.println(Arrays.toString(obj));
        }
        System.out.println(result);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Hyundai", result.get(0).getBrand());
        assertEquals(120.50, result.get(0).getAverageRepairTime());
        assertEquals("Toyota", result.get(1).getBrand());
        assertEquals(90.75, result.get(1).getAverageRepairTime());
    }

    @Test
    public void getRepairsEngineType_ShouldReturnCorrectData() {
        List<Object[]> mockedResults = new ArrayList<>();
        mockedResults.add(new Object[]{"Reparación de motor", 10L, 5L, 3L, 1L, 20000L});
        mockedResults.add(new Object[]{"Reparación de frenos", 20L, 15L, 10L, 5L, 15000L});

        when(repairService.getRepairsEngineType()).thenReturn(mockedResults);

        List<VehicleTypeMotorRepairsDto> results = summaryService.getRepairsEngineType();

        assertNotNull(results);
        assertEquals(2, results.size());
        assertEquals("Reparación de motor", results.get(0).getRepairType());
        assertEquals(10L, results.get(0).getGasolineCount());
        assertEquals(5L, results.get(0).getDieselCount());
        assertEquals(3L, results.get(0).getHybridCount());
        assertEquals(1L, results.get(0).getElectricCount());
        assertEquals(20000L, results.get(0).getTotalCost());

        assertEquals("Reparación de frenos", results.get(1).getRepairType());
        assertEquals(20L, results.get(1).getGasolineCount());
        assertEquals(15L, results.get(1).getDieselCount());
        assertEquals(10L, results.get(1).getHybridCount());
        assertEquals(5L, results.get(1).getElectricCount());
        assertEquals(15000L, results.get(1).getTotalCost());
    }

    @Test
    public void getRepairDetails_ShouldReturnAllRepairDetails() {
        List<Object[]> mockedResults = new ArrayList<>();
        mockedResults.add(new Object[]{"ABC123", 100000, 30000, 20000, 17100, 107100});
        mockedResults.add(new Object[]{"XYZ789", 150000, 10000, 12000, 28880, 180880});

        when(repairService.getRepairDetails()).thenReturn(mockedResults);

        List<RepairDetailsDto> repairDetails = summaryService.getRepairDetails();

        // Assert
        assertNotNull(repairDetails);
        assertEquals(2, repairDetails.size());
        assertEquals("ABC123", repairDetails.get(0).getPlate());
        assertEquals(100000, repairDetails.get(0).getBaseRepairCost());
        assertEquals(30000, repairDetails.get(0).getDiscount());
        assertEquals(20000, repairDetails.get(0).getSurcharge());
        assertEquals(17100, repairDetails.get(0).getIva());
        assertEquals(107100, repairDetails.get(0).getTotalCost());

        assertEquals("XYZ789", repairDetails.get(1).getPlate());
        assertEquals(150000, repairDetails.get(1).getBaseRepairCost());
        assertEquals(10000, repairDetails.get(1).getDiscount());
        assertEquals(12000, repairDetails.get(1).getSurcharge());
        assertEquals(28880, repairDetails.get(1).getIva());
        assertEquals(180880, repairDetails.get(1).getTotalCost());
    }

    @Test
    public void getRepairsVehicleType_ShouldReturnAllRepairDetails() {
        List<Object[]> mockedResults = new ArrayList<>();
        mockedResults.add(new Object[]{"Reparación de neumaticos", 5L, 10L, 15L, 8L, 2L, 40000L});
        mockedResults.add(new Object[]{"Reparación de suspensión", 3L, 7L, 9L, 4L, 1L, 20000L});

        when(repairService.getRepairsVehicleType()).thenReturn(mockedResults);

        List<VehicleTypeRepairsDto> repairsVehicleType = summaryService.getRepairsVehicleType();

        assertNotNull(repairsVehicleType);
        assertEquals(2, repairsVehicleType.size());

        assertEquals("Reparación de neumaticos", repairsVehicleType.get(0).getRepairType());
        assertEquals(Long.valueOf(5), repairsVehicleType.get(0).getHatchbackCount());
        assertEquals(Long.valueOf(10), repairsVehicleType.get(0).getSuvCount());
        assertEquals(Long.valueOf(2), repairsVehicleType.get(0).getVanCount());
        assertEquals(Long.valueOf(40000), repairsVehicleType.get(0).getTotalCost());

        assertEquals("Reparación de suspensión", repairsVehicleType.get(1).getRepairType());
        assertEquals(Long.valueOf(3), repairsVehicleType.get(1).getHatchbackCount());
        assertEquals(Long.valueOf(4), repairsVehicleType.get(1).getPickupCount());
        assertEquals(Long.valueOf(1), repairsVehicleType.get(1).getVanCount());
        assertEquals(Long.valueOf(20000), repairsVehicleType.get(1).getTotalCost());
    }
}
