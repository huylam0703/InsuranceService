package app.project.InsuranceService.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "tbl_user_bank_accounts")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserBankAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @OneToOne(fetch = FetchType.LAZY)
    User user;

    @ManyToOne(fetch = FetchType.LAZY)
    Bank bank;

    String accountNumber;

    String accountHolderName;
}
