package app.project.InsuranceService.controller;

import app.project.InsuranceService.dto.request.VNPay.PaymentRequest;
import app.project.InsuranceService.dto.response.Auth.ApiResponse;
import app.project.InsuranceService.dto.response.VNPay.PaymentResponse;
import app.project.InsuranceService.dto.response.VNPay.VNPayResponse;
import app.project.InsuranceService.service.Payment.PaymentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/payment")
public class VNPayController {
    PaymentService paymentService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<VNPayResponse>> createPayment(@Valid @RequestBody PaymentRequest request,
                                                                    HttpServletRequest httpRequest) throws UnsupportedEncodingException {
        log.info("createPayment");

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<VNPayResponse>builder()
                        .code(1000)
                        .message("create payment success")
                        .result(paymentService.createPayment(request, httpRequest))
                        .build());
    }

    @GetMapping("/vnpay-return")
    public ResponseEntity<ApiResponse<PaymentResponse>> vnpayReturn(HttpServletRequest request) {

        Map<String, String> fields = new HashMap<>();

        request.getParameterMap().forEach((key, value) -> {
            fields.put(key, value[0]);
        });

        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<PaymentResponse>builder()
                        .code(1000)
                        .message("vnpay return success")
                        .result(paymentService.handleVNPayReturn(fields))
                        .build());
    }

}
