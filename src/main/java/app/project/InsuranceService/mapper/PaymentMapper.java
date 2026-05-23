package app.project.InsuranceService.mapper;

import app.project.InsuranceService.dto.response.VNPay.PaymentResponse;
import app.project.InsuranceService.entity.Payment;
import app.project.InsuranceService.enums.PaymentStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

    @Mapping(source = "contract.contractId", target = "contractId")
    @Mapping(source = "contract.contractCode", target = "contractCode")
    @Mapping(source = "id", target = "paymentId")
    PaymentResponse toPaymentResponse(Payment payment);

    @Named("statusToDisplay")
    default String statusToDisplay(PaymentStatus status) {
        return status != null ? status.getDisplayName() : null;
    }
}