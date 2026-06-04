package app.project.InsuranceService.repository;

import app.project.InsuranceService.entity.Payment;
import app.project.InsuranceService.enums.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, String> {

    boolean existsByContract_ContractIdAndPaymentStatus(String contractId, PaymentStatus status);

    Optional<Payment> findByTransactionReference(String transactionReference);

}
