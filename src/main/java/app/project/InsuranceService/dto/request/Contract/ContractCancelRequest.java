package app.project.InsuranceService.dto.request.Contract;

import app.project.InsuranceService.enums.ContractStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ContractCancelRequest {
    ContractStatus status;
}
