package bcanales.autofix.repositories;

import bcanales.autofix.dtos.RepairDetailsDto;
import bcanales.autofix.entities.RepairEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepairRepository extends JpaRepository<RepairEntity, Long> {
    int countByVehicleId(Long vehicleId);

    @Query(value = "SELECT rt.repair_type, vt.type as vehicle_type, COUNT(DISTINCT v.id) AS vehicle_count, SUM(r.repair_cost) AS total_cost " +
            "FROM repair r " +
            "INNER JOIN vehicle v ON r.vehicle = v.id " +
            "INNER JOIN repair_type rt ON r.repair_type = rt.id " +
            "INNER JOIN vehicle_type vt ON v.vehicle_type = vt.id " +
            "GROUP BY rt.repair_type, vt.type " +
            "ORDER BY total_cost DESC ", nativeQuery = true)
    List<Object[]> findRepairsByVehicleTypes();


    @Query(value = "SELECT vb.brand, AVG(EXTRACT(EPOCH FROM (r.exit_date_time - r.entry_date_time)) / 3600) " +
            "FROM repair r JOIN vehicle v ON r.vehicle = v.id " +
            "JOIN vehicle_brand vb ON v.vehicle_brand = vb.id " +
            "GROUP BY vb.brand " +
            "ORDER BY AVG(EXTRACT(EPOCH FROM (r.exit_date_time - r.entry_date_time))) ASC", nativeQuery = true)
    List<Object[]> findAverageRepairTimeByBrand();

    @Query(value = "SELECT rt.repair_type, ve.engine, COUNT(DISTINCT v.id) AS vehicle_count, SUM(r.repair_cost) AS total_cost " +
            "FROM repair r " +
            "INNER JOIN vehicle v ON r.vehicle = v.id " +
            "INNER JOIN vehicle_engine ve ON v.vehicle_engine = ve.id " +
            "INNER JOIN repair_type rt ON r.repair_type = rt.id " +
            "GROUP BY rt.repair_type, ve.engine " +
            "ORDER BY total_cost DESC ", nativeQuery = true)
    List<Object[]> findRepairsByEngineType();

    @Query(value = "SELECT v.plate AS plate, " +
            "r.base_repair_cost AS baseRepairCost, " +
            "r.discount AS discount, " +
            "r.surcharge AS surcharge, " +
            "r.iva AS iva, " +
            "(r.base_repair_cost - r.discount + r.surcharge + r.iva) AS totalCost " +
            "FROM Repair r " +
            "JOIN Vehicle v ON r.vehicle = v.id", nativeQuery = true)
    List<Object[]> findAllRepairDetails();
}
