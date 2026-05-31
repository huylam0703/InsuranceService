package app.project.InsuranceService.dto.request.UserBankAccount;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserBankAccountRequest {
    @NotBlank(message = "BANK_CODE_REQUIRED")
    String bankCode;

    @NotBlank(message = "ACCOUNT_NUMBER_REQUIRED")
    String accountNumber;

    @NotBlank(message = "ACCOUNT_HOLDER_NAME_REQUIRED")
    String accountHolderName;
}
