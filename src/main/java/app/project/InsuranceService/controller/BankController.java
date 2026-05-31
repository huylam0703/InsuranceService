package app.project.InsuranceService.controller;

import app.project.InsuranceService.dto.response.Auth.ApiResponse;
import app.project.InsuranceService.dto.response.Bank.BankResponse;
import app.project.InsuranceService.service.Bank.BankService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/banks")
public class BankController {
    BankService bankService;

    @GetMapping("/list")
    public ResponseEntity<ApiResponse<List<BankResponse>>> getAllBanks() {
        log.info("get All Banks");

        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<BankResponse>>builder()
                        .code(1000)
                        .message("get all bank success")
                        .result(bankService.getAllBanks())
                        .build());
    }
}
