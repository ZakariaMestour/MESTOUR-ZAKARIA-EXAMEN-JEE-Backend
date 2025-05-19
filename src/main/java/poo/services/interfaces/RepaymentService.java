package poo.services.interfaces;
import poo.dtos.RepaymentDTO;
import poo.enums.RepaymentType;

import java.time.LocalDate;
import java.util.List;

public interface RepaymentService {
    List<RepaymentDTO> getAllRepayments();
    RepaymentDTO getRepaymentById(Long id);
    List<RepaymentDTO> getRepaymentsByCreditId(Long creditId);
    List<RepaymentDTO> getRepaymentsByType(RepaymentType type);
    List<RepaymentDTO> getRepaymentsByDateRange(LocalDate startDate, LocalDate endDate);
    RepaymentDTO createRepayment(RepaymentDTO repaymentDTO);
    RepaymentDTO updateRepayment(Long id, RepaymentDTO repaymentDTO);
    void deleteRepayment(Long id);

    Double calculateTotalRepaidAmount(Long creditId);
    Double calculateRemainingAmount(Long creditId);
}
