package app.project.InsuranceService.dto.request.ContractDetails;

import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TravelContractDetailRequest {

    @NotBlank(message = "DESTINATION_COUNTRY_TRAVEL")
    String destinationCountry;

    @NotNull(message = "DEPARTURE_DATE_TRAVEL")
    @FutureOrPresent(message = "DEPARTURE_DATE_VALID")
    LocalDate departureDate;

    @NotNull(message = "RETURN_DATE_TRAVEL")
    @Future(message = "RETURN_DATE_VALID")
    LocalDate returnDate;

    @NotBlank(message = "PASSPORT_NUMBER")
    @Pattern(
            regexp = "^[A-Z0-9]{6,20}$",
            message = "PASSPORT_NUMBER_VALID"
    )
    String passportNumber;

    @NotBlank(message = "TRAVEL_PURPOSE")
    String travelPurpose;

    @NotBlank(message = "EMERGENCY_CONTACT_NAME")
    String emergencyContactName;

    @NotBlank(message = "EMERGENCY_CONTACT_PHONE")
    @Pattern(
            regexp = "^(0|\\+84)[0-9]{9,10}$",
            message = "EMERGENCY_CONTACT_PHONE_INVALID"
    )
    String emergencyContactPhone;

    @AssertTrue(message = "IS_RETURN_DATE_VALID")
    public boolean isReturnDateValid() {
        if (departureDate == null || returnDate == null) {
            return true;
        }
        return returnDate.isAfter(departureDate);
    }
}
