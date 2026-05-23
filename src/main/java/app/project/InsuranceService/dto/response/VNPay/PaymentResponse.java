package app.project.InsuranceService.dto.response.VNPay;

import app.project.InsuranceService.enums.PaymentStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaymentResponse {
    String paymentId;

    String paymentCode;

    String contractId;

    String contractCode;

    BigDecimal amount;

    PaymentStatus paymentStatus;

    String gatewayResponse;

    String transactionReference;

    LocalDateTime paidAt;

    LocalDateTime createdAt;
}