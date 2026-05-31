package app.project.InsuranceService.service.Claim;

import app.project.InsuranceService.dto.request.Claim.ClaimAdminUpdateRequest;
import app.project.InsuranceService.dto.request.Claim.ClaimCreationRequest;
import app.project.InsuranceService.dto.request.Claim.ClaimUserUpdateRequest;
import app.project.InsuranceService.dto.response.Claim.ClaimResponse;
import app.project.InsuranceService.enums.ClaimStatus;

import java.util.List;


public interface ClaimService {

    ClaimResponse addClaim(ClaimCreationRequest claim);

    ClaimResponse getDetailClaim(String claimId);

    List<ClaimResponse> getAllClaimsCustomer(int pageNo, int pageSize, ClaimStatus status, String userId);

    List<ClaimResponse> getAllMyClaims(int pageNo, int pageSize, ClaimStatus status);

    List<ClaimResponse> getAllClaim(int pageNo, int pageSize, ClaimStatus status);

    ClaimResponse adminReviewClaim(String claimId);

    ClaimResponse adminNeedInfoClaim(String claimId);

    ClaimResponse adminApprovedClaim(ClaimAdminUpdateRequest request, String claimId);

    ClaimResponse adminRejectedClaim(ClaimAdminUpdateRequest request, String claimId);

    ClaimResponse adminPaidClaim(ClaimAdminUpdateRequest request, String claimId);

    ClaimResponse userUpdateClaim(ClaimUserUpdateRequest request, String claimId);
}
