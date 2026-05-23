package app.project.InsuranceService.service.Contract.Impl;

import app.project.InsuranceService.dto.request.Contract.ContractCreationRequest;
import app.project.InsuranceService.dto.response.Contract.ContractResponse;
import app.project.InsuranceService.dto.response.ContractDetails.HealthContractDetailResponse;
import app.project.InsuranceService.dto.response.ContractDetails.LifeContractDetailResponse;
import app.project.InsuranceService.dto.response.ContractDetails.TravelContractDetailResponse;
import app.project.InsuranceService.dto.response.ContractDetails.VehicleContractDetailResponse;
import app.project.InsuranceService.entity.Contract;
import app.project.InsuranceService.entity.DetailsContractType.HealthContractDetails;
import app.project.InsuranceService.entity.DetailsContractType.LifeContractDetails;
import app.project.InsuranceService.entity.DetailsContractType.TravelContractDetails;
import app.project.InsuranceService.entity.DetailsContractType.VehicleContractDetails;
import app.project.InsuranceService.entity.Policy;
import app.project.InsuranceService.entity.User;
import app.project.InsuranceService.enums.ContractPaymentStatus;
import app.project.InsuranceService.enums.ContractStatus;
import app.project.InsuranceService.exception.AppException;
import app.project.InsuranceService.exception.ErrorCode;
import app.project.InsuranceService.mapper.ContractDetailsMapper;
import app.project.InsuranceService.mapper.ContractMapper;
import app.project.InsuranceService.repository.ContractDetails.HealthRepository;
import app.project.InsuranceService.repository.ContractDetails.LifeRepository;
import app.project.InsuranceService.repository.ContractDetails.TravelRepository;
import app.project.InsuranceService.repository.ContractDetails.VehicleRepository;
import app.project.InsuranceService.repository.ContractRepository;
import app.project.InsuranceService.repository.PolicyRepository;
import app.project.InsuranceService.repository.UserRepository;
import app.project.InsuranceService.service.Contract.ContractService;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ContractServiceImpl implements ContractService {
    ContractRepository contractRepository;
    ContractMapper contractMapper;
    UserRepository userRepository;
    PolicyRepository policyRepository;

    ContractDetailsMapper contractDetailsMapper;
    HealthRepository healthRepository;
    LifeRepository lifeRepository;
    VehicleRepository vehicleRepository;
    TravelRepository travelRepository;

    final Cloudinary cloudinary;

    @Override
    @PreAuthorize("hasRole('USER')")
    public ContractResponse purchaseContract(ContractCreationRequest request,
                                             MultipartFile vehicleImage) throws IOException {
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
                .paymentStatus(ContractPaymentStatus.UNPAID)
                .build();
        Contract savedContract = contractRepository.save(contract);

        ContractResponse response = contractMapper.toContractResponse(savedContract);

        switch (policy.getPolicyType()) {
            case HEALTH -> response.setDetail(createHealthDetail(savedContract, request));
            case LIFE -> response.setDetail(createLifeDetail(savedContract, request));
            case VEHICLE -> response.setDetail(createVehicleDetail(savedContract, request, vehicleImage));
            case TRAVEL -> response.setDetail(createTravelDetail(savedContract, request));
        }

        return response;
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

        ContractResponse response = contractMapper.toContractResponse(contract);

        switch (contract.getPolicy().getPolicyType()) {
            case HEALTH -> response.setDetail(
                    contractDetailsMapper.toHealthResponse(
                            healthRepository.findByContract(contract)
                                    .orElseThrow(() -> new AppException(ErrorCode.HEALTH_DETAIL_NOT_FOUND))
                    )
            );

            case LIFE -> response.setDetail(
                    contractDetailsMapper.toLifeResponse(
                            lifeRepository.findByContract(contract)
                                    .orElseThrow(() -> new AppException(ErrorCode.LIFE_DETAIL_NOT_FOUND))
                    )
            );

            case VEHICLE -> response.setDetail(
                    contractDetailsMapper.toVehicleResponse(
                            vehicleRepository.findByContract(contract)
                                    .orElseThrow(() -> new AppException(ErrorCode.VEHICLE_DETAIL_NOT_FOUND))
                    )
            );

            case TRAVEL -> response.setDetail(
                    contractDetailsMapper.toTravelResponse(
                            travelRepository.findByContract(contract)
                                    .orElseThrow(() -> new AppException(ErrorCode.TRAVEL_DETAIL_NOT_FOUND))
                    )
            );
        }

        return response;
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

    private HealthContractDetailResponse createHealthDetail(
            Contract contract,
            ContractCreationRequest request
    ) {
        if (request.getHealthDetail() == null) {
            throw new AppException(ErrorCode.HEALTH_DETAIL_REQUIRED);
        }

        HealthContractDetails detail = HealthContractDetails.builder()
                .contract(contract)
                .heightCm(request.getHealthDetail().getHeightCm())
                .weightKg(request.getHealthDetail().getWeightKg())
                .bloodType(request.getHealthDetail().getBloodType())
                .medicalHistory(request.getHealthDetail().getMedicalHistory())
                .allergies(request.getHealthDetail().getAllergies())
                .smoking(request.getHealthDetail().getSmoking())
                .alcoholUse(request.getHealthDetail().getAlcoholUse())
                .build();

        HealthContractDetails savedDetail =
                healthRepository.save(detail);

        return contractDetailsMapper.toHealthResponse(savedDetail);
    }

    private LifeContractDetailResponse createLifeDetail(
            Contract contract,
            ContractCreationRequest request
    ) {
        if (request.getLifeDetail() == null) {
            throw new AppException(ErrorCode.LIFE_DETAIL_REQUIRED);
        }

        LifeContractDetails detail = LifeContractDetails.builder()
                .contract(contract)
                .occupation(request.getLifeDetail().getOccupation())
                .annualIncome(request.getLifeDetail().getAnnualIncome())
                .beneficiaryName(request.getLifeDetail().getBeneficiaryName())
                .beneficiaryRelationship(request.getLifeDetail().getBeneficiaryRelationship())
                .beneficiaryPhone(request.getLifeDetail().getBeneficiaryPhone())
                .medicalHistory(request.getLifeDetail().getMedicalHistory())
                .smoking(request.getLifeDetail().getSmoking())
                .build();

        LifeContractDetails savedDetail =
                lifeRepository.save(detail);

        return contractDetailsMapper.toLifeResponse(savedDetail);
    }

    private VehicleContractDetailResponse createVehicleDetail(
            Contract contract,
            ContractCreationRequest request,
            MultipartFile vehicleImage
    ) throws IOException {
        if (request.getVehicleDetail() == null) {
            throw new AppException(ErrorCode.VEHICLE_DETAIL_REQUIRED);
        }

        String publicValue = generatePublicValue(vehicleImage.getOriginalFilename());

        String extension = getFileName(vehicleImage.getOriginalFilename())[1];

        File fileUpload = convert(vehicleImage);

        cloudinary.uploader().upload(fileUpload, ObjectUtils.asMap("public_id", publicValue));

        String filePath = cloudinary.url().generate(StringUtils.join(publicValue, ".", extension));

        VehicleContractDetails detail = VehicleContractDetails.builder()
                .contract(contract)
                .vehicleType(request.getVehicleDetail().getVehicleType())
                .licensePlate(request.getVehicleDetail().getLicensePlate())
                .brand(request.getVehicleDetail().getBrand())
                .model(request.getVehicleDetail().getModel())
                .manufactureYear(request.getVehicleDetail().getManufactureYear())
                .chassisNumber(request.getVehicleDetail().getChassisNumber())
                .engineNumber(request.getVehicleDetail().getEngineNumber())
                .vehicleImageUrl(filePath)
                .build();

        VehicleContractDetails savedDetail =
                vehicleRepository.save(detail);
        cleanDisk(fileUpload);

        return contractDetailsMapper.toVehicleResponse(savedDetail);
    }

    private TravelContractDetailResponse createTravelDetail(
            Contract contract,
            ContractCreationRequest request
    ) {
        if (request.getTravelDetail() == null) {
            throw new AppException(ErrorCode.TRAVEL_DETAIL_REQUIRED);
        }

        TravelContractDetails detail = TravelContractDetails.builder()
                .contract(contract)
                .destinationCountry(request.getTravelDetail().getDestinationCountry())
                .departureDate(request.getTravelDetail().getDepartureDate())
                .returnDate(request.getTravelDetail().getReturnDate())
                .passportNumber(request.getTravelDetail().getPassportNumber())
                .travelPurpose(request.getTravelDetail().getTravelPurpose())
                .emergencyContactName(request.getTravelDetail().getEmergencyContactName())
                .emergencyContactPhone(request.getTravelDetail().getEmergencyContactPhone())
                .build();

        TravelContractDetails savedDetail =
                travelRepository.save(detail);

        return contractDetailsMapper.toTravelResponse(savedDetail);
    }

    private File convert(MultipartFile file) throws IOException {
        assert file.getOriginalFilename() != null;
        File convFile = new File(StringUtils
                .join(generatePublicValue(file.getOriginalFilename()), getFileName(file.getOriginalFilename())[1]));

        try(InputStream is = file.getInputStream()) {
            Files.copy(is, convFile.toPath());
        }
        return convFile;
    }

    private void cleanDisk(File file) {
        try{
            Path filePath = file.toPath();
            Files.delete(filePath);
        }catch (IOException e) {
            log.error("error");
        }
    }

    public String generatePublicValue(String originalName){
        String fileName = getFileName(originalName)[0];
        return StringUtils.join(UUID.randomUUID().toString(), "_", fileName);
    }

    public String[] getFileName(String originalName){
        return originalName.split("\\.");
    }
}
