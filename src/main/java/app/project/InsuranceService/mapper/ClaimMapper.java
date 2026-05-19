package app.project.InsuranceService.mapper;

import app.project.InsuranceService.dto.request.Claim.ClaimAdminUpdateRequest;
import app.project.InsuranceService.dto.request.Claim.ClaimCreationRequest;
import app.project.InsuranceService.dto.request.Claim.ClaimUserUpdateRequest;
import app.project.InsuranceService.dto.response.Claim.ClaimResponse;
import app.project.InsuranceService.entity.Claim;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ClaimMapper {
    Claim toClaim(ClaimCreationRequest request);

    @Mapping(source = "contract.contractId", target = "contractId")
    @Mapping(source = "contract.contractCode", target = "contractCode")

    @Mapping(source = "customer.id", target = "customerId")
    @Mapping(source = "customer.username", target = "customerUsername")

    ClaimResponse toClaimResponse(Claim claim);

    void updateClaim(ClaimAdminUpdateRequest request, @MappingTarget Claim claim);

    void userUpdateClaim(ClaimUserUpdateRequest request, @MappingTarget Claim claim);
}
