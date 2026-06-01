package app.project.InsuranceService.service.Email;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class EmailTemplateService {

    public String buildClaimPaidEmail(
            String customerName,
            String claimCode,
            BigDecimal amount
    ) {
        return """
                <html>
                    <body>
                        <h2>Thông báo thanh toán bồi thường</h2>
                        <p>Xin chào %s,</p>
                        <p>Yêu cầu bồi thường <b>%s</b> của bạn đã được thanh toán.</p>
                        <p>Số tiền: <b>%s VND</b></p>
                        <p>Cảm ơn bạn đã sử dụng Insurance Service.</p>
                    </body>
                </html>
                """.formatted(customerName, claimCode, amount);
    }
}
