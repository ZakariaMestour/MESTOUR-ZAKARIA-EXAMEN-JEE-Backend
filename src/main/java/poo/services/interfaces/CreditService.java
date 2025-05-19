package poo.services.interfaces;
import poo.dtos.CreditDTO;
import poo.dtos.MortgageCreditDTO;
import poo.dtos.PersonalCreditDTO;
import poo.dtos.ProfessionalCreditDTO;
import poo.enums.CreditStatus;

import java.time.LocalDate;
import java.util.List;
public interface CreditService {

    List<CreditDTO> getAllCredits();
    CreditDTO getCreditById(Long id);
    List<CreditDTO> getCreditsByClientId(Long clientId);
    List<CreditDTO> getCreditsByStatus(CreditStatus status);
    List<CreditDTO> getCreditsByDateRange(LocalDate startDate, LocalDate endDate);
    void deleteCredit(Long id);
    PersonalCreditDTO createPersonalCredit(PersonalCreditDTO personalCreditDTO);
    PersonalCreditDTO updatePersonalCredit(Long id, PersonalCreditDTO personalCreditDTO);
    List<PersonalCreditDTO> getAllPersonalCredits();

    MortgageCreditDTO createMortgageCredit(MortgageCreditDTO mortgageCreditDTO);
    MortgageCreditDTO updateMortgageCredit(Long id, MortgageCreditDTO mortgageCreditDTO);
    List<MortgageCreditDTO> getAllMortgageCredits();

    ProfessionalCreditDTO createProfessionalCredit(ProfessionalCreditDTO professionalCreditDTO);
    ProfessionalCreditDTO updateProfessionalCredit(Long id, ProfessionalCreditDTO professionalCreditDTO);
    List<ProfessionalCreditDTO> getAllProfessionalCredits();

    CreditDTO approveCreditApplication(Long creditId);
    CreditDTO rejectCreditApplication(Long creditId);
}
