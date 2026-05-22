package app.project.InsuranceService.dto.request.ClaimReview;

import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ClaimReviewRequest {
    @Size(max = 2000, message = "COMMENT_TOO_LONG")
    String comment;
}
