package app.project.InsuranceService.repository;

import app.project.InsuranceService.entity.UserBankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserBankAccountRepository extends JpaRepository<UserBankAccount, String> {
    Optional<UserBankAccount> findByUser_Id(String userId);

    Optional<UserBankAccount> findByIdAndUser_Id(String id, String userId);

}
