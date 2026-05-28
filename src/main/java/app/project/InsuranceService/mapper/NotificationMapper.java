package app.project.InsuranceService.mapper;

import app.project.InsuranceService.dto.response.Notification.NotificationResponse;
import app.project.InsuranceService.entity.Notification;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NotificationMapper {

    NotificationResponse toNotificationResponse(Notification notification);
}
