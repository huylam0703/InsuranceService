package app.project.InsuranceService.repository;

import app.project.InsuranceService.entity.Claim;
import app.project.InsuranceService.entity.ClaimDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClaimDocumentRepository extends JpaRepository<ClaimDocument, String> {
    List<ClaimDocument> findByClaim(Claim claim);

    ClaimDocument deleteByClaim(Claim claim);
}
