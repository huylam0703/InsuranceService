package app.project.InsuranceService.service.ClaimDocument.Impl;

import app.project.InsuranceService.dto.request.ClaimDocument.ClaimDocumentRequest;
import app.project.InsuranceService.dto.response.ClaimDocument.ClaimDocumentResponse;
import app.project.InsuranceService.entity.Claim;
import app.project.InsuranceService.entity.ClaimDocument;
import app.project.InsuranceService.entity.User;
import app.project.InsuranceService.enums.ClaimDocumentType;
import app.project.InsuranceService.enums.ClaimStatus;
import app.project.InsuranceService.exception.AppException;
import app.project.InsuranceService.exception.ErrorCode;
import app.project.InsuranceService.mapper.ClaimDocumentMapper;
import app.project.InsuranceService.repository.ClaimDocumentRepository;
import app.project.InsuranceService.repository.ClaimRepository;
import app.project.InsuranceService.repository.UserRepository;
import app.project.InsuranceService.service.ClaimDocument.ClaimDocumentService;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ClaimDocumentServiceImpl implements ClaimDocumentService {
    ClaimDocumentRepository claimDocumentRepository;
    ClaimDocumentMapper claimDocumentMapper;
    ClaimRepository claimRepository;
    Cloudinary cloudinary;
    UserRepository userRepository;


    @Override
    @PreAuthorize("isAuthenticated()")
    public List<ClaimDocumentResponse> addClaimDocument(ClaimDocumentRequest request, List<MultipartFile> files) {
        Claim claim = claimRepository.findById(request.getClaimId())
                .orElseThrow(()-> new AppException(ErrorCode.CLAIM_NOT_FOUND));

        User currentUser = getCurrentUser();

        if (claim.getStatus() == ClaimStatus.PAID ||
                claim.getStatus() == ClaimStatus.REJECTED) {
            throw new AppException(ErrorCode.CLAIM_CLOSED);
        }

        return files.stream()
                .map(file -> uploadSingleFile(claim, currentUser, file))
                .toList();
    }

    @Override
    public List<ClaimDocumentResponse> getClaimDocumentsByClaimId(String claimId) {
        Claim claim = claimRepository.findById(claimId)
                .orElseThrow(()-> new AppException(ErrorCode.CLAIM_NOT_FOUND));

        User currentUser = getCurrentUser();

        boolean isOwner = claim.getCustomer()
                .getId()
                .equals(currentUser.getId());

        boolean isAdmin = SecurityContextHolder.getContext()
                .getAuthentication()
                .getAuthorities()
                .stream()
                .anyMatch(authority ->
                        authority.getAuthority().equals("ROLE_ADMIN"));

        if (!isOwner && !isAdmin) {
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }

        return claimDocumentRepository.findByClaim(claim)
                .stream()
                .map(claimDocumentMapper::toClaimDocumentResponse)
                .toList();
    }

    @Override
    @PreAuthorize("isAuthenticated()")
    public void deleteClaimDocumentByClaimDocId(String claimDocumentId) {
        claimDocumentRepository.deleteById(claimDocumentId);
    }

    private ClaimDocumentResponse uploadSingleFile(Claim claim, User currentUser, MultipartFile file) {
        try {
            File fileUpload = convert(file);

            Map uploadResult = cloudinary.uploader().upload(
                    fileUpload,
                    ObjectUtils.asMap("resource_type", "auto")
            );

            String fileUrl = uploadResult.get("secure_url").toString();

            ClaimDocument document = ClaimDocument.builder()
                    .claim(claim)
                    .fileName(file.getOriginalFilename())
                    .fileUrl(fileUrl)
                    .fileType(resolveFileType(file.getContentType()))
                    .mimeType(file.getContentType())
                    .fileSize(file.getSize())
                    .uploadedBy(currentUser)
                    .uploadedAt(LocalDateTime.now())
                    .build();

            ClaimDocument savedDocument = claimDocumentRepository.save(document);

            cleanDisk(fileUpload);

            return claimDocumentMapper.toClaimDocumentResponse(savedDocument);

        } catch (IOException e) {
            throw new AppException(ErrorCode.FILE_UPLOAD_FAILED);
        }
    }
    private ClaimDocumentType resolveFileType(String mimeType) {
        if (mimeType != null && mimeType.startsWith("image/")) {
            return ClaimDocumentType.IMAGE;
        }

        if ("application/pdf".equals(mimeType)) {
            return ClaimDocumentType.PDF;
        }

        throw new AppException(ErrorCode.INVALID_FILE_TYPE);
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



    private User getCurrentUser() {
        var context = SecurityContextHolder.getContext();

        String username = context.getAuthentication().getName();

        return userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTS));
    }
}
