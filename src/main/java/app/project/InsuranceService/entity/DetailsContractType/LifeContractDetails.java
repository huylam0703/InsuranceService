package app.project.InsuranceService.entity.DetailsContractType;

import app.project.InsuranceService.entity.Contract;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Entity
@Table(name = "tbl_life_contract_details")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LifeContractDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contract_id", nullable = false, unique = true)
    Contract contract;

    String occupation;

    BigDecimal annualIncome;

    String beneficiaryName;

    String beneficiaryRelationship;

    String beneficiaryPhone;

    @Column(columnDefinition = "TEXT")
    String medicalHistory;

    Boolean smoking;
}
