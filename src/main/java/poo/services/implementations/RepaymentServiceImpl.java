package poo.services.implementations;

import poo.dtos.RepaymentDTO;
import poo.entities.Credit;
import poo.entities.Repayment;
import poo.enums.RepaymentType;
import poo.exceptions.ResourceNotFoundException;
import poo.repositories.CreditRepository;
import poo.repositories.RepaymentRepository;
import poo.services.interfaces.RepaymentService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class RepaymentServiceImpl implements RepaymentService {

    private final RepaymentRepository repaymentRepository;
    private final CreditRepository creditRepository;


    public RepaymentServiceImpl(RepaymentRepository repaymentRepository, CreditRepository creditRepository) {
        this.repaymentRepository = repaymentRepository;
        this.creditRepository = creditRepository;
    }

    @Override
    public List<RepaymentDTO> getAllRepayments() {
        return repaymentRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public RepaymentDTO getRepaymentById(Long id) {
        Repayment repayment = repaymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Repayment not found with ID: " + id));
        return convertToDTO(repayment);
    }

    @Override
    public List<RepaymentDTO> getRepaymentsByCreditId(Long creditId) {
        return repaymentRepository.findByCreditId(creditId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<RepaymentDTO> getRepaymentsByType(RepaymentType type) {
        return repaymentRepository.findByType(type).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<RepaymentDTO> getRepaymentsByDateRange(LocalDate startDate, LocalDate endDate) {
        return repaymentRepository.findByDateBetween(startDate, endDate).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public RepaymentDTO createRepayment(RepaymentDTO repaymentDTO) {
        Repayment repayment = convertToEntity(repaymentDTO);

        // Link credit
        Credit credit = creditRepository.findById(repaymentDTO.getCreditId())
                .orElseThrow(() -> new ResourceNotFoundException("Credit not found with ID: " + repaymentDTO.getCreditId()));
        repayment.setCredit(credit);

        Repayment savedRepayment = repaymentRepository.save(repayment);
        return convertToDTO(savedRepayment);
    }

    @Override
    public RepaymentDTO updateRepayment(Long id, RepaymentDTO repaymentDTO) {
        Repayment existingRepayment = repaymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Repayment not found with ID: " + id));

        existingRepayment.setDate(repaymentDTO.getDate());
        existingRepayment.setAmount(repaymentDTO.getAmount());
        existingRepayment.setType(repaymentDTO.getType());

        Repayment updatedRepayment = repaymentRepository.save(existingRepayment);
        return convertToDTO(updatedRepayment);
    }

    @Override
    public void deleteRepayment(Long id) {
        Repayment repayment = repaymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Repayment not found with ID: " + id));
        repaymentRepository.delete(repayment);
    }

    @Override
    public Double calculateTotalRepaidAmount(Long creditId) {
        // Verify credit exists
        creditRepository.findById(creditId)
                .orElseThrow(() -> new ResourceNotFoundException("Credit not found with ID: " + creditId));

        List<Repayment> repayments = repaymentRepository.findByCreditId(creditId);
        return repayments.stream()
                .mapToDouble(Repayment::getAmount)
                .sum();
    }

    @Override
    public Double calculateRemainingAmount(Long creditId) {
        Credit credit = creditRepository.findById(creditId)
                .orElseThrow(() -> new ResourceNotFoundException("Credit not found with ID: " + creditId));

        Double totalAmount = credit.getAmount() + (credit.getAmount() * credit.getInterestRate() / 100);
        Double totalRepaid = calculateTotalRepaidAmount(creditId);

        return totalAmount - totalRepaid;
    }

    // Helper methods for entity-DTO conversion
    private RepaymentDTO convertToDTO(Repayment repayment) {
        return new RepaymentDTO(
                repayment.getId(),
                repayment.getDate(),
                repayment.getAmount(),
                repayment.getType(),
                repayment.getCredit().getId()
        );
    }

    private Repayment convertToEntity(RepaymentDTO repaymentDTO) {
        Repayment repayment = new Repayment();
        repayment.setId(repaymentDTO.getId());
        repayment.setDate(repaymentDTO.getDate() != null ? repaymentDTO.getDate() : LocalDate.now());
        repayment.setAmount(repaymentDTO.getAmount());
        repayment.setType(repaymentDTO.getType());
        return repayment;
    }
}
