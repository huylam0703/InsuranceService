package app.project.InsuranceService.service.ClaimDocument;

import app.project.InsuranceService.dto.request.ClaimDocument.ClaimDocumentRequest;
import app.project.InsuranceService.dto.response.ClaimDocument.ClaimDocumentResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ClaimDocumentService {

    List<ClaimDocumentResponse> addClaimDocument(ClaimDocumentRequest request, List<MultipartFile> files);

    List<ClaimDocumentResponse> getClaimDocumentsByClaimId(String claimId);

    void deleteClaimDocumentByClaimDocId(String claimDocumentId);


}
