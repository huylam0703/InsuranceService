package app.project.InsuranceService.service.Email.Impl;

import app.project.InsuranceService.dto.request.Email.EmailRequest;
import app.project.InsuranceService.dto.request.Email.SendEmailRequest;
import app.project.InsuranceService.dto.request.Email.Sender;
import app.project.InsuranceService.dto.response.Email.EmailResponse;
import app.project.InsuranceService.exception.AppException;
import app.project.InsuranceService.exception.ErrorCode;
import app.project.InsuranceService.repository.httpclient.EmailClient;
import app.project.InsuranceService.service.Email.EmailService;
import feign.FeignException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmailServiceImpl implements EmailService {
    EmailClient emailClient;

    @NonFinal
    @Value("${brevo.api-key}")
    String apiKey;

    @NonFinal
    @Value("${brevo.sender.name}")
    String name;

    @NonFinal
    @Value("${brevo.sender.email}")
    String email;

    @Override
    public EmailResponse sendEmail(SendEmailRequest request) {
        EmailRequest emailRequest = EmailRequest.builder()
                .sender(Sender.builder()
                        .name(name)
                        .email(email)
                        .build())
                .to(List.of(request.getTo()))
                .htmlContent(request.getHtmlContent())
                .subject(request.getSubject())
                .build();

        try {
            return emailClient.sendEmail(apiKey, emailRequest);
        }catch (FeignException e){
            log.error("Cannot send email via Brevo: {}", e.contentUTF8());
            throw new AppException(ErrorCode.CANNOT_SEND_EMAIL);
        }
    }
}
