package app.project.InsuranceService.dto.request.VNPay;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaymentRequest {

    @NotBlank(message = "Course ID không được để trống")
    String contractId;

    String orderInfo;
    String bankCode; // Mã ngân hàng (optional)
}