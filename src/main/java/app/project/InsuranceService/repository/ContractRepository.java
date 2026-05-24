package app.project.InsuranceService.repository;

import app.project.InsuranceService.entity.Contract;
import app.project.InsuranceService.enums.ContractPaymentStatus;
import app.project.InsuranceService.enums.ContractStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContractRepository extends JpaRepository<Contract, String> {
    Page<Contract> findByUser_Id(String userId, Pageable pageable);

    Page<Contract> findByUser_IdAndContractStatus(String userId, ContractStatus contractStatus, Pageable pageable);

    Page<Contract> findByContractStatus(ContractStatus contractStatus, Pageable pageable);

    long countByContractStatusAndPaymentStatus(ContractStatus contractStatus, ContractPaymentStatus paymentStatus);
}
