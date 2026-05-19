package app.project.InsuranceService.repository;

import app.project.InsuranceService.entity.Claim;
import app.project.InsuranceService.entity.User;
import app.project.InsuranceService.enums.ClaimStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface ClaimRepository extends JpaRepository<Claim, String> {
    long countByCustomerAndCreatedAtAfter(User customer, LocalDateTime fromDate);

    Page<Claim> findByCustomer_IdAndStatus(String userId, ClaimStatus status, Pageable pageable);

    Page<Claim> findByStatus(ClaimStatus status, Pageable pageable);

    Page<Claim> findByCustomer_Id(String userId, Pageable pageable);
}
