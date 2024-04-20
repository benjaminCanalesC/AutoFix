package bcanales.autofix.services;

import bcanales.autofix.entities.RepairTypeEntity;
import bcanales.autofix.repositories.RepairTypeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class RepairTypeServiceTest {

    @Autowired
    private RepairTypeService repairTypeService;

    @Autowired
    private RepairTypeRepository repairTypeRepository;

    @Test
    public void saveRepairType_shouldReturnSavedRepairType() {
        RepairTypeEntity repairType = new RepairTypeEntity(null, "Reparaciones del Sistema de Frenos",
                "Incluye el reemplazo de pastillas de freno, discos, tambores, líneas de freno y reparación o reemplazo del cilindro maestro de frenos.", 120000, 120000, 180000, 220000);

        RepairTypeEntity savedRepairType = repairTypeService.saveRepairType(repairType);

        assertNotNull(savedRepairType);
        assertNotNull(savedRepairType.getId());
        assertEquals("Reparaciones del Sistema de Frenos", savedRepairType.getRepairType());
        assertEquals(120000, savedRepairType.getGasolineCost());
        assertEquals(220000, savedRepairType.getElectricCost());
    }

    @Test
    public void getRepairTypes_shouldReturnAllRepairTypes() {
        repairTypeRepository.save(new RepairTypeEntity(null, "Reparaciones del Sistema de Frenos",
                "Incluye el reemplazo de pastillas de freno, discos, tambores, líneas de freno y reparación o reemplazo del cilindro maestro de frenos.", 120000, 120000, 180000, 220000));
        repairTypeRepository.save(new RepairTypeEntity(null, "Reparaciones del Motor",
                "Desde reparaciones menores como el reemplazo de bujías y cables, hasta reparaciones mayores como la reconstrucción del motor o la reparación de la junta de la culata.", 350000, 450000, 700000, 800000));

        List<RepairTypeEntity> repairTypes = repairTypeService.getRepairTypes();

        assertNotNull(repairTypes);
        assertFalse(repairTypes.isEmpty());
        assertEquals(2, repairTypes.size());
        assertEquals("Reparaciones del Sistema de Frenos", repairTypes.get(0).getRepairType());
        assertEquals("Reparaciones del Motor", repairTypes.get(1).getRepairType());
    }

    @Test
    public void getRepairTypeById_shouldReturnRepairTypeWhenExists() {
        RepairTypeEntity savedRepairType = repairTypeRepository.save(new RepairTypeEntity(null, "Reparaciones del Sistema de Frenos",
                "Incluye el reemplazo de pastillas de freno, discos, tambores, líneas de freno y reparación o reemplazo del cilindro maestro de frenos.", 120000, 120000, 180000, 220000));
        Long id = savedRepairType.getId();

        Optional<RepairTypeEntity> foundRepairType = repairTypeService.getRepairTypeById(id);

        assertTrue(foundRepairType.isPresent(), "Repair type should be found");
        assertEquals(savedRepairType, foundRepairType.get(), "The retrieved repair type should match the expected one");
    }
}
