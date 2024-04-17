package bcanales.autofix.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VehicleTypeRepairsDto {
    private String repairType;
    private Long hatchbackCount;
    private Long suvCount;
    private Long sedanCount;
    private Long pickupCount;
    private Long vanCount;
    private Long totalCost;
}
