package app.project.InsuranceService.controller;

import app.project.InsuranceService.dto.request.Contract.ContractCancelRequest;
import app.project.InsuranceService.dto.request.Contract.ContractCreationRequest;
import app.project.InsuranceService.dto.response.Auth.ApiResponse;
import app.project.InsuranceService.dto.response.Contract.ContractResponse;
import app.project.InsuranceService.enums.ContractStatus;
import app.project.InsuranceService.service.Contract.ContractService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/contracts")
public class ContractController {
    ContractService contractService;

    @PostMapping(value = "/purchase",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<ContractResponse>> purchaseContract
            (@RequestPart("request") @Valid ContractCreationRequest request,
             @RequestPart(value = "vehicleImage", required = false) MultipartFile vehicleImage) throws IOException {

        log.info("Request to purchase contract");

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<ContractResponse>builder()
                        .code(1000)
                        .message("Purchase contract")
                        .result(contractService.purchaseContract(request, vehicleImage))
                        .build());
    }

    @GetMapping("/{contractId}")
    public ResponseEntity<ApiResponse<ContractResponse>> getDetailContract(@PathVariable String contractId){
        log.info("Request to get detail contract");

        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<ContractResponse>builder()
                        .code(1000)
                        .message("Get detail contract")
                        .result(contractService.getDetailContract(contractId))
                        .build());
    }

    @GetMapping("/user")
    public ResponseEntity<ApiResponse<List<ContractResponse>>> getAllContractsByUserId(@RequestParam(defaultValue = "1") int pageNo,
                                                             @RequestParam(defaultValue = "5") int pageSize,
                                                             @RequestParam(required = false) ContractStatus status,
                                                             @RequestParam(required = false) String userId){
        log.info("Request to get all contracts by user");

        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<ContractResponse>>builder()
                        .code(1000)
                        .message("Get contracts")
                        .result(contractService.getAllContractsByUserId(pageNo,pageSize,status,userId))
                        .build());
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ContractResponse>>> getAllContracts(@RequestParam(defaultValue = "1") int pageNo,
                                                                                       @RequestParam(defaultValue = "10") int pageSize,
                                                                                       @RequestParam(required = false) ContractStatus status){
        log.info("Request to get all contracts");

        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<ContractResponse>>builder()
                        .code(1000)
                        .message("Get all contracts")
                        .result(contractService.getAllContracts(pageNo,pageSize,status))
                        .build());
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<List<ContractResponse>>> getAllMyContracts(@RequestParam(defaultValue = "1") int pageNo,
                                                                               @RequestParam(defaultValue = "5") int pageSize,
                                                                               @RequestParam(required = false) ContractStatus status){
        log.info("Request to get all my contracts");

        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<ContractResponse>>builder()
                        .code(1000)
                        .message("Get all my contracts")
                        .result(contractService.getAllMyContracts(pageNo,pageSize,status))
                        .build());
    }

    @PutMapping("/{contractId}")
    public ResponseEntity<ApiResponse<ContractResponse>> cancelContract(@PathVariable String contractId){
        log.info("Request to cancel contract");

        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(ApiResponse.<ContractResponse>builder()
                        .code(1000)
                        .message("Cancel contract")
                        .result(contractService.cancelContract(contractId))
                        .build());

    }

}
