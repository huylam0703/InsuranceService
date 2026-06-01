package app.project.InsuranceService.controller;

import app.project.InsuranceService.dto.request.Claim.ClaimAdminUpdateRequest;
import app.project.InsuranceService.dto.request.Claim.ClaimCreationRequest;
import app.project.InsuranceService.dto.request.Claim.ClaimUserUpdateRequest;
import app.project.InsuranceService.dto.response.Auth.ApiResponse;
import app.project.InsuranceService.dto.response.Claim.ClaimResponse;
import app.project.InsuranceService.enums.ClaimStatus;
import app.project.InsuranceService.enums.ContractStatus;
import app.project.InsuranceService.service.Claim.ClaimService;
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
@RequestMapping("/claims")
public class ClaimController {
    ClaimService claimService;

    @PostMapping
    public ResponseEntity<ApiResponse<ClaimResponse>> addClaim(@RequestBody @Valid ClaimCreationRequest request) {
        log.info("Add claim request: {}", request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<ClaimResponse>builder()
                        .code(1000)
                        .message("add claim success")
                        .result(claimService.addClaim(request))
                        .build());
    }

    @GetMapping("/{claimId}")
    public ResponseEntity<ApiResponse<ClaimResponse>> getDetailClaim(@PathVariable String claimId) {
        log.info("Get detail claim request: {}", claimId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<ClaimResponse>builder()
                        .code(1000)
                        .message("get detail claim success")
                        .result(claimService.getDetailClaim(claimId))
                        .build());
    }

    @GetMapping("/list/customer")
    public ResponseEntity<ApiResponse<List<ClaimResponse>>> getAllClaimsCustomer(@RequestParam(defaultValue = "1") int pageNo,
                                                                                 @RequestParam(defaultValue = "5") int pageSize,
                                                                                 @RequestParam(required = false) ClaimStatus status,
                                                                                 @RequestParam(required = false) String userId) {
        log.info("Get all claims customer request");

        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<ClaimResponse>>builder()
                        .code(1000)
                        .message("get all claims customer success")
                        .result(claimService.getAllClaimsCustomer(pageNo, pageSize, status, userId))
                        .build());
    }

    @GetMapping("/list/myClaim")
    public ResponseEntity<ApiResponse<List<ClaimResponse>>> getAllMyClaim(@RequestParam(defaultValue = "1") int pageNo,
                                                                                 @RequestParam(defaultValue = "5") int pageSize,
                                                                                 @RequestParam(required = false) ClaimStatus status) {
        log.info("Get all my claims request");

        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<ClaimResponse>>builder()
                        .code(1000)
                        .message("get all my claims success")
                        .result(claimService.getAllMyClaims(pageNo, pageSize, status))
                        .build());
    }

    @GetMapping("/list/claim")
    public ResponseEntity<ApiResponse<List<ClaimResponse>>> getAllClaim(@RequestParam(defaultValue = "1") int pageNo,
                                                                          @RequestParam(defaultValue = "5") int pageSize,
                                                                          @RequestParam(required = false) ClaimStatus status) {
        log.info("Get all claims request");

        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<ClaimResponse>>builder()
                        .code(1000)
                        .message("get all claims success")
                        .result(claimService.getAllClaim(pageNo, pageSize, status))
                        .build());
    }

    @PutMapping("/review/{claimId}")
    public ResponseEntity<ApiResponse<ClaimResponse>> adminReviewClaim(@PathVariable String claimId) {
        log.info("Admin review claim request: {}", claimId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<ClaimResponse>builder()
                        .code(1000)
                        .message("adim Review Claim success")
                        .result(claimService.adminReviewClaim(claimId))
                        .build());

    }

    @PutMapping("/info/{claimId}")
    public ResponseEntity<ApiResponse<ClaimResponse>> adminNeedInfoClaim(@PathVariable String claimId) {
        log.info("adminNeedInfoClaim claim request: {}", claimId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<ClaimResponse>builder()
                        .code(1000)
                        .message("adim Need info Claim success")
                        .result(claimService.adminNeedInfoClaim(claimId))
                        .build());
    }

    @PutMapping("/approved/{claimId}")
    public ResponseEntity<ApiResponse<ClaimResponse>> adminApprovedClaim(@RequestBody @Valid ClaimAdminUpdateRequest request,
                                                                         @PathVariable String claimId) {
        log.info("adminApprovedClaim claim request: {}", claimId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<ClaimResponse>builder()
                        .code(1000)
                        .message("adim Approved Claim success")
                        .result(claimService.adminApprovedClaim(request, claimId))
                        .build());

    }

    @PutMapping("/reject/{claimId}")
    public ResponseEntity<ApiResponse<ClaimResponse>> adminRejectClaim(@RequestBody @Valid ClaimAdminUpdateRequest request,
                                                                       @PathVariable String claimId) {
        log.info("adminRejectClaim claim request: {}", claimId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<ClaimResponse>builder()
                        .code(1000)
                        .message("adim Rejected Claim success")
                        .result(claimService.adminRejectedClaim(request, claimId))
                        .build());
    }

    @PutMapping("/paid/{claimId}")
    public ResponseEntity<ApiResponse<ClaimResponse>> adminPaidClaim(@PathVariable String claimId) {
        log.info("adminPaidClaim claim request: {}", claimId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<ClaimResponse>builder()
                        .code(1000)
                        .message("adim Paid Claim success")
                        .result(claimService.adminPaidClaim(claimId))
                        .build());
    }

    @PutMapping("/{claimId}")
    public ResponseEntity<ApiResponse<ClaimResponse>> userUpdateClaim(@RequestBody @Valid ClaimUserUpdateRequest request,
                                                                      @PathVariable String claimId) {
        log.info("userUpdateClaim claim request: {}", claimId);

        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(ApiResponse.<ClaimResponse>builder()
                        .code(1000)
                        .message("update claim success")
                        .result(claimService.userUpdateClaim(request, claimId))
                        .build());
    }
}
