package bcanales.autofix.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "repairType")
public class repairTypeEntity {
    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String repairType;
    private String repairDescription;
    private int gasolineCost;
    private int dieselCost;
    private int hybridCost;
    private int electricCost;
}
