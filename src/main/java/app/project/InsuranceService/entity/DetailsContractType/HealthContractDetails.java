package app.project.InsuranceService.entity.DetailsContractType;

import app.project.InsuranceService.entity.Contract;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "tbl_health_contract_details")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HealthContractDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contract_id", nullable = false, unique = true)
    Contract contract;

    Double heightCm;

    Double weightKg;

    String bloodType;

    String medicalHistory;

    String allergies;

    Boolean smoking;

    Boolean alcoholUse;
}
