package app.project.InsuranceService.dto.response.ContractDetails;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TravelContractDetailResponse {
    String id;

    String contractId;

    String destinationCountry;

    LocalDate departureDate;

    LocalDate returnDate;

    String passportNumber;

    String travelPurpose;

    String emergencyContactName;

    String emergencyContactPhone;
}
