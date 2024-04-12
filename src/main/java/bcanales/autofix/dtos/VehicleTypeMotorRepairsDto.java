package bcanales.autofix.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VehicleTypeMotorRepairsDto {
    private String repairType;
    private String engineType;
    private Long numberOfVehicles;
    private Long totalAmount;
}
