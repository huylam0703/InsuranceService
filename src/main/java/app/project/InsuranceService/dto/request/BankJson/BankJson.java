package app.project.InsuranceService.dto.request.BankJson;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BankJson {
    Integer id;

    String shortName;

    String name;

    String bin;

    String code;
}
