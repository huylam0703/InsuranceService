package app.project.InsuranceService.service.Contract;

import app.project.InsuranceService.dto.request.Contract.ContractCancelRequest;
import app.project.InsuranceService.dto.request.Contract.ContractCreationRequest;
import app.project.InsuranceService.dto.response.Contract.ContractResponse;
import app.project.InsuranceService.enums.ContractStatus;

import java.util.List;

public interface ContractService {

    ContractResponse purchaseContract(ContractCreationRequest request);

    ContractResponse getDetailContract(String contractId);

    List<ContractResponse> getAllContractsByUserId(int pageNo, int pageSize, ContractStatus status, String userId);

    List<ContractResponse> getAllContracts(int pageNo, int pageSize, ContractStatus status);

    List<ContractResponse> getAllMyContracts(int pageNo, int pageSize, ContractStatus status);

    ContractResponse cancelContract(String contractId);
}
