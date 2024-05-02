package bcanales.autofix.controllers;

import bcanales.autofix.dtos.RepairDetailsDto;
import bcanales.autofix.dtos.VehicleBrandRepairAverageDto;
import bcanales.autofix.dtos.VehicleTypeMotorRepairsDto;
import bcanales.autofix.dtos.VehicleTypeRepairsDto;
import bcanales.autofix.entities.*;
import bcanales.autofix.services.SummaryService;
import bcanales.autofix.services.RepairService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SummaryController.class)
public class SummaryControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SummaryService summaryService;

    @MockBean
    private RepairService repairService;

    @Test
    public void getAverageTimeByBrandReport_shouldReturnAverageRepairTimeByBrand() throws Exception {
        List<VehicleBrandRepairAverageDto> expectedDtos = new ArrayList<>();
        expectedDtos.add(new VehicleBrandRepairAverageDto("Hyundai", 120.50));
        expectedDtos.add(new VehicleBrandRepairAverageDto("Toyota", 90.75));

        given(summaryService.getAverageRepairTimeByBrand()).willReturn(expectedDtos);

        mockMvc.perform(get("/api/summaries/averageTimeByBrandReport"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].brand", is("Hyundai")))
                .andExpect(jsonPath("$[0].averageRepairTime", is(120.50)))
                .andExpect(jsonPath("$[1].brand", is("Toyota")))
                .andExpect(jsonPath("$[1].averageRepairTime", is(90.75)));
    }

    @Test
    public void getTypeMotorRepairsSummary_shouldReturnTypeMotorRepairsSummary() throws Exception {
        RepairEntity repair = new RepairEntity();
        repair.setId(1L);
        repair.setEntryDateTime(LocalDateTime.parse("2023-12-10T12:00:00"));
        repair.setBonusDiscount(false);
        repair.setRepairType(new RepairTypeEntity(1L, "Brake System Repairs", "Includes replacement of brake pads, discs, drums, brake lines and repair or replacement of the master brake cylinder.", 120000, 120000, 180000, 220000));
        repair.setVehicle(new VehicleEntity(1L, "USAD94", "Model", 100000, 5, 2023, new VehicleBrandEntity(1L, "Brand"), new VehicleEngineEntity(1L, "Gasoline"), new VehicleTypeEntity(1L, "Sedan")));

        given(repairService.saveRepair(Mockito.any(RepairEntity.class))).willReturn(repair);

        List<VehicleTypeMotorRepairsDto> expectedDtos = new ArrayList<>();
        expectedDtos.add(new VehicleTypeMotorRepairsDto("Brake System Repairs", 1L, 2L, 1L, 12L, 100000L));
        given(summaryService.getRepairsEngineType()).willReturn(expectedDtos);

        mockMvc.perform(get("/api/summaries/repairsByEngineType"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].repairType", is("Brake System Repairs")))
                .andExpect(jsonPath("$[0].gasolineCount", is(1)))
                .andExpect(jsonPath("$[0].dieselCount", is(2)))
                .andExpect(jsonPath("$[0].totalCost", is(100000)));
    }

    @Test
    public void getRepairDetailsSummary_shouldReturnRepairDetailsSummary() throws Exception {
        RepairEntity repair = new RepairEntity();
        repair.setId(1L);
        repair.setEntryDateTime(LocalDateTime.parse("2023-12-10T12:00:00"));
        repair.setBonusDiscount(false);
        repair.setBaseRepairCost(120000);
        repair.setDiscount(10);
        repair.setSurcharge(5);
        repair.setIva(21);
        repair.setRepairCost(136);
        repair.setVehicle(new VehicleEntity(1L, "BBYS15", "Elantra", 100000, 5, 2023, new VehicleBrandEntity(1L, "Hyundai"), new VehicleEngineEntity(1L, "Gasoline"), new VehicleTypeEntity(1L, "Sedan")));

        given(repairService.saveRepair(Mockito.any(RepairEntity.class))).willReturn(repair);

        List<RepairDetailsDto> expectedDtos = new ArrayList<>();
        expectedDtos.add(new RepairDetailsDto("BBYS15", 120000, 10, 5, 21, 136));
        given(summaryService.getRepairDetails()).willReturn(expectedDtos);

        mockMvc.perform(get("/api/summaries/repairDetails"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].plate", is("BBYS15")))
                .andExpect(jsonPath("$[0].baseRepairCost", is(120000)))
                .andExpect(jsonPath("$[0].discount", is(10)))
                .andExpect(jsonPath("$[0].surcharge", is(5)))
                .andExpect(jsonPath("$[0].iva", is(21)))
                .andExpect(jsonPath("$[0].totalCost", is(136)));
    }

    @Test
    public void getRepairVehicleTypeSummary_shouldReturnRepairVehicleTypeSummary() throws Exception {
        RepairEntity repair = new RepairEntity();
        repair.setId(1L);
        repair.setEntryDateTime(LocalDateTime.parse("2023-12-10T12:00:00"));
        repair.setBonusDiscount(false);
        repair.setRepairType(new RepairTypeEntity(1L, "Brake System Repairs", "Includes replacement of brake pads, discs, drums, brake lines and repair or replacement of the master brake cylinder.", 120000, 120000, 180000, 220000));
        repair.setVehicle(new VehicleEntity(1L, "USAD94", "Model", 100000, 5, 2023, new VehicleBrandEntity(1L, "Brand"), new VehicleEngineEntity(1L, "Gasoline"), new VehicleTypeEntity(1L, "Sedan")));

        given(repairService.saveRepair(Mockito.any(RepairEntity.class))).willReturn(repair);

        List<VehicleTypeRepairsDto> expectedDtos = new ArrayList<>();
        expectedDtos.add(new VehicleTypeRepairsDto("Brake System Repairs", 1L, 2L, 1L, 4L, 2L, 120000L));
        given(summaryService.getRepairsVehicleType()).willReturn(expectedDtos);

        mockMvc.perform(get("/api/summaries/repairsByVehicleType"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].repairType", is("Brake System Repairs")))
                .andExpect(jsonPath("$[0].hatchbackCount", is(1)))
                .andExpect(jsonPath("$[0].vanCount", is(2)))
                .andExpect(jsonPath("$[0].totalCost", is(120000)));
    }
}
