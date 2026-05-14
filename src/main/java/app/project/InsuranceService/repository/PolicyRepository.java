package app.project.InsuranceService.repository;

import app.project.InsuranceService.entity.Policy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PolicyRepository extends JpaRepository<Policy, String> {
    boolean existsByName(String name);
    boolean existsByPolicyCode(String policyCode);
}
