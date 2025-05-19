package poo.dtos;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import poo.enums.RepaymentType;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RepaymentDTO {
    private Long id;
    private LocalDate date;
    private Double amount;
    private RepaymentType type;
    private Long creditId;
}
