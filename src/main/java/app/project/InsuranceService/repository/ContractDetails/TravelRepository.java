package app.project.InsuranceService.repository.ContractDetails;

import app.project.InsuranceService.entity.Contract;
import app.project.InsuranceService.entity.DetailsContractType.HealthContractDetails;
import app.project.InsuranceService.entity.DetailsContractType.TravelContractDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TravelRepository extends JpaRepository<TravelContractDetails, String> {
    Optional<TravelContractDetails> findByContract(Contract contract);
}
