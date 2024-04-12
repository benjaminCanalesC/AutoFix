package bcanales.autofix.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RepairDetailsDto {
    private String plate;
    private Integer baseRepairCost;
    private Integer discount;
    private Integer surcharge;
    private Integer iva;
    private Integer totalCost;
}
