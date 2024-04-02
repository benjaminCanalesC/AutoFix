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
public class VehicleEntity {
    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String plate;
    private String model;
    private int fabricationYear;
    private int mileage;
    private int seats;

    @ManyToOne
    @JoinColumn(name = "vehicleBrand")
    VehicleBrandEntity vehicleBrand;

    @ManyToOne
    @JoinColumn(name = "vehicleEngine")
    VehicleEngineEntity vehicleEngine;

    @ManyToOne
    @JoinColumn(name = "vehicleType")
    VehicleTypeEntity vehicleType;
}
