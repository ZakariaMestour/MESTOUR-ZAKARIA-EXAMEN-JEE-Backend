package poo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import poo.entities.MortgageCredit;
import poo.enums.PropertyType;

import java.util.List;
@Repository
public interface MortgageCreditRepository extends JpaRepository<MortgageCredit, Long> {
    List<MortgageCredit> findByPropertyType(PropertyType propertyType);
}
