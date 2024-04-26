package bcanales.autofix.repositories;

import bcanales.autofix.dtos.RepairDetailsDto;
import bcanales.autofix.entities.RepairEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RepairRepository extends JpaRepository<RepairEntity, Long> {
    int countByVehicleId(Long vehicleId);

    List<RepairEntity> getRepairsByVehicleId(Long vehicleId);

    @Query(value = "SELECT rt.repair_type, " +
            "COUNT(DISTINCT CASE WHEN vt.type = 'Hatchback' THEN v.id ELSE NULL END) AS hatchbackCount, " +
            "COUNT(DISTINCT CASE WHEN vt.type = 'SUV' THEN v.id ELSE NULL END) AS suvCount, " +
            "COUNT(DISTINCT CASE WHEN vt.type = 'Sedan' THEN v.id ELSE NULL END) AS sedanCount, " +
            "COUNT(DISTINCT CASE WHEN vt.type = 'Pickup' THEN v.id ELSE NULL END) AS pickupCount, " +
            "COUNT(DISTINCT CASE WHEN vt.type = 'Furgoneta' THEN v.id ELSE NULL END) AS vanCount, " +
            "SUM(r.repair_cost) AS totalCost " +
            "FROM repair r INNER JOIN vehicle v ON r.vehicle = v.id " +
            "INNER JOIN repair_type rt ON r.repair_type = rt.id " +
            "INNER JOIN vehicle_type vt ON v.vehicle_type = vt.id " +
            "GROUP BY rt.repair_type " +
            "ORDER BY totalCost DESC", nativeQuery = true)
    List<Object[]> findRepairsByVehicleTypes();



    @Query(value = "SELECT vb.brand, ROUND(AVG(EXTRACT(EPOCH FROM (r.exit_date_time - r.entry_date_time)) / 3600), 1) " +
            "FROM repair r JOIN vehicle v ON r.vehicle = v.id " +
            "JOIN vehicle_brand vb ON v.vehicle_brand = vb.id " +
            "GROUP BY vb.brand " +
            "ORDER BY AVG(EXTRACT(EPOCH FROM (r.exit_date_time - r.entry_date_time))) ASC", nativeQuery = true)
    List<Object[]> findAverageRepairTimeByBrand();

    @Query(value = "SELECT rt.repair_type, " +
            "COUNT(DISTINCT CASE WHEN ve.engine = 'Gasolina' THEN v.id ELSE NULL END) AS gasolineCount, " +
            "COUNT(DISTINCT CASE WHEN ve.engine = 'Diesel' THEN v.id ELSE NULL END) AS dieselCount, " +
            "COUNT(DISTINCT CASE WHEN ve.engine = 'Hibrido' THEN v.id ELSE NULL END) AS hybridCount, " +
            "COUNT(DISTINCT CASE WHEN ve.engine = 'Electrico' THEN v.id ELSE NULL END) AS electricCount, " +
            "SUM(r.repair_cost) AS totalCost " +
            "FROM repair r INNER JOIN vehicle v ON r.vehicle = v.id " +
            "INNER JOIN vehicle_engine ve ON v.vehicle_engine = ve.id " +
            "INNER JOIN repair_type rt ON r.repair_type = rt.id " +
            "GROUP BY rt.repair_type " +
            "ORDER BY rt.repair_type DESC;", nativeQuery = true)
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
