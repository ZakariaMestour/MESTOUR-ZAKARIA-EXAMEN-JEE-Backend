package poo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import poo.entities.PersonalCredit;
import poo.enums.PersonalCreditReason;

import java.util.List;
@Repository
public interface PersonalCreditRepository extends JpaRepository<PersonalCredit, Long> {
    List<PersonalCredit> findByReason(PersonalCreditReason reason);
}
