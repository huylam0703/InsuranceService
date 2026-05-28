package app.project.InsuranceService.dto.response.Notification;

import app.project.InsuranceService.enums.NotificationStatus;
import app.project.InsuranceService.enums.NotificationType;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NotificationResponse {

    String notificationId;

    String title;

    String message;

    NotificationType type;

    NotificationStatus status;

    Boolean isRead;

    LocalDateTime createdAt;
}
