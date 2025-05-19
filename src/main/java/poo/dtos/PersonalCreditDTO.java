package poo.dtos;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import poo.enums.PersonalCreditReason;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class PersonalCreditDTO extends CreditDTO{
    private PersonalCreditReason reason;

    public PersonalCreditDTO(CreditDTO creditDTO, PersonalCreditReason reason) {
        super(creditDTO.getId(), creditDTO.getRequestDate(), creditDTO.getStatus(),
                creditDTO.getAcceptanceDate(), creditDTO.getAmount(), creditDTO.getDurationMonths(),
                creditDTO.getInterestRate(), creditDTO.getClientId(), creditDTO.getRepaymentIds(), "PERSONAL");
        this.reason = reason;
    }
}
