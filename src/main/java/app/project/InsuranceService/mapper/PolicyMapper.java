package app.project.InsuranceService.mapper;

import app.project.InsuranceService.dto.request.Policy.PolicyCreationRequest;
import app.project.InsuranceService.dto.request.Policy.PolicyUpdateRequest;
import app.project.InsuranceService.dto.response.Policy.PolicyResponse;
import app.project.InsuranceService.entity.Policy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PolicyMapper {
    Policy toPolicy(PolicyCreationRequest request);

    PolicyResponse toPolicyResponse(Policy policy);

    void updatePolicy(PolicyUpdateRequest request, @MappingTarget Policy policy);
}
