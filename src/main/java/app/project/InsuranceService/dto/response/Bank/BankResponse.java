package app.project.InsuranceService.dto.response.Bank;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BankResponse {
    String code;

    String shortName;

    String name;
}
