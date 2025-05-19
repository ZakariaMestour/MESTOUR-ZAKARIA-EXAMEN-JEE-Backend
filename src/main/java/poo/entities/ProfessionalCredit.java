package poo.entities;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "professional_credits")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue("PROFESSIONAL")
public class ProfessionalCredit  extends Credit{

    @Column(name = "reason", nullable = false)
    private String reason;

    @Column(name = "company_name", nullable = false)
    private String companyName;
}
