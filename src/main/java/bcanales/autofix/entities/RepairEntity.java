package bcanales.autofix.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

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

    private int repairCost;
    private int baseRepairCost;
    private int subtotalCost;
    private int discount;
    private int surcharge;
    private int iva;
    private boolean bonusDiscount;

    private LocalDateTime entryDateTime;
    private LocalDateTime exitDateTime;
    private LocalDateTime pickupDateTime;

    @OneToMany(mappedBy = "repair", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<RepairDetailEntity> repairDetails;

    @ManyToOne
    @JoinColumn(name = "vehicle")
    VehicleEntity vehicle;
}
