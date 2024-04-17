package bcanales.autofix.repositories;

import bcanales.autofix.entities.VehicleBrandEntity;
import bcanales.autofix.entities.VehicleEngineEntity;
import bcanales.autofix.entities.VehicleEntity;
import bcanales.autofix.entities.VehicleTypeEntity;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class VehicleRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Test
    public void whenFindVehicleByPlate_thenReturnVehicle() {
        // Persiste VehicleBrand
        VehicleBrandEntity teslaBrand = new VehicleBrandEntity(null, "Tesla");
        entityManager.persist(teslaBrand);

        // Persiste VehicleType
        VehicleTypeEntity sedanType = new VehicleTypeEntity(null, "Sedan");
        entityManager.persist(sedanType);

        // Persiste VehicleEngine
        VehicleEngineEntity electricType = new VehicleEngineEntity(null, "Electrico");
        entityManager.persist(electricType);

        VehicleEntity vehicle = new VehicleEntity();
        vehicle.setPlate("DOEI98");
        vehicle.setModel("Model S");
        vehicle.setMileage(15000);
        vehicle.setSeats(5);
        vehicle.setFabricationYear(2012);
        vehicle.setVehicleBrand(teslaBrand);
        vehicle.setVehicleType(sedanType);
        vehicle.setVehicleEngine(electricType);

        entityManager.persist(vehicle);

        VehicleEntity found = vehicleRepository.findByPlate("DOEI98")
                .orElseThrow(() -> new EntityNotFoundException("Vehicle with plate DOEI98 does not exist."));

        assertThat(found.getPlate()).isEqualTo("DOEI98");
        assertThat(found.getVehicleBrand()).isEqualTo(teslaBrand);
        assertThat(found.getVehicleType()).isEqualTo(sedanType);
        assertThat(found.getVehicleEngine()).isEqualTo(electricType);
        assertThat(found.getModel()).isEqualTo("Model S");
        assertThat(found.getMileage()).isEqualTo(15000);
        assertThat(found.getSeats()).isEqualTo(5);
        assertThat(found.getFabricationYear()).isEqualTo(2012);
    }
}
