package app.project.InsuranceService.controller;

import app.project.InsuranceService.dto.request.Policy.PolicyCreationRequest;
import app.project.InsuranceService.dto.request.Policy.PolicyUpdateRequest;
import app.project.InsuranceService.dto.response.Auth.ApiResponse;
import app.project.InsuranceService.dto.response.Policy.PolicyResponse;
import app.project.InsuranceService.service.Policy.PolicyService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/policies")
public class PolicyController {
    PolicyService policyService;

    @PostMapping
    public ResponseEntity<ApiResponse<PolicyResponse>> createPolicy (@RequestBody @Valid PolicyCreationRequest request){
        log.info("Create policy");

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<PolicyResponse>builder()
                        .code(1000)
                        .message("Create policy successfully")
                        .result(policyService.createPolicy(request))
                        .build());
    }

    @GetMapping("/{policyId}")
    public ResponseEntity<ApiResponse<PolicyResponse>> getPolicy (@PathVariable("policyId") String policyId){
        log.info("Get policy");

        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<PolicyResponse>builder()
                        .code(1000)
                        .message("Get policy successfully")
                        .result(policyService.getPolicy(policyId))
                        .build());

    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<PolicyResponse>>> getPolicies (){
        log.info("Get All policies");

        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<PolicyResponse>>builder()
                        .code(1000)
                        .message("Get All policies successfully")
                        .result(policyService.getAllPolicies())
                        .build());
    }

    @PutMapping("/{policyId}")
    public ResponseEntity<ApiResponse<PolicyResponse>> updatePolicy (@PathVariable String policyId,
                                                                     @RequestBody @Valid PolicyUpdateRequest request){
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<PolicyResponse>builder()
                        .code(1000)
                        .message("Update policy successfully")
                        .result(policyService.updatePolicy(request,policyId))
                        .build());
    }

    @DeleteMapping("/{policyId}")
    public ResponseEntity<ApiResponse<String>> deletePolicy (@PathVariable String policyId){
        log.info("Delete policy");
        policyService.deletePolicy(policyId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<String>builder()
                        .code(1000)
                        .result("Delete policy successfully")
                        .build());
    }
}
