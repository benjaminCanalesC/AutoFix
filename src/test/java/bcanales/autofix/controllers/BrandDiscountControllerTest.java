package bcanales.autofix.controllers;

import bcanales.autofix.entities.*;
import bcanales.autofix.services.BrandDiscountService;
import bcanales.autofix.services.BrandDiscountServiceTest;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BrandDiscountController.class)
public class BrandDiscountControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BrandDiscountService brandDiscountService;

    @Test
    public void saveBrandDiscount_shouldReturnSavedBrandDiscount() throws Exception {
        VehicleBrandEntity toyotaBrand = new VehicleBrandEntity(1L, "Toyota");
        BrandDiscountEntity expectedBrandDiscount = new BrandDiscountEntity(1L, 70000, 5, toyotaBrand);

        given(brandDiscountService.saveBrandDiscount(Mockito.any(BrandDiscountEntity.class))).willReturn(expectedBrandDiscount);

        String brandDiscountJson = """
            {
            "amount": 70000,
            "quantity": 5,
            "vehicleBrand": {
                "id": 1
                }
            }""";

        mockMvc.perform(post("/api/brandDiscounts/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(brandDiscountJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.vehicleBrand.brand", is("Toyota")));
    }

    @Test
    public void getAllBrandDiscounts_shouldReturnAllBrandDiscounts() throws Exception {
        ArrayList<BrandDiscountEntity> brandDiscounts = new ArrayList<>();
        VehicleBrandEntity toyotaBrand = new VehicleBrandEntity(1L, "Toyota");
        VehicleBrandEntity hondaBrand = new VehicleBrandEntity(2L, "Honda");
        brandDiscounts.add(new BrandDiscountEntity(1L, 70000, 5, toyotaBrand));
        brandDiscounts.add(new BrandDiscountEntity(2L, 50000, 3, hondaBrand));

        given(brandDiscountService.getBrandDiscounts()).willReturn(brandDiscounts);

        mockMvc.perform(get("/api/brandDiscounts/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].amount", is(70000)))
                .andExpect(jsonPath("$[0].vehicleBrand.brand", is("Toyota")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].amount", is(50000)))
                .andExpect(jsonPath("$[1].quantity", is(3)))
                .andExpect(jsonPath("$[1].vehicleBrand.brand", is("Honda")));
    }

}
