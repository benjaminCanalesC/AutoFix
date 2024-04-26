package bcanales.autofix.controllers;

import bcanales.autofix.entities.BrandDiscountEntity;
import bcanales.autofix.services.BrandDiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/brandDiscounts")
@CrossOrigin("*")
public class BrandDiscountController {
    @Autowired
    BrandDiscountService brandDiscountService;

    @PostMapping("/")
    public ResponseEntity<BrandDiscountEntity> saveBrandDiscount(@RequestBody BrandDiscountEntity brandDiscount) {
        BrandDiscountEntity newBrandDiscount = brandDiscountService.saveBrandDiscount(brandDiscount);
        return ResponseEntity.ok(newBrandDiscount);
    }

    @GetMapping("/")
    public ResponseEntity<List<BrandDiscountEntity>> getRepairs() {
        List<BrandDiscountEntity> brandDiscounts = brandDiscountService.getBrandDiscounts();
        return ResponseEntity.ok(brandDiscounts);
    }
}
