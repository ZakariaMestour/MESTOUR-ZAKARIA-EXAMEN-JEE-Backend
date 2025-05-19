package poo.dtos;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import poo.enums.PropertyType;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class MortgageCreditDTO extends CreditDTO {
    private PropertyType propertyType;

    public MortgageCreditDTO(CreditDTO creditDTO, PropertyType propertyType) {
        super(creditDTO.getId(), creditDTO.getRequestDate(), creditDTO.getStatus(),
                creditDTO.getAcceptanceDate(), creditDTO.getAmount(), creditDTO.getDurationMonths(),
                creditDTO.getInterestRate(), creditDTO.getClientId(), creditDTO.getRepaymentIds(), "MORTGAGE");
        this.propertyType = propertyType;
    }
}
