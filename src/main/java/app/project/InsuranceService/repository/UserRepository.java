package app.project.InsuranceService.repository;

import app.project.InsuranceService.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Boolean existsByUsername(String username);
}
