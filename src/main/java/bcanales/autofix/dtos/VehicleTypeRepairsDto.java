package bcanales.autofix.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VehicleTypeRepairsDto {
    private String repairType;
    private String vehicleType;
    private Long vehicleCount;
    private Long totalCost;
}
