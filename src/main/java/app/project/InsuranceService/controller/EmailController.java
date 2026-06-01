package app.project.InsuranceService.controller;

import app.project.InsuranceService.dto.request.Email.SendEmailRequest;
import app.project.InsuranceService.dto.response.Auth.ApiResponse;
import app.project.InsuranceService.dto.response.Email.EmailResponse;
import app.project.InsuranceService.service.Email.EmailService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmailController {
    EmailService emailService;

    @PostMapping("/email/send")
    ResponseEntity<ApiResponse<EmailResponse>> sendEmail(@RequestBody SendEmailRequest request) {
        log.info("Send email request: {}", request);

        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.<EmailResponse>builder()
                        .code(1000)
                        .message("send email success")
                        .result(emailService.sendEmail(request))
                        .build());
    }
}
