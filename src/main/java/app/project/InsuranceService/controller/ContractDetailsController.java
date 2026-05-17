package app.project.InsuranceService.controller;

import app.project.InsuranceService.dto.response.Auth.ApiResponse;
import app.project.InsuranceService.dto.response.ContractDetails.HealthContractDetailResponse;
import app.project.InsuranceService.service.ContractDetails.ContractDetailsService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/contracts")
public class ContractDetailsController {
    ContractDetailsService contractDetailsService;

    @GetMapping("/health/{healthId}")
    public ResponseEntity<ApiResponse<HealthContractDetailResponse>> getHealthContractDetail(@PathVariable String healthId) {
        log.info("get health contract detail by id: {}", healthId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<HealthContractDetailResponse>builder()
                        .code(1000)
                        .message("get health contract detail successfully")
                        .result(contractDetailsService.getHealthContractDetail(healthId))
                        .build());
    }
}
