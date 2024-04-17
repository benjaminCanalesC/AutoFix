package bcanales.autofix.controllers;

import bcanales.autofix.entities.BrandDiscountEntity;
import bcanales.autofix.entities.VehicleBrandEntity;
import bcanales.autofix.services.BrandDiscountService;
import bcanales.autofix.services.BrandDiscountServiceTest;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
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

}
