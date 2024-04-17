package bcanales.autofix.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VehicleTypeMotorRepairsDto {
    private String repairType;
    private Long gasolineCount;
    private Long dieselCount;
    private Long hybridCount;
    private Long electricCount;
    private Long totalCost;
}
