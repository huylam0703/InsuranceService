package app.project.InsuranceService.service.Contract.Impl;

import app.project.InsuranceService.dto.request.Contract.ContractCancelRequest;
import app.project.InsuranceService.dto.request.Contract.ContractCreationRequest;
import app.project.InsuranceService.dto.response.Contract.ContractResponse;
import app.project.InsuranceService.entity.Contract;
import app.project.InsuranceService.entity.Policy;
import app.project.InsuranceService.entity.User;
import app.project.InsuranceService.enums.ContractStatus;
import app.project.InsuranceService.enums.PaymentStatus;
import app.project.InsuranceService.exception.AppException;
import app.project.InsuranceService.exception.ErrorCode;
import app.project.InsuranceService.mapper.ContractMapper;
import app.project.InsuranceService.repository.ContractRepository;
import app.project.InsuranceService.repository.PolicyRepository;
import app.project.InsuranceService.repository.UserRepository;
import app.project.InsuranceService.service.Contract.ContractService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ContractServiceImpl implements ContractService {
    ContractRepository contractRepository;
    ContractMapper contractMapper;
    UserRepository userRepository;
    PolicyRepository policyRepository;

    @Override
    @PreAuthorize("hasRole('USER')")
    public ContractResponse purchaseContract(ContractCreationRequest request) {
        Policy policy = policyRepository.findById(request.getPolicy_id())
                .orElseThrow(()-> new AppException(ErrorCode.POLICY_NOT_FOUND));

        User user = getCurrentUser();

        Contract contract = Contract.builder()
                .user(user)
                .policy(policy)
                .contractCode(contractCodeGenerate(policy.getPolicyCode()))
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusMonths(policy.getDurationMonths()))
                .premiumAmount(policy.getPremiumAmount())
                .coverageAmount(policy.getCoverageAmount())
                .remainingCoverage(policy.getCoverageAmount())
                .contractStatus(ContractStatus.PENDING)
                .paymentStatus(PaymentStatus.UNPAID)
                .build();
        Contract savedContract = contractRepository.save(contract);

        return contractMapper.toContractResponse(savedContract);
    }

    @Override
    public ContractResponse getDetailContract(String contractId) {
        Contract contract = contractRepository.findById(contractId)
                .orElseThrow(() -> new AppException(ErrorCode.CONTRACT_NOT_FOUND));

        User currentUser = getCurrentUser();

        boolean isOwner = contract.getUser()
                .getId()
                .equals(currentUser.getId());

        boolean isAdminOrStaffOrManager = SecurityContextHolder.getContext()
                .getAuthentication()
                .getAuthorities()
                .stream()
                .anyMatch(auth ->
                        auth.getAuthority().equals("ROLE_ADMIN") ||
                                auth.getAuthority().equals("ROLE_STAFF") ||
                                auth.getAuthority().equals("ROLE_MANAGER")
                );

        if (!isOwner && !isAdminOrStaffOrManager) {
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }

        return contractMapper.toContractResponse(contract);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public List<ContractResponse> getAllContractsByUserId(int pageNo, int pageSize, ContractStatus status, String userId) {

        if(pageNo > 0){
            pageNo = pageNo - 1;
        }

        Pageable pageable = PageRequest.of(pageNo, pageSize);

        Page<Contract> contracts;

        // filter user + status
        if (status != null) {

            contracts = contractRepository
                    .findByUser_IdAndContractStatus(
                            userId,
                            status,
                            pageable
                    );

        }
        // chỉ filter user
        else {

            contracts = contractRepository
                    .findByUser_Id(
                            userId,
                            pageable
                    );
        }

        return contracts.stream()
                .map(contractMapper::toContractResponse).toList();
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public List<ContractResponse> getAllContracts(int pageNo, int pageSize, ContractStatus status) {
        if(pageNo > 0){
            pageNo = pageNo - 1;
        }

        Pageable pageable = PageRequest.of(pageNo, pageSize);

        Page<Contract> contracts;
        if (status != null) {
            contracts = contractRepository
                    .findByContractStatus(
                            status,
                            pageable
                    );
        }else {
            contracts = contractRepository.findAll(pageable);
        }

        return contracts.stream()
                .map(contractMapper::toContractResponse).toList();
    }

    @Override
    @PreAuthorize("hasRole('USER')")
    public List<ContractResponse> getAllMyContracts(int pageNo, int pageSize, ContractStatus status) {
        User userCurrent = getCurrentUser();

        String targetUserId = userCurrent.getId();

        if(pageNo > 0){
            pageNo = pageNo - 1;
        }

        Pageable pageable = PageRequest.of(pageNo, pageSize);

        Page<Contract> contracts;

        if (status != null) {
            contracts = contractRepository.findByUser_IdAndContractStatus(targetUserId, status, pageable);
        }
        else {
            contracts = contractRepository.findByUser_Id(targetUserId, pageable);
        }

        return contracts.stream()
                .map(contractMapper::toContractResponse).toList();
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ContractResponse cancelContract(String contractId) {
        Contract contract = contractRepository.findById(contractId)
                .orElseThrow(()->new AppException(ErrorCode.CONTRACT_NOT_FOUND));

        contract.setContractStatus(ContractStatus.CANCELLED);
        contractRepository.save(contract);

        return contractMapper.toContractResponse(contract);
    }


    private String contractCodeGenerate(String policyCode){
        long count = contractRepository.count() + 1;

        return "CTR-" + policyCode + "-" + String.format("%03d", count);
    }


    private User getCurrentUser() {
        var context = SecurityContextHolder.getContext();

        String username = context.getAuthentication().getName();

        return userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTS));
    }
}
