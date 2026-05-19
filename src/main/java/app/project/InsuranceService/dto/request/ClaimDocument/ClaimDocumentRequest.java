package app.project.InsuranceService.dto.request.ClaimDocument;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ClaimDocumentRequest {
    String claimId;
}
