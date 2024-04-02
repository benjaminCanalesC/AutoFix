package bcanales.autofix.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "repair")
public class RepairEntity {
    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate entryDate;
    private LocalTime entryTime;

    private int repairCost;

    private LocalDate exitDate;
    private LocalTime exitTime;

    private LocalDate pickupDate;
    private LocalTime pickupTime;

    @ManyToOne
    @JoinColumn(name = "repairType")
    RepairTypeEntity repairType;

    @ManyToOne
    @JoinColumn(name = "vehicle")
    VehicleEntity vehicle;
}
