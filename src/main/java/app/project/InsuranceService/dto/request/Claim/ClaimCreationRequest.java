package app.project.InsuranceService.dto.request.Claim;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ClaimCreationRequest {

    @NotBlank(message = "CONTRACT_ID_REQUIRED")
    String contractId;

    @NotBlank(message = "TITLE_REQUIRED")
    String title;

    @NotBlank(message = "DESCRIPTION_REQUIRED")
    String description;

    @NotNull(message = "INCIDENT_DATE_REQUIRED")
    @PastOrPresent(message = "INCIDENT_DATE_INVALID")
    LocalDate incidentDate;

    @NotNull(message = "CLAIM_AMOUNT_REQUIRED")
    @DecimalMin(
            value = "0.01",
            inclusive = true,
            message = "CLAIM_AMOUNT_INVALID"
    )
    BigDecimal claimAmount;
}
