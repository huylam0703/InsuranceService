package app.project.InsuranceService.service.UserBankAccount;

import app.project.InsuranceService.dto.request.UserBankAccount.UserBankAccountRequest;
import app.project.InsuranceService.dto.response.UserBankAccount.UserBankAccountResponse;

public interface UserBankAccountService {

    UserBankAccountResponse createBankAccount(UserBankAccountRequest request);

    UserBankAccountResponse getMyBankAccount();

    UserBankAccountResponse getUserBankAccount(String userId);
}
