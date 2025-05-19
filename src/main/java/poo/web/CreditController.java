package poo.web;

import org.springframework.format.annotation.DateTimeFormat;
import poo.dtos.CreditDTO;
import poo.dtos.MortgageCreditDTO;
import poo.dtos.PersonalCreditDTO;
import poo.dtos.ProfessionalCreditDTO;
import poo.enums.CreditStatus;
import poo.services.implementations.CreditServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import poo.services.interfaces.CreditService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/credits")
@CrossOrigin("*")
public class CreditController {
    private final CreditService creditService;

    @Autowired
    public CreditController(CreditService creditService) {
        this.creditService = creditService;
    }

    // General credit endpoints
    @GetMapping
    public ResponseEntity<List<CreditDTO>> getAllCredits() {
        List<CreditDTO> credits = creditService.getAllCredits();
        return new ResponseEntity<>(credits, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CreditDTO> getCreditById(@PathVariable Long id) {
        CreditDTO credit = creditService.getCreditById(id);
        return new ResponseEntity<>(credit, HttpStatus.OK);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<CreditDTO>> getCreditsByStatus(@PathVariable CreditStatus status) {
        List<CreditDTO> credits = creditService.getCreditsByStatus(status);
        return new ResponseEntity<>(credits, HttpStatus.OK);
    }

    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<CreditDTO>> getCreditsByClientId(@PathVariable Long clientId) {
        List<CreditDTO> credits = creditService.getCreditsByClientId(clientId);
        return new ResponseEntity<>(credits, HttpStatus.OK);
    }

    @GetMapping("/date-range")
    public ResponseEntity<List<CreditDTO>> getCreditsByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<CreditDTO> credits = creditService.getCreditsByDateRange(startDate, endDate);
        return new ResponseEntity<>(credits, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCredit(@PathVariable Long id) {
        creditService.deleteCredit(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Personal Credit endpoints
    @GetMapping("/personal")
    public ResponseEntity<List<PersonalCreditDTO>> getAllPersonalCredits() {
        List<PersonalCreditDTO> credits = creditService.getAllPersonalCredits();
        return new ResponseEntity<>(credits, HttpStatus.OK);
    }

    @PostMapping("/personal")
    public ResponseEntity<PersonalCreditDTO> createPersonalCredit(@RequestBody PersonalCreditDTO creditDTO) {
        PersonalCreditDTO createdCredit = creditService.createPersonalCredit(creditDTO);
        return new ResponseEntity<>(createdCredit, HttpStatus.CREATED);
    }

    @PutMapping("/personal/{id}")
    public ResponseEntity<PersonalCreditDTO> updatePersonalCredit(@PathVariable Long id, @RequestBody PersonalCreditDTO creditDTO) {
        PersonalCreditDTO updatedCredit = creditService.updatePersonalCredit(id, creditDTO);
        return new ResponseEntity<>(updatedCredit, HttpStatus.OK);
    }

    // Mortgage Credit endpoints
    @GetMapping("/mortgage")
    public ResponseEntity<List<MortgageCreditDTO>> getAllMortgageCredits() {
        List<MortgageCreditDTO> credits = creditService.getAllMortgageCredits();
        return new ResponseEntity<>(credits, HttpStatus.OK);
    }

    @PostMapping("/mortgage")
    public ResponseEntity<MortgageCreditDTO> createMortgageCredit(@RequestBody MortgageCreditDTO creditDTO) {
        MortgageCreditDTO createdCredit = creditService.createMortgageCredit(creditDTO);
        return new ResponseEntity<>(createdCredit, HttpStatus.CREATED);
    }

    @PutMapping("/mortgage/{id}")
    public ResponseEntity<MortgageCreditDTO> updateMortgageCredit(@PathVariable Long id, @RequestBody MortgageCreditDTO creditDTO) {
        MortgageCreditDTO updatedCredit = creditService.updateMortgageCredit(id, creditDTO);
        return new ResponseEntity<>(updatedCredit, HttpStatus.OK);
    }

    // Professional Credit endpoints
    @GetMapping("/professional")
    public ResponseEntity<List<ProfessionalCreditDTO>> getAllProfessionalCredits() {
        List<ProfessionalCreditDTO> credits = creditService.getAllProfessionalCredits();
        return new ResponseEntity<>(credits, HttpStatus.OK);
    }

    @PostMapping("/professional")
    public ResponseEntity<ProfessionalCreditDTO> createProfessionalCredit(@RequestBody ProfessionalCreditDTO creditDTO) {
        ProfessionalCreditDTO createdCredit = creditService.createProfessionalCredit(creditDTO);
        return new ResponseEntity<>(createdCredit, HttpStatus.CREATED);
    }

    @PutMapping("/professional/{id}")
    public ResponseEntity<ProfessionalCreditDTO> updateProfessionalCredit(@PathVariable Long id, @RequestBody ProfessionalCreditDTO creditDTO) {
        ProfessionalCreditDTO updatedCredit = creditService.updateProfessionalCredit(id, creditDTO);
        return new ResponseEntity<>(updatedCredit, HttpStatus.OK);
    }

    // Credit status management endpoints
    @PatchMapping("/{id}/approve")
    public ResponseEntity<CreditDTO> approveCreditApplication(@PathVariable Long id) {
        CreditDTO updatedCredit = creditService.approveCreditApplication(id);
        return new ResponseEntity<>(updatedCredit, HttpStatus.OK);
    }

    @PatchMapping("/{id}/reject")
    public ResponseEntity<CreditDTO> rejectCreditApplication(@PathVariable Long id) {
        CreditDTO updatedCredit = creditService.rejectCreditApplication(id);
        return new ResponseEntity<>(updatedCredit, HttpStatus.OK);
    }
}

