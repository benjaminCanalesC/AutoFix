package bcanales.autofix.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "vehicle")
public class vehicleEntity {
    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String plate;
    private String model;
    private int fabricationYear;
    private int seats;

    @ManyToOne
    @JoinColumn(name = "vehicleBrand")
    vehicleBrandEntity vehicleBrand;

    @ManyToOne
    @JoinColumn(name = "vehicleEngine")
    vehicleEngineEntity vehicleEngine;

    @ManyToOne
    @JoinColumn(name = "vehicleType")
    vehicleTypeEntity vehicleType;
}
