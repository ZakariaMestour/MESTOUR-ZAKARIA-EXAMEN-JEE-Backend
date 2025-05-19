package poo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import poo.entities.*;
import poo.enums.CreditStatus;
import poo.enums.PersonalCreditReason;
import poo.enums.PropertyType;
import poo.enums.RepaymentType;
import poo.repositories.ClientRepository;
import poo.repositories.CreditRepository;

import java.time.LocalDate;
import java.util.ArrayList;

@SpringBootApplication
public class ExamenApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExamenApplication.class, args);
	}
	//@Bean
	public CommandLineRunner seedData(ClientRepository clientRepository,
									  CreditRepository creditRepository) {
		return args -> {
			if (clientRepository.count() == 0) {
				// Création des clients
				Client client1 = new Client(null, "Alice Dupont", "alice.dupont@mail.com", new ArrayList<>());
				Client client2 = new Client(null, "Pierre Martin", "pierre.martin@mail.com", new ArrayList<>());

				clientRepository.save(client1);
				clientRepository.save(client2);

				// 1. Crédit hypothécaire (APPARTMENT)
				MortgageCredit mortgage = new MortgageCredit();
				setCommonCreditProperties(mortgage, LocalDate.now().minusMonths(1),
						CreditStatus.ACCEPTED, LocalDate.now().minusWeeks(3),
						250000.0, 240, 3.2, client1);
				mortgage.setPropertyType(PropertyType.APARTMENT);
				addRepayment(mortgage, LocalDate.now().plusMonths(1), 1200.0, RepaymentType.MONTHLY_PAYMENT);
				creditRepository.save(mortgage);

				// 2. Crédit professionnel (PENDING)
				ProfessionalCredit pro = new ProfessionalCredit();
				setCommonCreditProperties(pro, LocalDate.now().minusDays(15),
						CreditStatus.PENDING, null,
						50000.0, 60, 5.5, client2);
				pro.setReason("Extension des locaux");
				pro.setCompanyName("Tech Innov");
				addRepayment(pro, LocalDate.now().plusMonths(1), 900.0, RepaymentType.MONTHLY_PAYMENT);
				creditRepository.save(pro);

				// 3. Crédit personnel (CAR_PURCHASE - REJECTED)
				PersonalCredit perso1 = new PersonalCredit();
				setCommonCreditProperties(perso1, LocalDate.now().minusDays(10),
						CreditStatus.REJECTED, null,
						15000.0, 24, 7.0, client1);
				perso1.setReason(PersonalCreditReason.CAR_PURCHASE);
				creditRepository.save(perso1);

				// 4. Crédit personnel (EDUCATION - ACCEPTED)
				PersonalCredit perso2 = new PersonalCredit();
				setCommonCreditProperties(perso2, LocalDate.now().minusDays(5),
						CreditStatus.ACCEPTED, LocalDate.now().minusDays(2),
						8000.0, 12, 6.5, client2);
				perso2.setReason(PersonalCreditReason.EDUCATION);
				addRepayment(perso2, LocalDate.now().plusMonths(1), 700.0, RepaymentType.MONTHLY_PAYMENT);
				addRepayment(perso2, LocalDate.now().plusMonths(2), 700.0, RepaymentType.EARLY_REPAYMENT);
				creditRepository.save(perso2);

				// 5. Crédit hypothécaire (HOUSE - PENDING)
				MortgageCredit mortgage2 = new MortgageCredit();
				setCommonCreditProperties(mortgage2, LocalDate.now().minusDays(7),
						CreditStatus.PENDING, null,
						180000.0, 180, 2.9, client2);
				mortgage2.setPropertyType(PropertyType.HOUSE);
				creditRepository.save(mortgage2);
			}
		};
	}

	private void setCommonCreditProperties(Credit credit, LocalDate requestDate,
										   CreditStatus status, LocalDate acceptanceDate,
										   Double amount, Integer duration,
										   Double rate, Client client) {
		credit.setRequestDate(requestDate);
		credit.setStatus(status);
		credit.setAcceptanceDate(acceptanceDate);
		credit.setAmount(amount);
		credit.setDurationMonths(duration);
		credit.setInterestRate(rate);
		credit.setClient(client);
	}

	private void addRepayment(Credit credit, LocalDate date, Double amount, RepaymentType type) {
		Repayment repayment = new Repayment(null, date, amount, type, credit);
		if (credit.getRepayments() == null) {
			credit.setRepayments(new ArrayList<>());
		}
		credit.getRepayments().add(repayment);
	}
}
