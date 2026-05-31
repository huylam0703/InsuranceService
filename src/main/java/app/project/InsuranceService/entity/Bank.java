package app.project.InsuranceService.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "tbl_banks")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Bank {
    @Id
    Integer id;

    @Column(unique = true, nullable = false)
    String code;

    @Column(nullable = false)
    String shortName;

    @Column(nullable = false)
    String name;

    @Column(unique = true)
    String bin;
}
