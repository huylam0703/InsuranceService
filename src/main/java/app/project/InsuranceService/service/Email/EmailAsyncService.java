package app.project.InsuranceService.service.Email;

import app.project.InsuranceService.dto.request.Email.SendEmailRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailAsyncService {

    private final EmailService emailService;

    @Async
    public void sendEmailAsync(SendEmailRequest request) {
        try {
            emailService.sendEmail(request);
        } catch (Exception e) {
            log.error("Send email failed", e);
        }
    }
}
