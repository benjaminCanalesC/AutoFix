package bcanales.autofix.controllers;

import bcanales.autofix.entities.RepairEntity;
import bcanales.autofix.entities.RepairTypeEntity;
import bcanales.autofix.services.RepairTypeService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RepairTypeController.class)
public class RepairTypeControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RepairTypeService repairTypeService;

    @Test
    public void saveRepairType_shouldReturnSavedRepairType() throws Exception {
        RepairTypeEntity brakeSystemRepair = new RepairTypeEntity(1L,
                "Reparaciones del Sistema de Frenos",
                "Incluye el reemplazo de pastillas de freno, discos, tambores, líneas de freno y reparación o reemplazo del cilindro maestro de frenos.",
                120000, 120000, 180000, 220000);

        given(repairTypeService.saveRepairType(Mockito.any(RepairTypeEntity.class))).willReturn(brakeSystemRepair);

        String repairTypeJson = """
            {
                "repairType": "General Maintenance",
                "repairDescription": "Routine maintenance of vehicle systems",
                "gasolineCost": 120000,
                "dieselCost": 120000,
                "hybridCost": 180000,
                "electricCost": 220000
            }
            """;

        mockMvc.perform(post("/api/repairTypes/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(repairTypeJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.repairType", is("Reparaciones del Sistema de Frenos")))
                .andExpect(jsonPath("$.repairDescription", is("Incluye el reemplazo de pastillas de freno, discos, tambores, líneas de freno y reparación o reemplazo del cilindro maestro de frenos.")))
                .andExpect(jsonPath("$.dieselCost", is(120000)));
    }

    @Test
    public void getRepairTypes_shouldReturnAllRepairTypes() throws Exception {
        ArrayList<RepairTypeEntity> repairTypes = new ArrayList<>();
        repairTypes.add(new RepairTypeEntity(1L,
                "Reparaciones del Sistema de Frenos",
                "Incluye el reemplazo de pastillas de freno, discos, tambores, líneas de freno y reparación o reemplazo del cilindro maestro de frenos.",
                120000, 120000, 180000, 220000));
        repairTypes.add(new RepairTypeEntity(2L,
                "Servicio del Sistema de Refrigeración",
                "Reparación o reemplazo de radiadores, bombas de agua, termostatos y mangueras, así como la solución de problemas de sobrecalentamiento.",
                130000, 130000, 190000, 230000));

        given(repairTypeService.getRepairTypes()).willReturn(repairTypes);

        mockMvc.perform(get("/api/repairTypes/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].repairType", is("Reparaciones del Sistema de Frenos")))
                .andExpect(jsonPath("$[1].repairType", is("Servicio del Sistema de Refrigeración")));
    }
}
