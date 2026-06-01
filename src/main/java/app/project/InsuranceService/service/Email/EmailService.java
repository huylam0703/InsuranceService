package app.project.InsuranceService.service.Email;

import app.project.InsuranceService.dto.request.Email.SendEmailRequest;
import app.project.InsuranceService.dto.response.Email.EmailResponse;

public interface EmailService {

    EmailResponse sendEmail(SendEmailRequest request);
}
