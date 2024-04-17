package bcanales.autofix.repositories;

import bcanales.autofix.entities.BrandDiscountEntity;
import bcanales.autofix.entities.RepairEntity;
import bcanales.autofix.entities.VehicleBrandEntity;
import bcanales.autofix.entities.VehicleEntity;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class BrandDiscountRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private BrandDiscountRepository brandDiscountRepository;

    @Test
    public void whenFindBrandDiscountByVehicleBrand_thenReturnBrandDiscount() {
        VehicleBrandEntity toyotaBrand = new VehicleBrandEntity(null, "Toyota");
        entityManager.persistAndFlush(toyotaBrand);

        BrandDiscountEntity brandDiscount = new BrandDiscountEntity(null, 5, 70000, toyotaBrand);
        entityManager.persistAndFlush(brandDiscount);

        BrandDiscountEntity found = brandDiscountRepository.findBrandDiscountEntityByVehicleBrand(toyotaBrand)
                .orElseThrow(() -> new EntityNotFoundException("Vehicle brand " + toyotaBrand.getBrand() + "does not exist."));

        assertThat(found.getAmount()).isEqualTo(brandDiscount.getAmount());
        assertThat(found.getQuantity()).isEqualTo(brandDiscount.getQuantity());
        assertThat(found.getVehicleBrand()).isEqualTo(toyotaBrand);
    }
}
