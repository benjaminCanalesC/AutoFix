package bcanales.autofix.controllers;

import bcanales.autofix.entities.VehicleEngineEntity;
import bcanales.autofix.entities.VehicleEntity;
import bcanales.autofix.services.VehicleEngineService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(VehicleEngineController.class)
public class VehicleEngineControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VehicleEngineService vehicleEngineService;

    @Test
    public void saveVehicleEngine_shouldReturnSavedVehicleEngine() throws Exception {
        VehicleEngineEntity savedVehicleEngine = new VehicleEngineEntity(null, "Gasolina");

        given(vehicleEngineService.saveVehicleEngine(any(VehicleEngineEntity.class))).willReturn(savedVehicleEngine);

        String vehicleEngineJson = """
        {
            "engine": "Gasolina"
        }
        """;

        mockMvc.perform(post("/api/vehicleEngines/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(vehicleEngineJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.engine", is("Gasolina")));
    }

    @Test
    public void getVehicleEngines_shouldReturnListOfVehicleEngines() throws Exception {
        ArrayList<VehicleEngineEntity> vehicleEngines = new ArrayList<>();
        vehicleEngines.add(new VehicleEngineEntity(1L, "Gasolina"));
        vehicleEngines.add(new VehicleEngineEntity(2L, "Diesel"));

        given(vehicleEngineService.getVehicleEngines()).willReturn(vehicleEngines);

        mockMvc.perform(get("/api/vehicleEngines/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].engine", is("Gasolina")))
                .andExpect(jsonPath("$[1].engine", is("Diesel")));
    }
}
