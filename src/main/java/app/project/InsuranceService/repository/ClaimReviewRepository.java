package app.project.InsuranceService.repository;

import app.project.InsuranceService.entity.Claim;
import app.project.InsuranceService.entity.ClaimReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClaimReviewRepository extends JpaRepository<ClaimReview, String> {
    List<ClaimReview> findByClaim(Claim claim);
}
