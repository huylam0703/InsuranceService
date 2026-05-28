package app.project.InsuranceService.service.Notification.Impl;

import app.project.InsuranceService.dto.response.Notification.NotificationResponse;
import app.project.InsuranceService.entity.Notification;
import app.project.InsuranceService.entity.User;
import app.project.InsuranceService.enums.NotificationStatus;
import app.project.InsuranceService.enums.NotificationType;
import app.project.InsuranceService.mapper.NotificationMapper;
import app.project.InsuranceService.repository.NotificationRepository;
import app.project.InsuranceService.service.Notification.NotificationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NotificationServiceImpl implements NotificationService {

    NotificationRepository notificationRepository;
    NotificationMapper notificationMapper;
    SimpMessagingTemplate messagingTemplate;

    @Override
    public NotificationResponse createNotification(User user, String title, String message, NotificationType type) {
        Notification notification = Notification.builder()
                .user(user)
                .title(title)
                .message(message)
                .type(type)
                .status(NotificationStatus.SENT)
                .isRead(false)
                .createdAt(LocalDateTime.now())
                .build();

        Notification savedNotification =
                notificationRepository.save(notification);

        NotificationResponse response =
                notificationMapper.toNotificationResponse(savedNotification);

        messagingTemplate.convertAndSendToUser(
                user.getUsername(),
                "/queue/notifications",
                response
        );

        return response;
    }

    @Override
    public NotificationResponse getNotifyPayment() {
        return null;
    }

    @Override
    public NotificationResponse notifyAdmins(
            User admin,
            String title,
            String message,
            NotificationType type
    ) {
        return createNotification(admin, title, message, type);
    }
}