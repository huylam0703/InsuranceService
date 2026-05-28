package app.project.InsuranceService.service.Payment;

import app.project.InsuranceService.dto.request.VNPay.PaymentRequest;
import app.project.InsuranceService.dto.response.VNPay.PaymentResponse;
import app.project.InsuranceService.dto.response.VNPay.VNPayResponse;
import jakarta.servlet.http.HttpServletRequest;

import java.io.UnsupportedEncodingException;
import java.util.Map;

public interface PaymentService {

    VNPayResponse createPayment(PaymentRequest request, HttpServletRequest httpRequest) throws UnsupportedEncodingException;

    PaymentResponse handleVNPayReturn(Map<String, String> params);

}
