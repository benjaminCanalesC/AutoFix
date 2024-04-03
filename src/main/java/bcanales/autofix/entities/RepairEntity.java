package bcanales.autofix.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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

    private LocalDateTime entryDateTime;

    private int repairCost;

    private LocalDateTime exitDateTime;

    private LocalDateTime pickupDateTime;

    @ManyToOne
    @JoinColumn(name = "repairType")
    RepairTypeEntity repairType;

    @ManyToOne
    @JoinColumn(name = "vehicle")
    VehicleEntity vehicle;
}
