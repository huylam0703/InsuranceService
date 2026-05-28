package app.project.InsuranceService.service.Notification;

import app.project.InsuranceService.dto.response.Notification.NotificationResponse;
import app.project.InsuranceService.entity.User;

import app.project.InsuranceService.enums.NotificationType;

public interface NotificationService {

    NotificationResponse notifyAdmins(User user, String tittle, String message, NotificationType type);

    NotificationResponse createNotification(User user, String title, String message, NotificationType type);

    NotificationResponse getNotifyPayment();
}
