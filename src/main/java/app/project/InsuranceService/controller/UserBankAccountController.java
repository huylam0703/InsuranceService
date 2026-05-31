package app.project.InsuranceService.controller;

import app.project.InsuranceService.dto.request.UserBankAccount.UserBankAccountRequest;
import app.project.InsuranceService.dto.response.Auth.ApiResponse;
import app.project.InsuranceService.dto.response.UserBankAccount.UserBankAccountResponse;
import app.project.InsuranceService.service.UserBankAccount.UserBankAccountService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/bankAccount")
public class UserBankAccountController {
    UserBankAccountService userBankAccountService;

    @PostMapping("create-bank-account")
    public ResponseEntity<ApiResponse<UserBankAccountResponse>> createBankAccount(
            @RequestBody @Valid UserBankAccountRequest request) {

        log.info("create bank account");

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<UserBankAccountResponse>builder()
                        .code(1000)
                        .message("create bank account success")
                        .result(userBankAccountService.createBankAccount(request))
                        .build());
    }

    @GetMapping("my-bank-account")
    public ResponseEntity<ApiResponse<UserBankAccountResponse>> getMyBankAccount() {
        log.info("get my bank account");

        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<UserBankAccountResponse>builder()
                        .code(1000)
                        .message("get my bank account success")
                        .result(userBankAccountService.getMyBankAccount())
                        .build());
    }

    @GetMapping("/user-bank-account/{userId}")
    public ResponseEntity<ApiResponse<UserBankAccountResponse>> getUserBankAccount(@PathVariable String userId) {
        log.info("get user bank account");

        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<UserBankAccountResponse>builder()
                        .code(1000)
                        .message("get user bank account success")
                        .result(userBankAccountService.getUserBankAccount(userId))
                        .build());
    }
}
