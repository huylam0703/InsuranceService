package app.project.InsuranceService.controller;

import app.project.InsuranceService.dto.request.ClaimDocument.ClaimDocumentRequest;
import app.project.InsuranceService.dto.response.Auth.ApiResponse;
import app.project.InsuranceService.dto.response.ClaimDocument.ClaimDocumentResponse;
import app.project.InsuranceService.service.ClaimDocument.ClaimDocumentService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/claim-document")
public class ClaimDocumentController {
    ClaimDocumentService claimDocumentService;
    ObjectMapper objectMapper;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<List<ClaimDocumentResponse>>>  addClaimDocument(@RequestPart("request") String requestJson,
                                                                                    @RequestPart("files") List<MultipartFile> files)
            throws JsonProcessingException {

        ClaimDocumentRequest request = objectMapper.readValue(requestJson, ClaimDocumentRequest.class);

        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<ClaimDocumentResponse>>builder()
                        .code(1000)
                        .message("Upload claim documents success")
                        .result(claimDocumentService.addClaimDocument(request, files))
                        .build());
    }

    @GetMapping("/{claimId}")
    public ResponseEntity<ApiResponse<List<ClaimDocumentResponse>>> getClaimDocumentByClaimId(@PathVariable String claimId){
        log.info("get claim document of claim id {}", claimId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<ClaimDocumentResponse>>builder()
                        .code(1000)
                        .message("Get claim document of claim id " + claimId)
                        .result(claimDocumentService.getClaimDocumentsByClaimId(claimId))
                        .build());
    }

    @DeleteMapping("/{claimDocumentId}")
    public ResponseEntity<ApiResponse<Void>> deleteClaimDocumentByClaimDocsId(@PathVariable String claimDocumentId){
        log.info("delete claim document of claim document id {}", claimDocumentId);

        claimDocumentService.deleteClaimDocumentByClaimDocId(claimDocumentId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<Void>builder()
                        .code(1000)
                        .message("Delete claim document of claim document id " + claimDocumentId)
                        .build());
    }
}
