package app.project.InsuranceService.dto.response.ClaimDocument;

import app.project.InsuranceService.enums.ClaimDocumentType;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ClaimDocumentResponse {
    String id;

    String claimId;

    String fileName;

    String fileUrl;

    ClaimDocumentType fileType;

    String mimeType;

    Long fileSize;

    String uploadedByUserId;

    String uploadedByUsername;

    LocalDateTime uploadedAt;
}
