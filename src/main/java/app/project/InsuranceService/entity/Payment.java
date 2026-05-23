package app.project.InsuranceService.entity;

import app.project.InsuranceService.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_payments")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    String paymentCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contract_id", nullable = false)
    Contract contract;

    @Column(precision = 15, scale = 2)
    BigDecimal amount;

    @Enumerated(EnumType.STRING)
    PaymentStatus paymentStatus;

    String transactionReference;

    @Column(columnDefinition = "TEXT")
    String gatewayResponse;

    String transactionNo; // Mã giao dịch từ VNPay
    String bankCode;
    String cardType;

    @Column(columnDefinition = "TEXT")
    String orderInfo;
    String vnpayTransactionNo; // Mã giao dịch VNPay
    String vnpayResponseCode;

    LocalDateTime createdAt;

    LocalDateTime paidAt;
}
