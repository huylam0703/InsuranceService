package app.project.InsuranceService.service.Bank;

import app.project.InsuranceService.dto.response.Bank.BankResponse;

import java.util.List;

public interface BankService {
    List<BankResponse> getAllBanks();
}
