package bcanales.autofix.controllers;

import bcanales.autofix.entities.VehicleBrandEntity;
import bcanales.autofix.entities.VehicleEngineEntity;
import bcanales.autofix.entities.VehicleEntity;
import bcanales.autofix.entities.VehicleTypeEntity;
import bcanales.autofix.services.VehicleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(VehicleController.class)
public class VehicleControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VehicleService vehicleService;

    @Test
    public void saveVehicle_shouldReturnSavedVehicle() throws Exception {
        VehicleEntity savedVehicle = new VehicleEntity(null,"BBYS15", "Elantra", 1000, 5, 2024,
                new VehicleBrandEntity(1L, "Hyundai"), new VehicleEngineEntity(1L, "Gasolina"), new VehicleTypeEntity(1L, "Sedan"));

        given(vehicleService.saveVehicle(any(VehicleEntity.class))).willReturn(savedVehicle);

        String vehicleJson = """
                {
                    "plate": "BBYS15",
                    "model": "Elantra",
                    "mileage": 1000,
                    "seats": 5,
                    "fabricationYear": 2024,
                    "vehicleBrand": {
                        "id": 1
                    },
                    "vehicleEngine": {
                        "id": 1
                    },
                    "vehicleType": {
                        "id": 1
                    }
                }
                """;

        mockMvc.perform(post("/api/vehicles/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(vehicleJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.plate", is("BBYS15")));
    }

    @Test
    public void getVehicles_shouldReturnListOfVehicles() throws Exception {
        ArrayList<VehicleEntity> vehicles = new ArrayList<>();
        vehicles.add(new VehicleEntity(1L, "BBYS15", "Elantra", 1000, 5, 2024,
                new VehicleBrandEntity(1L, "Hyundai"), new VehicleEngineEntity(1L, "Gasolina"), new VehicleTypeEntity(1L, "Sedan")));
        vehicles.add(new VehicleEntity(2L, "CDRS20", "Civic", 2000, 5, 2022,
                new VehicleBrandEntity(2L, "Honda"), new VehicleEngineEntity(2L, "Diesel"), new VehicleTypeEntity(2L, "Sedan")));

        given(vehicleService.getVehicles()).willReturn(vehicles);

        mockMvc.perform(get("/api/vehicles/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].plate", is("BBYS15")))
                .andExpect(jsonPath("$[1].plate", is("CDRS20")));
    }

    @Test
    public void getVehicleById_shouldReturnVehicle() throws Exception {
        VehicleEntity vehicle = new VehicleEntity(1L, "BBYS15", "Elantra", 1000, 5, 2024,
                new VehicleBrandEntity(1L, "Hyundai"), new VehicleEngineEntity(1L, "Gasolina"), new VehicleTypeEntity(1L, "Sedan"));

        given(vehicleService.getVehicleById(1L)).willReturn(Optional.of(vehicle));

        mockMvc.perform(get("/api/vehicles/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.plate", is("BBYS15")));
    }

    @Test
    public void getVehicleById_shouldReturn404_whenVehicleNotFound() throws Exception {
        given(vehicleService.getVehicleById(anyLong())).willReturn(Optional.empty());

        mockMvc.perform(get("/api/vehicles/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateVehicle_shouldReturnUpdatedVehicle() throws Exception {
        VehicleEntity updatedVehicle = new VehicleEntity(1L, "BBYS15", "Elantra", 2024, 2000, 5,
                new VehicleBrandEntity(1L, "Hyundai"), new VehicleEngineEntity(1L, "Gasolina"), new VehicleTypeEntity(1L, "Sedan"));

        given(vehicleService.updateVehicle(any(VehicleEntity.class))).willReturn(updatedVehicle);

        String vehicleJson = """
                {
                    "id": 1,
                    "plate": "BBYS15",
                    "model": "Elantra",
                    "mileage": 2000,
                    "seats": 5,
                    "fabricationYear": 2024,
                    "vehicleBrand": {
                        "id": 1
                    },
                    "vehicleEngine": {
                        "id": 1
                    },
                    "vehicleType": {
                        "id": 1
                    }
                }
            """;

        mockMvc.perform(put("/api/vehicles/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(vehicleJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.mileage", is(2000)));
    }

    @Test
    public void deleteVehicleById_shouldReturn204() throws Exception {
        when(vehicleService.deleteVehicle(1L)).thenReturn(true);

        mockMvc.perform(delete("/api/vehicles/1"))
                .andExpect(status().isNoContent());
    }
}
