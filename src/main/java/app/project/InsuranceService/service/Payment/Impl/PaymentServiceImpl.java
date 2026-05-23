package app.project.InsuranceService.service.Payment.Impl;

import app.project.InsuranceService.Utils.VNPayUtil;
import app.project.InsuranceService.configuration.VNPayConfig;
import app.project.InsuranceService.dto.request.VNPay.PaymentRequest;
import app.project.InsuranceService.dto.response.VNPay.PaymentResponse;
import app.project.InsuranceService.dto.response.VNPay.VNPayResponse;
import app.project.InsuranceService.entity.Contract;
import app.project.InsuranceService.entity.Payment;
import app.project.InsuranceService.entity.User;
import app.project.InsuranceService.enums.ContractPaymentStatus;
import app.project.InsuranceService.enums.ContractStatus;
import app.project.InsuranceService.enums.PaymentStatus;
import app.project.InsuranceService.exception.AppException;
import app.project.InsuranceService.exception.ErrorCode;
import app.project.InsuranceService.mapper.PaymentMapper;
import app.project.InsuranceService.repository.ContractRepository;
import app.project.InsuranceService.repository.PaymentRepository;
import app.project.InsuranceService.repository.UserRepository;
import app.project.InsuranceService.service.Payment.PaymentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;


@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PaymentServiceImpl implements PaymentService {
    PaymentRepository paymentRepository;
    ContractRepository contractRepository;
    PaymentMapper paymentMapper;
    VNPayConfig vnPayConfig;
    UserRepository userRepository;

    @Override
    @Transactional
    @PreAuthorize("isAuthenticated()")
    public VNPayResponse createPayment(PaymentRequest request, HttpServletRequest httpRequest)
            throws UnsupportedEncodingException {

        Contract contract = contractRepository.findById(request.getContractId())
                .orElseThrow(()-> new AppException(ErrorCode.CONTRACT_NOT_FOUND));

        // kiem tra da mua contract chua
        if(paymentRepository.existsByContract_ContractIdAndPaymentStatus(contract.getContractId(), PaymentStatus.SUCCESS)){
            throw new AppException(ErrorCode.CONTRACT_EXISTED);
        }

        //tao ma giao dich
        String txnRef = VNPayUtil.getRandomNumber(8);

        //tao payment record
        Payment payment = Payment.builder()
                .paymentCode(generatePaymentCode())
                .contract(contract)
                .amount(contract.getPremiumAmount())
                .paymentStatus(PaymentStatus.PENDING)
                .transactionReference(txnRef)
                .createdAt(LocalDateTime.now())
                .orderInfo(request.getOrderInfo() != null ?
                        request.getOrderInfo() : "Thanh toan hop dong: " + contract.getContractCode())
                .build();

        paymentRepository.save(payment);

        String paymentUrl = buildVNPayUrl(payment, httpRequest, request.getBankCode());
        return VNPayResponse.builder()
                .paymentUrl(paymentUrl)
                .message("create URL payment success")
                .success(true)
                .build();

    }

    @Override
    @Transactional
    public PaymentResponse handleVNPayReturn(Map<String, String> params) {
        log.info("=== VNPay return Params ===");
        params.forEach((key, value) -> log.info("key={}, value={}", key, value));

        String vnpSecureHash = params.get("vnp_SecureHash");

        if (vnpSecureHash == null) {
            throw new AppException(ErrorCode.INVALID_PAYMENT_SIGNATURE);
        }

        String calculatedHash =
                VNPayUtil.hashAllFields(params, vnPayConfig.getHashSecret());

        log.info("Received hash: {}", vnpSecureHash);
        log.info("Calculated hash: {}", calculatedHash);

        if (!calculatedHash.equalsIgnoreCase(vnpSecureHash)) {
            throw new AppException(ErrorCode.INVALID_PAYMENT_SIGNATURE);
        }

        String txnRef = params.get("vnp_TxnRef");
        String responseCode = params.get("vnp_ResponseCode");

        Payment payment = paymentRepository.findByTransactionReference(txnRef)
                .orElseThrow(() -> new AppException(ErrorCode.PAYMENT_NOT_FOUND));

        payment.setGatewayResponse(params.toString());
        payment.setVnpayResponseCode(responseCode);
        payment.setTransactionNo(params.get("vnp_BankTranNo"));
        payment.setVnpayTransactionNo(params.get("vnp_TransactionNo"));
        payment.setBankCode(params.get("vnp_BankCode"));
        payment.setCardType(params.get("vnp_CardType"));
        payment.setOrderInfo(params.get("vnp_OrderInfo"));

        if ("00".equals(responseCode)) {
            payment.setPaymentStatus(PaymentStatus.SUCCESS);
            payment.setPaidAt(LocalDateTime.now());

            Contract contract = payment.getContract();
            contract.setPaymentStatus(ContractPaymentStatus.PAID);
            contract.setContractStatus(ContractStatus.ACTIVE);

            contractRepository.save(contract);
        } else {
            payment.setPaymentStatus(PaymentStatus.FAILED);
        }

        Payment savedPayment = paymentRepository.save(payment);

        return paymentMapper.toPaymentResponse(savedPayment);
    }


    private String buildVNPayUrl(Payment payment, HttpServletRequest request, String bankCode)
            throws UnsupportedEncodingException {

        Map<String, String> vnpParams = new HashMap<>();

        vnpParams.put("vnp_Version", vnPayConfig.getVersion());
        vnpParams.put("vnp_Command", vnPayConfig.getCommand());
        vnpParams.put("vnp_TmnCode", vnPayConfig.getTmnCode());
        vnpParams.put("vnp_Amount", String.valueOf(payment.getAmount().longValue() * 100)); // VNPay yêu cầu * 100
        vnpParams.put("vnp_CurrCode", "VND");

        if (bankCode != null && !bankCode.isEmpty()) {
            vnpParams.put("vnp_BankCode", bankCode);
        }

        vnpParams.put("vnp_TxnRef", payment.getTransactionReference());
        vnpParams.put("vnp_OrderInfo", payment.getOrderInfo());
        vnpParams.put("vnp_OrderType", vnPayConfig.getOrderType());
        vnpParams.put("vnp_Locale", "vn");
        vnpParams.put("vnp_ReturnUrl", vnPayConfig.getReturnUrl());
        String ipAddr = VNPayUtil.getIpAddress(request);

        if(ipAddr.equals("0:0:0:0:0:0:0:1")){
            ipAddr = "127.0.0.1";
        }

        vnpParams.put("vnp_IpAddr", ipAddr);

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnpCreateDate = formatter.format(cld.getTime());
        vnpParams.put("vnp_CreateDate", vnpCreateDate);

        cld.add(Calendar.MINUTE, 15); // Timeout 15 phút
        String vnpExpireDate = formatter.format(cld.getTime());
        vnpParams.put("vnp_ExpireDate", vnpExpireDate);

        // Sắp xếp params và tạo query string
        List<String> fieldNames = new ArrayList<>(vnpParams.keySet());
        Collections.sort(fieldNames);

        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();

        Iterator<String> itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = itr.next();
            String fieldValue = vnpParams.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                // Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));

                // Build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));

                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }

        String queryUrl = query.toString();
        String vnpSecureHash = VNPayUtil.hmacSHA512(vnPayConfig.getHashSecret(), hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnpSecureHash;

        String paymentUrl = vnPayConfig.getVnpayUrl() + "?" + queryUrl;

        log.info("Payment URL created for transaction: {}", payment.getTransactionReference());

        return paymentUrl;
    }


    private User getCurrentUser() {
        var context = SecurityContextHolder.getContext();

        String username = context.getAuthentication().getName();

        return userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTS));
    }

    private String generatePaymentCode() {
        long count = paymentRepository.count() + 1;

        return String.format("PAY%04d", count);
    }

}