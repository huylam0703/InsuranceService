package app.project.InsuranceService.mapper;

import app.project.InsuranceService.dto.response.ClaimReview.ClaimReviewResponse;
import app.project.InsuranceService.entity.ClaimReview;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ClaimReviewMapper {

    @Mapping(source = "claim.id", target = "claimId")
    @Mapping(source = "reviewer.id", target = "reviewerId")
    @Mapping(source = "reviewer.username", target = "reviewerUsername")
    ClaimReviewResponse toClaimReviewResponse(ClaimReview claimReview);
}
