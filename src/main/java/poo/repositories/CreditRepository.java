package poo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import poo.entities.Credit;
import poo.enums.CreditStatus;

import java.time.LocalDate;
import java.util.List;
@Repository
public interface CreditRepository extends JpaRepository<Credit, Long> {
    List<Credit> findByClientId(Long clientId);
    List<Credit> findByStatus(CreditStatus status);
    List<Credit> findByRequestDateBetween(LocalDate startDate, LocalDate endDate);
}
