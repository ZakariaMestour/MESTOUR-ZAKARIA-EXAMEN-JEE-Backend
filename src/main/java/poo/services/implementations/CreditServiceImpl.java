package poo.services.implementations;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import poo.dtos.CreditDTO;
import poo.dtos.MortgageCreditDTO;
import poo.dtos.PersonalCreditDTO;
import poo.dtos.ProfessionalCreditDTO;
import poo.entities.*;
import poo.enums.CreditStatus;
import poo.exceptions.ResourceNotFoundException;
import poo.repositories.*;
import poo.services.interfaces.CreditService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
@Transactional
public class CreditServiceImpl implements CreditService {
    private final CreditRepository creditRepository;
    private final PersonalCreditRepository personalCreditRepository;
    private final MortgageCreditRepository mortgageCreditRepository;
    private final ProfessionalCreditRepository professionalCreditRepository;
    private final ClientRepository clientRepository;


    public CreditServiceImpl(
            CreditRepository creditRepository,
            PersonalCreditRepository personalCreditRepository,
            MortgageCreditRepository mortgageCreditRepository,
            ProfessionalCreditRepository professionalCreditRepository,
            ClientRepository clientRepository) {
        this.creditRepository = creditRepository;
        this.personalCreditRepository = personalCreditRepository;
        this.mortgageCreditRepository = mortgageCreditRepository;
        this.professionalCreditRepository = professionalCreditRepository;
        this.clientRepository = clientRepository;
    }

    @Override
    public List<CreditDTO> getAllCredits() {
        return creditRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CreditDTO getCreditById(Long id) {
        Credit credit = creditRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Credit not found with ID: " + id));
        return convertToDTO(credit);
    }

    @Override
    public List<CreditDTO> getCreditsByClientId(Long clientId) {
        return creditRepository.findByClientId(clientId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<CreditDTO> getCreditsByStatus(CreditStatus status) {
        return creditRepository.findByStatus(status).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<CreditDTO> getCreditsByDateRange(LocalDate startDate, LocalDate endDate) {
        return creditRepository.findByRequestDateBetween(startDate, endDate).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteCredit(Long id) {
        Credit credit = creditRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Credit not found with ID: " + id));
        creditRepository.delete(credit);
    }

    // Personal Credit operations
    @Override
    public PersonalCreditDTO createPersonalCredit(PersonalCreditDTO personalCreditDTO) {
        PersonalCredit personalCredit = convertToPersonalCreditEntity(personalCreditDTO);

        // Link client
        Client client = clientRepository.findById(personalCreditDTO.getClientId())
                .orElseThrow(() -> new ResourceNotFoundException("Client not found with ID: " + personalCreditDTO.getClientId()));
        personalCredit.setClient(client);

        PersonalCredit savedCredit = personalCreditRepository.save(personalCredit);
        return convertToPersonalCreditDTO(savedCredit);
    }

    @Override
    public PersonalCreditDTO updatePersonalCredit(Long id, PersonalCreditDTO personalCreditDTO) {
        PersonalCredit existingCredit = personalCreditRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Personal Credit not found with ID: " + id));

        // Update fields
        existingCredit.setRequestDate(personalCreditDTO.getRequestDate());
        existingCredit.setStatus(personalCreditDTO.getStatus());
        existingCredit.setAcceptanceDate(personalCreditDTO.getAcceptanceDate());
        existingCredit.setAmount(personalCreditDTO.getAmount());
        existingCredit.setDurationMonths(personalCreditDTO.getDurationMonths());
        existingCredit.setInterestRate(personalCreditDTO.getInterestRate());
        existingCredit.setReason(personalCreditDTO.getReason());

        PersonalCredit updatedCredit = personalCreditRepository.save(existingCredit);
        return convertToPersonalCreditDTO(updatedCredit);
    }

    @Override
    public List<PersonalCreditDTO> getAllPersonalCredits() {
        return personalCreditRepository.findAll().stream()
                .map(this::convertToPersonalCreditDTO)
                .collect(Collectors.toList());
    }

    // Mortgage Credit operations
    @Override
    public MortgageCreditDTO createMortgageCredit(MortgageCreditDTO mortgageCreditDTO) {
        MortgageCredit mortgageCredit = convertToMortgageCreditEntity(mortgageCreditDTO);

        // Link client
        Client client = clientRepository.findById(mortgageCreditDTO.getClientId())
                .orElseThrow(() -> new ResourceNotFoundException("Client not found with ID: " + mortgageCreditDTO.getClientId()));
        mortgageCredit.setClient(client);

        MortgageCredit savedCredit = mortgageCreditRepository.save(mortgageCredit);
        return convertToMortgageCreditDTO(savedCredit);
    }

    @Override
    public MortgageCreditDTO updateMortgageCredit(Long id, MortgageCreditDTO mortgageCreditDTO) {
        MortgageCredit existingCredit = mortgageCreditRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Mortgage Credit not found with ID: " + id));

        // Update fields
        existingCredit.setRequestDate(mortgageCreditDTO.getRequestDate());
        existingCredit.setStatus(mortgageCreditDTO.getStatus());
        existingCredit.setAcceptanceDate(mortgageCreditDTO.getAcceptanceDate());
        existingCredit.setAmount(mortgageCreditDTO.getAmount());
        existingCredit.setDurationMonths(mortgageCreditDTO.getDurationMonths());
        existingCredit.setInterestRate(mortgageCreditDTO.getInterestRate());
        existingCredit.setPropertyType(mortgageCreditDTO.getPropertyType());

        MortgageCredit updatedCredit = mortgageCreditRepository.save(existingCredit);
        return convertToMortgageCreditDTO(updatedCredit);
    }

    @Override
    public List<MortgageCreditDTO> getAllMortgageCredits() {
        return mortgageCreditRepository.findAll().stream()
                .map(this::convertToMortgageCreditDTO)
                .collect(Collectors.toList());
    }

    // Professional Credit operations
    @Override
    public ProfessionalCreditDTO createProfessionalCredit(ProfessionalCreditDTO professionalCreditDTO) {
        ProfessionalCredit professionalCredit = convertToProfessionalCreditEntity(professionalCreditDTO);

        // Link client
        Client client = clientRepository.findById(professionalCreditDTO.getClientId())
                .orElseThrow(() -> new ResourceNotFoundException("Client not found with ID: " + professionalCreditDTO.getClientId()));
        professionalCredit.setClient(client);

        ProfessionalCredit savedCredit = professionalCreditRepository.save(professionalCredit);
        return convertToProfessionalCreditDTO(savedCredit);
    }

    @Override
    public ProfessionalCreditDTO updateProfessionalCredit(Long id, ProfessionalCreditDTO professionalCreditDTO) {
        ProfessionalCredit existingCredit = professionalCreditRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Professional Credit not found with ID: " + id));

        // Update fields
        existingCredit.setRequestDate(professionalCreditDTO.getRequestDate());
        existingCredit.setStatus(professionalCreditDTO.getStatus());
        existingCredit.setAcceptanceDate(professionalCreditDTO.getAcceptanceDate());
        existingCredit.setAmount(professionalCreditDTO.getAmount());
        existingCredit.setDurationMonths(professionalCreditDTO.getDurationMonths());
        existingCredit.setInterestRate(professionalCreditDTO.getInterestRate());
        existingCredit.setReason(professionalCreditDTO.getReason());
        existingCredit.setCompanyName(professionalCreditDTO.getCompanyName());

        ProfessionalCredit updatedCredit = professionalCreditRepository.save(existingCredit);
        return convertToProfessionalCreditDTO(updatedCredit);
    }

    @Override
    public List<ProfessionalCreditDTO> getAllProfessionalCredits() {
        return professionalCreditRepository.findAll().stream()
                .map(this::convertToProfessionalCreditDTO)
                .collect(Collectors.toList());
    }

    // Credit status management
    @Override
    public CreditDTO approveCreditApplication(Long creditId) {
        Credit credit = creditRepository.findById(creditId)
                .orElseThrow(() -> new ResourceNotFoundException("Credit not found with ID: " + creditId));

        credit.setStatus(CreditStatus.ACCEPTED);
        credit.setAcceptanceDate(LocalDate.now());

        Credit updatedCredit = creditRepository.save(credit);
        return convertToDTO(updatedCredit);
    }

    @Override
    public CreditDTO rejectCreditApplication(Long creditId) {
        Credit credit = creditRepository.findById(creditId)
                .orElseThrow(() -> new ResourceNotFoundException("Credit not found with ID: " + creditId));

        credit.setStatus(CreditStatus.REJECTED);

        Credit updatedCredit = creditRepository.save(credit);
        return convertToDTO(updatedCredit);
    }

    // Helper methods for entity-DTO conversion
    private CreditDTO convertToDTO(Credit credit) {
        List<Long> repaymentIds = credit.getRepayments().stream()
                .map(Repayment::getId)
                .collect(Collectors.toList());

        String creditType = "BASE";
        if (credit instanceof PersonalCredit) {
            creditType = "PERSONAL";
        } else if (credit instanceof MortgageCredit) {
            creditType = "MORTGAGE";
        } else if (credit instanceof ProfessionalCredit) {
            creditType = "PROFESSIONAL";
        }

        return new CreditDTO(
                credit.getId(),
                credit.getRequestDate(),
                credit.getStatus(),
                credit.getAcceptanceDate(),
                credit.getAmount(),
                credit.getDurationMonths(),
                credit.getInterestRate(),
                credit.getClient().getId(),
                repaymentIds,
                creditType
        );
    }

    private PersonalCreditDTO convertToPersonalCreditDTO(PersonalCredit personalCredit) {
        CreditDTO baseDTO = convertToDTO(personalCredit);
        return new PersonalCreditDTO(baseDTO, personalCredit.getReason());
    }

    private MortgageCreditDTO convertToMortgageCreditDTO(MortgageCredit mortgageCredit) {
        CreditDTO baseDTO = convertToDTO(mortgageCredit);
        return new MortgageCreditDTO(baseDTO, mortgageCredit.getPropertyType());
    }

    private ProfessionalCreditDTO convertToProfessionalCreditDTO(ProfessionalCredit professionalCredit) {
        CreditDTO baseDTO = convertToDTO(professionalCredit);
        return new ProfessionalCreditDTO(baseDTO, professionalCredit.getReason(), professionalCredit.getCompanyName());
    }

    private PersonalCredit convertToPersonalCreditEntity(PersonalCreditDTO dto) {
        PersonalCredit personalCredit = new PersonalCredit();
        // Set base credit properties
        setBaseCreditProperties(personalCredit, dto);
        // Set specific properties
        personalCredit.setReason(dto.getReason());
        return personalCredit;
    }

    private MortgageCredit convertToMortgageCreditEntity(MortgageCreditDTO dto) {
        MortgageCredit mortgageCredit = new MortgageCredit();
        // Set base credit properties
        setBaseCreditProperties(mortgageCredit, dto);
        // Set specific properties
        mortgageCredit.setPropertyType(dto.getPropertyType());
        return mortgageCredit;
    }

    private ProfessionalCredit convertToProfessionalCreditEntity(ProfessionalCreditDTO dto) {
        ProfessionalCredit professionalCredit = new ProfessionalCredit();
        // Set base credit properties
        setBaseCreditProperties(professionalCredit, dto);
        // Set specific properties
        professionalCredit.setReason(dto.getReason());
        professionalCredit.setCompanyName(dto.getCompanyName());
        return professionalCredit;
    }

    private void setBaseCreditProperties(Credit credit, CreditDTO dto) {
        credit.setId(dto.getId());
        credit.setRequestDate(dto.getRequestDate() != null ? dto.getRequestDate() : LocalDate.now());
        credit.setStatus(dto.getStatus() != null ? dto.getStatus() : CreditStatus.PENDING);
        credit.setAcceptanceDate(dto.getAcceptanceDate());
        credit.setAmount(dto.getAmount());
        credit.setDurationMonths(dto.getDurationMonths());
        credit.setInterestRate(dto.getInterestRate());
    }
}
