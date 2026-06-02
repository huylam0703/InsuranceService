package app.project.InsuranceService.controller;

import app.project.InsuranceService.dto.response.Auth.ApiResponse;
import app.project.InsuranceService.dto.response.ClaimReview.ClaimReviewResponse;
import app.project.InsuranceService.dto.response.PageResponse;
import app.project.InsuranceService.service.ClaimReview.ClaimReviewService;
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
@RequestMapping("/claim-review")
public class ClaimReviewController {
    ClaimReviewService claimReviewService;

    @GetMapping("/{claimId}")
    public ResponseEntity<ApiResponse<List<ClaimReviewResponse>>> getClaimReviewByClaimId(@PathVariable String claimId) {
        log.info("claimId: {}", claimId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<ClaimReviewResponse>>builder()
                        .code(1000)
                        .message("get claim review success")
                        .result(claimReviewService.getClaimReviewByClaimId(claimId))
                        .build());
    }

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<ClaimReviewResponse>>> getAllClaimReviews(@RequestParam(defaultValue = "1") int pageNo,
                                                                                             @RequestParam(defaultValue = "5") int pageSize) {

        log.info("get all claim reviews");

        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<PageResponse<ClaimReviewResponse>>builder()
                        .code(1000)
                        .message("get all claim reviews")
                        .result(claimReviewService.getAllClaimReview(pageNo, pageSize))
                        .build());
    }
}
