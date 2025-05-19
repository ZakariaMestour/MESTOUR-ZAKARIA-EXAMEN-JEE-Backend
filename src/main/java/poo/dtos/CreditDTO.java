package poo.dtos;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import poo.enums.CreditStatus;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreditDTO {
    private Long id;
    private LocalDate requestDate;
    private CreditStatus status;
    private LocalDate acceptanceDate;
    private Double amount;
    private Integer durationMonths;
    private Double interestRate;
    private Long clientId;
    private List<Long> repaymentIds;

    // Discriminator field to identify the type of credit
    private String creditType;
}
