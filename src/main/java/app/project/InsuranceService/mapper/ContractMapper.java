package app.project.InsuranceService.mapper;

import app.project.InsuranceService.dto.request.Contract.ContractCancelRequest;
import app.project.InsuranceService.dto.request.Contract.ContractCreationRequest;
import app.project.InsuranceService.dto.request.Policy.PolicyCreationRequest;
import app.project.InsuranceService.dto.request.Policy.PolicyUpdateRequest;
import app.project.InsuranceService.dto.request.User.UserUpdateRequest;
import app.project.InsuranceService.dto.response.Contract.ContractResponse;
import app.project.InsuranceService.dto.response.Policy.PolicyResponse;
import app.project.InsuranceService.entity.Contract;
import app.project.InsuranceService.entity.Policy;
import app.project.InsuranceService.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ContractMapper {
    Contract toContract(ContractCreationRequest request);

    @Mapping(source = "contractId", target = "contractId")
    @Mapping(source = "policy.policyCode", target = "policyCode")
    @Mapping(source = "policy.name", target = "policyName")
    ContractResponse toContractResponse(Contract contract);

}
