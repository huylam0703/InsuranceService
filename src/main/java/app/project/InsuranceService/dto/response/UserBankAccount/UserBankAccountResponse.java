package app.project.InsuranceService.dto.response.UserBankAccount;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserBankAccountResponse {
    String id;

    String userId;

    String bankCode;

    String bankName;

    String bankShortName;

    String accountNumber;

    String accountHolderName;
}
