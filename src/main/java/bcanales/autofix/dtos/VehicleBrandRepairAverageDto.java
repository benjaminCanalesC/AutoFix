package bcanales.autofix.dtos;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VehicleBrandRepairAverageDto {
    private String brand;
    private Double averageRepairTime;
}
