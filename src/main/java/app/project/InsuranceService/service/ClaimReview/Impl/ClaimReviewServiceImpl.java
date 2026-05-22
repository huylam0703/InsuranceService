package app.project.InsuranceService.service.ClaimReview.Impl;

import app.project.InsuranceService.dto.response.ClaimReview.ClaimReviewResponse;
import app.project.InsuranceService.entity.Claim;
import app.project.InsuranceService.entity.ClaimReview;
import app.project.InsuranceService.entity.Contract;
import app.project.InsuranceService.entity.User;
import app.project.InsuranceService.enums.ClaimActionType;
import app.project.InsuranceService.enums.ClaimStatus;
import app.project.InsuranceService.exception.AppException;
import app.project.InsuranceService.exception.ErrorCode;
import app.project.InsuranceService.mapper.ClaimReviewMapper;
import app.project.InsuranceService.repository.ClaimRepository;
import app.project.InsuranceService.repository.ClaimReviewRepository;
import app.project.InsuranceService.repository.UserRepository;
import app.project.InsuranceService.service.ClaimReview.ClaimReviewService;
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

import java.time.LocalDateTime;
import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ClaimReviewServiceImpl implements ClaimReviewService {
    ClaimReviewMapper claimReviewMapper;
    ClaimReviewRepository claimReviewRepository;
    UserRepository userRepository;
    ClaimRepository claimRepository;


    public void saveClaimReview(
            Claim claim,
            ClaimStatus previousStatus,
            ClaimStatus newStatus,
            ClaimActionType actionType,
            String comment
    ) {
        User admin = getCurrentUser();

        ClaimReview claimReview = ClaimReview.builder()
                .claim(claim)
                .reviewer(admin)
                .previousStatus(previousStatus)
                .newStatus(newStatus)
                .actionType(actionType)
                .comment(comment)
                .reviewedAt(LocalDateTime.now())
                .build();

        claimReviewRepository.save(claimReview);
    }

    @Override

    public List<ClaimReviewResponse> getClaimReviewByClaimId(String claimId) {
        Claim claim = claimRepository.findById(claimId)
                .orElseThrow(()-> new AppException(ErrorCode.CLAIM_NOT_FOUND));

        User userCurrent = getCurrentUser();

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

        return claimReviewRepository.findByClaim(claim)
                .stream()
                .map(claimReviewMapper::toClaimReviewResponse).toList();
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public List<ClaimReviewResponse> getAllClaimReview(int pageNo, int pageSize) {
        if(pageNo > 0){
            pageNo = pageNo - 1;
        }

        Pageable pageable = PageRequest.of(pageNo, pageSize);

        Page<ClaimReview> claimReviews;

        claimReviews = claimReviewRepository.findAll(pageable);

        return claimReviews.stream()
                .map(claimReviewMapper::toClaimReviewResponse).toList();
    }

    private User getCurrentUser() {
        var context = SecurityContextHolder.getContext();

        String username = context.getAuthentication().getName();

        return userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTS));
    }
}
