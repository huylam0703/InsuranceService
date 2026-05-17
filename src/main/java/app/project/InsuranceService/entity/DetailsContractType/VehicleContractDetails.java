package app.project.InsuranceService.entity.DetailsContractType;

import app.project.InsuranceService.entity.Contract;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "tbl_vehicle_contract_details")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VehicleContractDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contract_id", nullable = false, unique = true)
    Contract contract;

    String vehicleType;

    String licensePlate;

    String brand;

    String model;

    Integer manufactureYear;

    String chassisNumber;

    String engineNumber;

    String vehicleImageUrl;
}
