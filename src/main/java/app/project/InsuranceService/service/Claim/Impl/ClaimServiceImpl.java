package app.project.InsuranceService.service.Claim.Impl;

import app.project.InsuranceService.dto.request.Claim.ClaimCreationRequest;
import app.project.InsuranceService.dto.request.Claim.ClaimAdminUpdateRequest;
import app.project.InsuranceService.dto.request.Claim.ClaimUserUpdateRequest;
import app.project.InsuranceService.dto.request.Email.Recipient;
import app.project.InsuranceService.dto.request.Email.SendEmailRequest;
import app.project.InsuranceService.dto.response.Claim.ClaimResponse;
import app.project.InsuranceService.dto.response.ClaimDocument.ClaimDocumentResponse;
import app.project.InsuranceService.dto.response.PageResponse;
import app.project.InsuranceService.entity.Claim;
import app.project.InsuranceService.entity.Contract;
import app.project.InsuranceService.entity.User;
import app.project.InsuranceService.enums.ClaimActionType;
import app.project.InsuranceService.enums.ClaimStatus;
import app.project.InsuranceService.enums.ContractStatus;
import app.project.InsuranceService.exception.AppException;
import app.project.InsuranceService.exception.ErrorCode;
import app.project.InsuranceService.mapper.ClaimDocumentMapper;
import app.project.InsuranceService.mapper.ClaimMapper;
import app.project.InsuranceService.repository.ClaimDocumentRepository;
import app.project.InsuranceService.repository.ClaimRepository;
import app.project.InsuranceService.repository.ContractRepository;
import app.project.InsuranceService.repository.UserRepository;
import app.project.InsuranceService.service.Claim.ClaimService;
import app.project.InsuranceService.service.ClaimReview.ClaimReviewService;
import app.project.InsuranceService.service.Email.EmailAsyncService;
import app.project.InsuranceService.service.Email.EmailService;
import app.project.InsuranceService.service.Email.EmailTemplateService;
import jakarta.transaction.Transactional;
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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ClaimServiceImpl implements ClaimService {
    ClaimMapper claimMapper;
    ClaimRepository claimRepository;
    ContractRepository contractRepository;
    UserRepository userRepository;
    ClaimDocumentRepository claimDocumentRepository;
    ClaimDocumentMapper claimDocumentMapper;
    ClaimReviewService claimReviewService;
     EmailAsyncService emailAsyncService;
    EmailTemplateService emailTemplateService;


    @Override
    @PreAuthorize("isAuthenticated()")
    public ClaimResponse addClaim(ClaimCreationRequest claim) {

        Contract contract = contractRepository.findById(claim.getContractId())
                .orElseThrow(()-> new AppException(ErrorCode.CONTRACT_NOT_FOUND));

        String currentId = getCurrentUser().getId();
        if(!contract.getUser().getId().equals(currentId)) {
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }

        if (contract.getContractStatus() != ContractStatus.ACTIVE) {
            throw new AppException(ErrorCode.CONTRACT_NOT_ACTIVE);
        }

        if(claim.getClaimAmount().compareTo(contract.getRemainingCoverage()) > 0){
            throw new AppException(ErrorCode.CLAIM_AMOUNT_EXCEED_REMAINING_COVERAGE);
        }
        if (claim.getIncidentDate().isAfter(LocalDate.now())) {
            throw new AppException(ErrorCode.INVALID_INCIDENT_DATE);
        }

        User customer = getCurrentUser();

        BigDecimal fraudScore = calculateFraudScore(contract, customer, claim);

        boolean fraudFlag = fraudScore.compareTo(BigDecimal.valueOf(70)) >= 0;

        Claim claim1 = Claim.builder()
                .claimCode(generateClaimCode())
                .contract(contract)
                .customer(customer)
                .title(claim.getTitle())
                .description(claim.getDescription())
                .incidentDate(claim.getIncidentDate())
                .claimAmount(claim.getClaimAmount())
                .status(ClaimStatus.SUBMITTED)
                .fraudScore(fraudScore)
                .fraudFlag(fraudFlag)
                .submittedAt(LocalDateTime.now())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        Claim savedClaim = claimRepository.save(claim1);

        return claimMapper.toClaimResponse(savedClaim);
    }

    @Override
    public ClaimResponse getDetailClaim(String claimId) {
        User userCurrent = getCurrentUser();

        Claim claim = claimRepository.findById(claimId)
                .orElseThrow(()-> new AppException(ErrorCode.CLAIM_NOT_FOUND));

        boolean isOwner = claim.getCustomer()
                .getId()
                .equals(userCurrent.getId());

        boolean isAdmin = SecurityContextHolder.getContext()
                .getAuthentication().getAuthorities()
                .stream().anyMatch(authority
                        -> authority.getAuthority().equals("ROLE_ADMIN"));
        if(!isOwner && !isAdmin){
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }

        ClaimResponse claimResponse = claimMapper.toClaimResponse(claim);

        claimResponse.setDocuments(
                claimDocumentRepository.findByClaim(claim)
                        .stream()
                        .map(claimDocumentMapper::toClaimDocumentResponse)
                        .toList()
        );

        return claimResponse;
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public PageResponse<ClaimResponse> getAllClaimsCustomer(int pageNo, int pageSize, ClaimStatus status, String userId) {
        if(pageNo > 0){
            pageNo = pageNo - 1;
        }

        Pageable pageable = PageRequest.of(pageNo, pageSize);

        Page<Claim> claims;

        if(status != null) {
            claims = claimRepository.findByCustomer_IdAndStatus(userId, status, pageable);
        }
        else {
            claims = claimRepository.findByCustomer_Id(userId, pageable);
        }

        List<ClaimResponse> claimResponses = claims.getContent()
                .stream()
                .map(claimMapper::toClaimResponse)
                .toList();

        return PageResponse.<ClaimResponse>builder()
                .content(claimResponses)
                .pageNo(pageNo)
                .pageSize(pageSize)
                .totalElements(claims.getTotalElements())
                .totalPages(claims.getTotalPages())
                .last(claims.isLast())
                .build();
    }

    @Override
    @PreAuthorize("hasRole('USER')")
    public PageResponse<ClaimResponse> getAllMyClaims(int pageNo, int pageSize, ClaimStatus status) {
        User userCurrent = getCurrentUser();

        String userId = userCurrent.getId();

        if(pageNo > 0){
            pageNo = pageNo - 1;
        }

        Pageable pageable = PageRequest.of(pageNo, pageSize);

        Page<Claim> claims;

        if(status != null) {
            claims = claimRepository.findByCustomer_IdAndStatus(userId, status, pageable);
        }
        else {
            claims = claimRepository.findByCustomer_Id(userId, pageable);
        }

        List<ClaimResponse> claimResponses = claims.getContent()
                .stream()
                .map(claimMapper::toClaimResponse)
                .toList();

        return PageResponse.<ClaimResponse>builder()
                .content(claimResponses)
                .pageNo(pageNo)
                .pageSize(pageSize)
                .totalElements(claims.getTotalElements())
                .totalPages(claims.getTotalPages())
                .last(claims.isLast())
                .build();
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public PageResponse<ClaimResponse> getAllClaim(int pageNo, int pageSize, ClaimStatus status) {
        if(pageNo > 0){
            pageNo = pageNo - 1;
        }

        Pageable pageable = PageRequest.of(pageNo, pageSize);

        Page<Claim> claims;

        if(status != null) {
            claims = claimRepository.findByStatus(status, pageable);
        }
        else {
            claims = claimRepository.findAll(pageable);
        }

        List<ClaimResponse> claimResponses = claims.getContent()
                .stream()
                .map(claimMapper::toClaimResponse)
                .toList();

        return PageResponse.<ClaimResponse>builder()
                .content(claimResponses)
                .pageNo(pageNo)
                .pageSize(pageSize)
                .totalElements(claims.getTotalElements())
                .totalPages(claims.getTotalPages())
                .last(claims.isLast())
                .build();
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ClaimResponse adminReviewClaim(String claimId) {
        Claim claim = claimRepository.findById(claimId)
                .orElseThrow(()-> new AppException(ErrorCode.CLAIM_NOT_FOUND));

        ClaimStatus previousStatus = claim.getStatus();

        claim.setStatus(ClaimStatus.UNDER_REVIEW);
        claim.setUpdatedAt(LocalDateTime.now());

        Claim claim1 = claimRepository.save(claim);

        claimReviewService.saveClaimReview(
                claim1,
                previousStatus,
                ClaimStatus.UNDER_REVIEW,
                ClaimActionType.START_REVIEW,
                "Admin started reviewing claim"
        );

        return claimMapper.toClaimResponse(claim1);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ClaimResponse adminNeedInfoClaim(String claimId) {
        Claim claim = claimRepository.findById(claimId)
                .orElseThrow(()-> new AppException(ErrorCode.CLAIM_NOT_FOUND));

        ClaimStatus previousStatus = claim.getStatus();

        claim.setStatus(ClaimStatus.NEED_MORE_INFO);
        claim.setUpdatedAt(LocalDateTime.now());

        Claim claim1 = claimRepository.save(claim);

        claimReviewService.saveClaimReview(
                claim1,
                previousStatus,
                ClaimStatus.NEED_MORE_INFO,
                ClaimActionType.REQUEST_MORE_INFO,
                "Admin request more info claim"
        );

        return claimMapper.toClaimResponse(claim1);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ClaimResponse adminApprovedClaim(ClaimAdminUpdateRequest request, String claimId) {
        Claim claim = claimRepository.findById(claimId)
                .orElseThrow(()-> new AppException(ErrorCode.CLAIM_NOT_FOUND));

        if (request.getApprovedAmount() == null
                || request.getApprovedAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new AppException(ErrorCode.APPROVED_AMOUNT_INVALID);
        }

        if (request.getApprovedAmount().compareTo(claim.getContract().getRemainingCoverage()) > 0) {
            throw new AppException(ErrorCode.APPROVED_AMOUNT_EXCEED_REMAINING_COVERAGE);
        }

        ClaimStatus previousStatus = claim.getStatus();

        claim.setStatus(ClaimStatus.APPROVED);
        claim.setApprovedAmount(request.getApprovedAmount());
        claim.setUpdatedAt(LocalDateTime.now());

        Claim claim1 = claimRepository.save(claim);

        claimReviewService.saveClaimReview(
                claim1,
                previousStatus,
                ClaimStatus.APPROVED,
                ClaimActionType.APPROVED,
                "admin approved claim"
        );

        return claimMapper.toClaimResponse(claim1);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ClaimResponse adminRejectedClaim(ClaimAdminUpdateRequest request, String claimId) {
        Claim claim = claimRepository.findById(claimId)
                .orElseThrow(()-> new AppException(ErrorCode.CLAIM_NOT_FOUND));

        ClaimStatus previousStatus = claim.getStatus();

        claim.setStatus(ClaimStatus.REJECTED);
        claim.setRejectionReason(request.getRejectionReason());
        claim.setUpdatedAt(LocalDateTime.now());
        claim.setClosedAt(LocalDateTime.now());

        Claim savedClaim = claimRepository.save(claim);

        claimReviewService.saveClaimReview(
                savedClaim,
                previousStatus,
                ClaimStatus.REJECTED,
                ClaimActionType.REJECTED,
                "admin rejected claim"
        );

        return claimMapper.toClaimResponse(claim);
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public ClaimResponse adminPaidClaim(String claimId) {
        Claim claim = claimRepository.findById(claimId)
                .orElseThrow(()-> new AppException(ErrorCode.CLAIM_NOT_FOUND));

        if (claim.getStatus() != ClaimStatus.APPROVED) {
            throw new AppException(ErrorCode.CLAIM_MUST_BE_APPROVED_BEFORE_PAID);
        }

        if (claim.getApprovedAmount() == null
                || claim.getApprovedAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new AppException(ErrorCode.APPROVED_AMOUNT_INVALID);
        }

        ClaimStatus previousStatus = claim.getStatus();

        claim.setStatus(ClaimStatus.PAID);
        claim.setUpdatedAt(LocalDateTime.now());
        claim.setClosedAt(LocalDateTime.now());

        Contract contract = claim.getContract();
        if(claim.getApprovedAmount().compareTo(contract.getRemainingCoverage()) > 0){
            throw new AppException(ErrorCode.APPROVED_AMOUNT_EXCEED_REMAINING_COVERAGE);
        }

        Claim savedClaim = claimRepository.save(claim);

        contract.setRemainingCoverage(contract.getRemainingCoverage().subtract(claim.getApprovedAmount()));

        claimReviewService.saveClaimReview(
                savedClaim,
                previousStatus,
                ClaimStatus.PAID,
                ClaimActionType.PAID,
                "admin paid claim"
        );
        String htmlContent = emailTemplateService.buildClaimPaidEmail(
                claim.getCustomer().getUsername(),
                claim.getClaimCode(),
                claim.getApprovedAmount()
        );

        SendEmailRequest emailRequest  = SendEmailRequest.builder()
                .to(Recipient.builder()
                        .email(claim.getCustomer().getEmail())
                        .name(claim.getCustomer().getUsername())
                        .build())
                .subject("Thông báo thanh toán bồi thường")
                .htmlContent(htmlContent)
                .build();

        emailAsyncService.sendEmailAsync(emailRequest);

        return claimMapper.toClaimResponse(claim);
    }

    @Override
    @PreAuthorize("isAuthenticated()")
    public ClaimResponse userUpdateClaim(ClaimUserUpdateRequest request, String claimId) {

        Claim claim = claimRepository.findById(claimId)
                .orElseThrow(()-> new AppException(ErrorCode.CLAIM_NOT_FOUND));

        if (claim.getStatus() != ClaimStatus.SUBMITTED
                && claim.getStatus() != ClaimStatus.NEED_MORE_INFO) {
            throw new AppException(ErrorCode.CLAIM_CLOSED);
        }

        User user = getCurrentUser();

        if(!claim.getCustomer().getId().equals(user.getId())) {
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }

        claimMapper.userUpdateClaim(request, claim);

        Claim savedClaim = claimRepository.save(claim);

        return claimMapper.toClaimResponse(savedClaim);
    }


    private BigDecimal calculateFraudScore(Contract contract, User customer, ClaimCreationRequest request) {
        int score = 0;

        BigDecimal remainingCoverage = contract.getRemainingCoverage();
        BigDecimal claimAmount = request.getClaimAmount();

        //TH1: claimAmount > 80% remainingCoverage
        BigDecimal threshold80 = remainingCoverage.multiply(BigDecimal.valueOf(0.8));
        if(claimAmount.compareTo(threshold80) > 0) {
            score += 30;
        }

        //TH2: incident date quá cũ > 90 date
        if(request.getIncidentDate().isBefore(LocalDate.now().minusDays(90))){
            score += 20;
        }

        //TH3: claim amount lớn hơn 50 tr
        if(claimAmount.compareTo(BigDecimal.valueOf(50_000_000)) > 0){
            score += 20;
        }

        //TH4: some claim in 30 date
        LocalDateTime fromData = LocalDateTime.now().minusDays(30);
        long recentClaimCount = claimRepository.countByCustomerAndCreatedAtAfter(customer, fromData);

        if(recentClaimCount >= 3){
            score += 30;
        }

        return BigDecimal.valueOf(score);

    }

    private User getCurrentUser() {
        var context = SecurityContextHolder.getContext();

        String username = context.getAuthentication().getName();

        return userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTS));
    }

    private String generateClaimCode() {
        return "CLM-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}
