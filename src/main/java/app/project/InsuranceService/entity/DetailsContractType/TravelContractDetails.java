package app.project.InsuranceService.entity.DetailsContractType;

import app.project.InsuranceService.entity.Contract;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Entity
@Table(name = "tbl_travel_contract_details")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TravelContractDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contract_id", nullable = false, unique = true)
    Contract contract;

    String destinationCountry;

    LocalDate departureDate;

    LocalDate returnDate;

    String passportNumber;

    String travelPurpose;

    String emergencyContactName;

    String emergencyContactPhone;
}
