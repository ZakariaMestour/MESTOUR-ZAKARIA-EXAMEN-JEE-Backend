package poo.web;

import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import poo.dtos.RepaymentDTO;
import poo.enums.RepaymentType;
import poo.services.interfaces.RepaymentService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/repayments")
@CrossOrigin("*")
public class RepaymentController {
    private final RepaymentService repaymentService;

    @Autowired
    public RepaymentController(RepaymentService repaymentService) {
        this.repaymentService = repaymentService;
    }

    @GetMapping
    public ResponseEntity<List<RepaymentDTO>> getAllRepayments() {
        List<RepaymentDTO> repayments = repaymentService.getAllRepayments();
        return new ResponseEntity<>(repayments, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RepaymentDTO> getRepaymentById(@PathVariable Long id) {
        RepaymentDTO repayment = repaymentService.getRepaymentById(id);
        return new ResponseEntity<>(repayment, HttpStatus.OK);
    }

    @GetMapping("/credit/{creditId}")
    public ResponseEntity<List<RepaymentDTO>> getRepaymentsByCreditId(@PathVariable Long creditId) {
        List<RepaymentDTO> repayments = repaymentService.getRepaymentsByCreditId(creditId);
        return new ResponseEntity<>(repayments, HttpStatus.OK);
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<List<RepaymentDTO>> getRepaymentsByType(@PathVariable RepaymentType type) {
        List<RepaymentDTO> repayments = repaymentService.getRepaymentsByType(type);
        return new ResponseEntity<>(repayments, HttpStatus.OK);
    }

    @GetMapping("/date-range")
    public ResponseEntity<List<RepaymentDTO>> getRepaymentsByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<RepaymentDTO> repayments = repaymentService.getRepaymentsByDateRange(startDate, endDate);
        return new ResponseEntity<>(repayments, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<RepaymentDTO> createRepayment(@RequestBody RepaymentDTO repaymentDTO) {
        RepaymentDTO createdRepayment = repaymentService.createRepayment(repaymentDTO);
        return new ResponseEntity<>(createdRepayment, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RepaymentDTO> updateRepayment(@PathVariable Long id, @RequestBody RepaymentDTO repaymentDTO) {
        RepaymentDTO updatedRepayment = repaymentService.updateRepayment(id, repaymentDTO);
        return new ResponseEntity<>(updatedRepayment, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRepayment(@PathVariable Long id) {
        repaymentService.deleteRepayment(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/credit/{creditId}/total-repaid")
    public ResponseEntity<Double> calculateTotalRepaidAmount(@PathVariable Long creditId) {
        Double totalAmount = repaymentService.calculateTotalRepaidAmount(creditId);
        return new ResponseEntity<>(totalAmount, HttpStatus.OK);
    }

    @GetMapping("/credit/{creditId}/remaining")
    public ResponseEntity<Double> calculateRemainingAmount(@PathVariable Long creditId) {
        Double remainingAmount = repaymentService.calculateRemainingAmount(creditId);
        return new ResponseEntity<>(remainingAmount, HttpStatus.OK);
    }

}
