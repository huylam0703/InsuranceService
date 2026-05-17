package app.project.InsuranceService.service.ContractDetails.Impl;

import app.project.InsuranceService.dto.response.ContractDetails.HealthContractDetailResponse;
import app.project.InsuranceService.entity.DetailsContractType.HealthContractDetails;
import app.project.InsuranceService.entity.User;
import app.project.InsuranceService.exception.AppException;
import app.project.InsuranceService.exception.ErrorCode;
import app.project.InsuranceService.mapper.ContractDetailsMapper;
import app.project.InsuranceService.repository.ContractDetails.HealthRepository;
import app.project.InsuranceService.repository.UserRepository;
import app.project.InsuranceService.service.ContractDetails.ContractDetailsService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ContractDetailsServiceImpl implements ContractDetailsService {
    HealthRepository healthRepository;
    ContractDetailsMapper contractDetailsMapper;
    UserRepository userRepository;


    @Override
    public HealthContractDetailResponse getHealthContractDetail(String id) {

        HealthContractDetails healthDetail = healthRepository.findById(id)
                .orElseThrow(() ->
                        new AppException(ErrorCode.HEALTH_DETAIL_NOT_FOUND));

        User currentUser = getCurrentUser();

        boolean isOwner = healthDetail.getContract()
                .getUser()
                .getId()
                .equals(currentUser.getId());

        boolean isAdminOrStaffOrManager = SecurityContextHolder.getContext()
                .getAuthentication()
                .getAuthorities()
                .stream()
                .anyMatch(auth ->
                        auth.getAuthority().equals("ROLE_ADMIN")
                );

        if (!isOwner && !isAdminOrStaffOrManager) {
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }

        return contractDetailsMapper.toHealthResponse(healthDetail);
    }

    private User getCurrentUser() {
        var context = SecurityContextHolder.getContext();

        String username = context.getAuthentication().getName();

        return userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTS));
    }
}
