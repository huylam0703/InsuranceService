package app.project.InsuranceService.service.Contract;

import app.project.InsuranceService.dto.request.Contract.ContractCreationRequest;
import app.project.InsuranceService.dto.response.Contract.ContractResponse;
import app.project.InsuranceService.enums.ContractStatus;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ContractService {

    ContractResponse purchaseContract(ContractCreationRequest request, MultipartFile vehicleImage) throws IOException;

    ContractResponse getDetailContract(String contractId);

    List<ContractResponse> getAllContractsByUserId(int pageNo, int pageSize, ContractStatus status, String userId);

    List<ContractResponse> getAllContracts(int pageNo, int pageSize, ContractStatus status);

    List<ContractResponse> getAllMyContracts(int pageNo, int pageSize, ContractStatus status);

    ContractResponse cancelContract(String contractId);
}
