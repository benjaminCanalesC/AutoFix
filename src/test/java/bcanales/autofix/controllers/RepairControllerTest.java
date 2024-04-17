package bcanales.autofix.controllers;

import bcanales.autofix.entities.*;
import bcanales.autofix.services.RepairService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RepairController.class)
public class RepairControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RepairService repairService;

    @Test
    public void saveRepair_shouldReturnSavedRepair() throws Exception {
        RepairTypeEntity repairType = new RepairTypeEntity();
        repairType.setId(1L);

        VehicleEntity vehicle = new VehicleEntity();
        vehicle.setPlate("USAD94");

        RepairEntity expectedRepair = new RepairEntity();
        expectedRepair.setVehicle(vehicle);

        given(repairService.saveRepair(Mockito.any(RepairEntity.class))).willReturn(expectedRepair);

        String repairJson = """
            {
                "entryDateTime": "2023-12-10T12:00:00",
                "bonusDiscount": false,
                "repairType": {"id": 1},
                "vehicle": {"plate": "USAD94"}
            }
            """;

        mockMvc.perform(post("/api/repairs/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(repairJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.vehicle.plate", is("USAD94")));
    }

    @Test
    public void updateRepair_shouldReturnUpdatedRepair() throws Exception {
        RepairTypeEntity repairType = new RepairTypeEntity();
        repairType.setId(1L);

        VehicleEntity vehicle = new VehicleEntity();
        vehicle.setPlate("USAD94");

        RepairEntity expectedRepair = new RepairEntity();
        expectedRepair.setVehicle(vehicle);

        given(repairService.updateRepair(Mockito.any(RepairEntity.class))).willReturn(expectedRepair);

        String repairJson = """
            {
                "entryDateTime": "2023-12-10T12:00:00",
                "bonusDiscount": false,
                "repairType": {"id": 1},
                "vehicle": {"plate": "USAD94"}
            }
            """;





        mockMvc.perform(put("/api/repairs/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(repairJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.vehicle.plate", is("USAD94")));
    }

    @Test
    public void getAllRepairs_shouldReturnAllRepairs() throws Exception {
        ArrayList<RepairEntity> repairs = new ArrayList<>();
        repairs.add(new RepairEntity(1L, 0, 0, 0, 0, 0, false, null, null, null, new RepairTypeEntity(), new VehicleEntity()));
        repairs.add(new RepairEntity(2L, 0, 0, 0, 0, 0, false, null, null, null, new RepairTypeEntity(), new VehicleEntity()));

        given(repairService.getRepairs()).willReturn(repairs);

        mockMvc.perform(get("/api/repairs/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[1].id", is(2)));
    }

    @Test
    public void getRepairById_shouldReturnRepairWhenExists() throws Exception {
        RepairEntity repair = new RepairEntity();
        repair.setId(1L);
        repair.setEntryDateTime(LocalDateTime.parse("2023-12-10T12:00:00"));
        repair.setBonusDiscount(false);
        repair.setRepairType(new RepairTypeEntity(1L,
                "Reparaciones del Sistema de Frenos",
                "Incluye el reemplazo de pastillas de freno, discos, tambores, líneas de freno y reparación o reemplazo del cilindro maestro de frenos.",
                120000, 120000, 180000, 220000));
        repair.setVehicle(new VehicleEntity(1L, "USAD94", "Model", 100000, 5, 2023, new VehicleBrandEntity(1L, "Brand"), new VehicleEngineEntity(1L, "Engine"), new VehicleTypeEntity(1L, "Type")));

        given(repairService.getRepairById(1L)).willReturn(Optional.of(repair));

        mockMvc.perform(get("/api/repairs/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.vehicle.plate", is("USAD94")));
    }

    @Test
    public void getRepairById_shouldReturnNotFoundWhenDoesNotExist() throws Exception {
        given(repairService.getRepairById(1L)).willReturn(Optional.empty());

        mockMvc.perform(get("/api/repairs/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }


}
