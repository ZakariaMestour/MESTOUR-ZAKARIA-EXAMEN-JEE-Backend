package poo.dtos;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class ProfessionalCreditDTO extends CreditDTO{
    private String reason;
    private String companyName;

    public ProfessionalCreditDTO(CreditDTO creditDTO, String reason, String companyName) {
        super(creditDTO.getId(), creditDTO.getRequestDate(), creditDTO.getStatus(),
                creditDTO.getAcceptanceDate(), creditDTO.getAmount(), creditDTO.getDurationMonths(),
                creditDTO.getInterestRate(), creditDTO.getClientId(), creditDTO.getRepaymentIds(), "PROFESSIONAL");
        this.reason = reason;
        this.companyName = companyName;
    }
}
