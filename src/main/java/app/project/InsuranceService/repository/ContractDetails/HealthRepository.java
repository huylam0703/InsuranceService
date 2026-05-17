package app.project.InsuranceService.repository.ContractDetails;

import app.project.InsuranceService.entity.Contract;
import app.project.InsuranceService.entity.DetailsContractType.HealthContractDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HealthRepository extends JpaRepository<HealthContractDetails, String> {
    Optional<HealthContractDetails> findByContract(Contract contract);
}
