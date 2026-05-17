package app.project.InsuranceService.service.ContractDetails.Impl;

import app.project.InsuranceService.dto.response.ContractDetails.HealthContractDetailResponse;
import app.project.InsuranceService.dto.response.ContractDetails.LifeContractDetailResponse;
import app.project.InsuranceService.dto.response.ContractDetails.TravelContractDetailResponse;
import app.project.InsuranceService.dto.response.ContractDetails.VehicleContractDetailResponse;
import app.project.InsuranceService.entity.DetailsContractType.HealthContractDetails;
import app.project.InsuranceService.entity.DetailsContractType.LifeContractDetails;
import app.project.InsuranceService.entity.DetailsContractType.TravelContractDetails;
import app.project.InsuranceService.entity.DetailsContractType.VehicleContractDetails;
import app.project.InsuranceService.entity.User;
import app.project.InsuranceService.exception.AppException;
import app.project.InsuranceService.exception.ErrorCode;
import app.project.InsuranceService.mapper.ContractDetailsMapper;
import app.project.InsuranceService.repository.ContractDetails.HealthRepository;
import app.project.InsuranceService.repository.ContractDetails.LifeRepository;
import app.project.InsuranceService.repository.ContractDetails.TravelRepository;
import app.project.InsuranceService.repository.ContractDetails.VehicleRepository;
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
    LifeRepository lifeRepository;
    TravelRepository travelRepository;
    VehicleRepository vehicleRepository;
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

    @Override
    public LifeContractDetailResponse getLifeContractDetail(String id) {
        LifeContractDetails lifeContractDetails = lifeRepository.findById(id)
                .orElseThrow(()-> new AppException(ErrorCode.LIFE_DETAIL_NOT_FOUND));

        User currentUser = getCurrentUser();

        boolean isOwner = lifeContractDetails.getContract()
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

        return contractDetailsMapper.toLifeResponse(lifeContractDetails);
    }

    @Override
    public TravelContractDetailResponse getTravelContractDetail(String id) {
        TravelContractDetails travelContractDetails = travelRepository.findById(id)
                .orElseThrow(()-> new AppException(ErrorCode.TRAVEL_DETAIL_NOT_FOUND));

        User currentUser = getCurrentUser();

        boolean isOwner = travelContractDetails.getContract()
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
        return contractDetailsMapper.toTravelResponse(travelContractDetails);
    }

    @Override
    public VehicleContractDetailResponse getVehicleContractDetail(String id) {
        VehicleContractDetails vehicleContractDetails = vehicleRepository.findById(id)
                .orElseThrow(()-> new AppException(ErrorCode.VEHICLE_DETAIL_NOT_FOUND));

        User currentUser = getCurrentUser();

        boolean isOwner = vehicleContractDetails.getContract()
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
        return contractDetailsMapper.toVehicleResponse(vehicleContractDetails);
    }


    private User getCurrentUser() {
        var context = SecurityContextHolder.getContext();

        String username = context.getAuthentication().getName();

        return userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTS));
    }
}
