package app.project.InsuranceService.controller;

import app.project.InsuranceService.dto.response.Auth.ApiResponse;
import app.project.InsuranceService.dto.response.ContractDetails.HealthContractDetailResponse;
import app.project.InsuranceService.dto.response.ContractDetails.LifeContractDetailResponse;
import app.project.InsuranceService.dto.response.ContractDetails.TravelContractDetailResponse;
import app.project.InsuranceService.dto.response.ContractDetails.VehicleContractDetailResponse;
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

    @GetMapping("/life/{lifeId}")
    public ResponseEntity<ApiResponse<LifeContractDetailResponse>> getLifeContractDetail(@PathVariable String lifeId) {
        log.info("get life contract detail by id: {}", lifeId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<LifeContractDetailResponse>builder()
                        .code(1000)
                        .message("get health contract detail successfully")
                        .result(contractDetailsService.getLifeContractDetail(lifeId))
                        .build());
    }

    @GetMapping("/travel/{travelId}")
    public ResponseEntity<ApiResponse<TravelContractDetailResponse>> getTravelContractDetail(@PathVariable String travelId) {
        log.info("get travel contract detail by id: {}", travelId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<TravelContractDetailResponse>builder()
                        .code(1000)
                        .message("get health contract detail successfully")
                        .result(contractDetailsService.getTravelContractDetail(travelId))
                        .build());
    }

    @GetMapping("/vehicle/{vehicleId}")
    public ResponseEntity<ApiResponse<VehicleContractDetailResponse>> getVehicleContractDetail(@PathVariable String vehicleId) {
        log.info("get vehicle contract detail by id: {}", vehicleId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<VehicleContractDetailResponse>builder()
                        .code(1000)
                        .message("get health contract detail successfully")
                        .result(contractDetailsService.getVehicleContractDetail(vehicleId))
                        .build());
    }
}
