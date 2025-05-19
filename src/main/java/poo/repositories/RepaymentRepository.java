package poo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import poo.entities.Repayment;
import poo.enums.RepaymentType;

import java.time.LocalDate;
import java.util.List;
@Repository
public interface RepaymentRepository extends JpaRepository<Repayment, Long> {
    List<Repayment> findByCreditId(Long creditId);
    List<Repayment> findByType(RepaymentType type);
    List<Repayment> findByDateBetween(LocalDate startDate, LocalDate endDate);
}
