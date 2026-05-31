package app.project.InsuranceService.service.UserBankAccount.Impl;

import app.project.InsuranceService.dto.request.UserBankAccount.UserBankAccountRequest;
import app.project.InsuranceService.dto.response.UserBankAccount.UserBankAccountResponse;
import app.project.InsuranceService.entity.Bank;
import app.project.InsuranceService.entity.User;
import app.project.InsuranceService.entity.UserBankAccount;
import app.project.InsuranceService.exception.AppException;
import app.project.InsuranceService.exception.ErrorCode;
import app.project.InsuranceService.mapper.UserBankAccountMapper;
import app.project.InsuranceService.repository.BankRepository;
import app.project.InsuranceService.repository.UserBankAccountRepository;
import app.project.InsuranceService.repository.UserRepository;
import app.project.InsuranceService.service.UserBankAccount.UserBankAccountService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserBankAccountServiceImpl implements UserBankAccountService {
    UserBankAccountRepository userBankAccountRepository;
    UserBankAccountMapper userBankAccountMapper;
    BankRepository bankRepository;
    UserRepository userRepository;


    @Override
    @PreAuthorize("hasRole('USER')")
    public UserBankAccountResponse createBankAccount(UserBankAccountRequest request) {
        Bank bank = bankRepository.findByCode(request.getBankCode())
                .orElseThrow(()-> new AppException(ErrorCode.BANK_NOT_FOUND));

        User user = getCurrentUser();

        UserBankAccount userBankAccount = UserBankAccount.builder()
                .user(user)
                .bank(bank)
                .accountNumber(request.getAccountNumber())
                .accountHolderName(request.getAccountHolderName())
                .build();

        UserBankAccount savedUserBankAccount = userBankAccountRepository.save(userBankAccount);

        return userBankAccountMapper.toResponse(savedUserBankAccount);
    }

    @Override
    @PreAuthorize("isAuthenticated()")
    public UserBankAccountResponse getMyBankAccount() {
        User user = getCurrentUser();

        UserBankAccount userBankAccount = userBankAccountRepository.findByUser_Id(user.getId());
        return userBankAccountMapper.toResponse(userBankAccount);

    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public UserBankAccountResponse getUserBankAccount(String userId) {
        UserBankAccount userBankAccount = userBankAccountRepository.findByUser_Id(userId);
        return userBankAccountMapper.toResponse(userBankAccount);
    }


    private User getCurrentUser() {
        var context = SecurityContextHolder.getContext();

        String username = context.getAuthentication().getName();

        return userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTS));
    }
}
