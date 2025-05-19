package poo.entities;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import poo.enums.PersonalCreditReason;

@Entity
@Table(name = "personal_credits")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue("PERSONAL")
public class PersonalCredit extends Credit {
    @Enumerated(EnumType.STRING)
    @Column(name = "reason", nullable = false)
    private PersonalCreditReason reason;
}
