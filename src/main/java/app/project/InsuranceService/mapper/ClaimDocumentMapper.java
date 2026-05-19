package app.project.InsuranceService.mapper;

import app.project.InsuranceService.dto.request.Claim.ClaimAdminUpdateRequest;
import app.project.InsuranceService.dto.request.Claim.ClaimCreationRequest;
import app.project.InsuranceService.dto.request.ClaimDocument.ClaimDocumentRequest;
import app.project.InsuranceService.dto.response.ClaimDocument.ClaimDocumentResponse;
import app.project.InsuranceService.entity.Claim;
import app.project.InsuranceService.entity.ClaimDocument;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ClaimDocumentMapper {

    @Mapping(source = "claim.id", target = "claimId")
    @Mapping(source = "uploadedBy.id", target = "uploadedByUserId")
    @Mapping(source = "uploadedBy.username", target = "uploadedByUsername")
    ClaimDocumentResponse toClaimDocumentResponse(ClaimDocument document);

}
