package app.project.InsuranceService.repository;

import app.project.InsuranceService.entity.Bank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BankRepository extends JpaRepository<Bank, Integer> {

    Optional<Bank> findByCode(String code);

    List<Bank> findAllByOrderByShortNameAsc();
}
