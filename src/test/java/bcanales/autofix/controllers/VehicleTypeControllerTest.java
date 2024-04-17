package bcanales.autofix.controllers;

import bcanales.autofix.entities.VehicleEngineEntity;
import bcanales.autofix.entities.VehicleTypeEntity;
import bcanales.autofix.services.VehicleTypeService;
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

@WebMvcTest(VehicleTypeController.class)
public class VehicleTypeControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VehicleTypeService vehicleTypeService;

    @Test
    public void saveVehicleType_shouldReturnSavedVehicleType() throws Exception {
        VehicleTypeEntity savedVehicleType = new VehicleTypeEntity(null, "Sedan");

        given(vehicleTypeService.saveVehicleType(any(VehicleTypeEntity.class))).willReturn(savedVehicleType);

        String vehicleTypeJson = """
        {
            "typeName": "Sedan"
        }
        """;

        mockMvc.perform(post("/api/vehicleTypes/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(vehicleTypeJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.type", is("Sedan")));
    }

    @Test
    public void getVehicleTypes_shouldReturnListOfVehicleTypes() throws Exception {
        ArrayList<VehicleTypeEntity> vehicleTypes = new ArrayList<>();
        vehicleTypes.add(new VehicleTypeEntity(1L, "Sedan"));
        vehicleTypes.add(new VehicleTypeEntity(2L, "Hatchback"));

        given(vehicleTypeService.getVehicleTypes()).willReturn(vehicleTypes);

        mockMvc.perform(get("/api/vehicleTypes/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].type", is("Sedan")))
                .andExpect(jsonPath("$[1].type", is("Hatchback")));
    }
}
