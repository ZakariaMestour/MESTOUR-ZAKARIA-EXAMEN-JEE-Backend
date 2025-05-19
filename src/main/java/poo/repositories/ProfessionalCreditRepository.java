package poo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import poo.entities.ProfessionalCredit;

import java.util.List;
@Repository
public interface ProfessionalCreditRepository extends JpaRepository<ProfessionalCredit, Long> {
    List<ProfessionalCredit> findByCompanyName(String companyName);
    List<ProfessionalCredit> findByReason(String reason);

}
