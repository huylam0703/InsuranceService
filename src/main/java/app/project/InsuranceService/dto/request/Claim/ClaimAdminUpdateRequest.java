package app.project.InsuranceService.dto.request.Claim;

import app.project.InsuranceService.enums.ClaimStatus;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ClaimAdminUpdateRequest {

    @DecimalMin(
            value = "0.0",
            inclusive = true,
            message = "APPROVED_AMOUNT_INVALID"
    )
    BigDecimal approvedAmount;

    @Size(max = 2000, message = "REJECTION_REASON_TOO_LONG")
    String rejectionReason;
}
