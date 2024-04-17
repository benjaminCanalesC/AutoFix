package bcanales.autofix.controllers;

import bcanales.autofix.entities.VehicleBrandEntity;
import bcanales.autofix.services.VehicleBrandService;
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

@WebMvcTest(VehicleBrandController.class)
public class VehicleBrandControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VehicleBrandService vehicleBrandService;

    @Test
    public void saveVehicleBrand_shouldReturnSavedVehicleBrand() throws Exception {
        VehicleBrandEntity vehicleBrand = new VehicleBrandEntity(null, "Toyota");

        given(vehicleBrandService.saveVehicleBrand(any(VehicleBrandEntity.class))).willReturn(vehicleBrand);

        String vehicleBrandJson = """
                {
                    "brand": "Toyota"
                }
                """;

        mockMvc.perform(post("/api/vehicleBrands/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(vehicleBrandJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.brand", is("Toyota")));
    }

    @Test
    public void getVehicleBrands_shouldReturnAllVehicleBrands() throws Exception {
        ArrayList<VehicleBrandEntity> vehicleBrands = new ArrayList<>();
        vehicleBrands.add(new VehicleBrandEntity(1L, "Toyota"));
        vehicleBrands.add(new VehicleBrandEntity(2L, "Honda"));

        given(vehicleBrandService.getVehicleBrands()).willReturn(vehicleBrands);

        mockMvc.perform(get("/api/vehicleBrands/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].brand", is("Toyota")))
                .andExpect(jsonPath("$[1].brand", is("Honda")));
    }
}
