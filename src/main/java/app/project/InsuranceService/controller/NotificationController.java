package app.project.InsuranceService.controller;

import app.project.InsuranceService.dto.response.Auth.ApiResponse;
import app.project.InsuranceService.dto.response.Notification.NotificationResponse;
import app.project.InsuranceService.entity.User;
import app.project.InsuranceService.enums.NotificationType;
import app.project.InsuranceService.service.Notification.NotificationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/notification")
public class NotificationController {
    NotificationService notificationService;

//    @PostMapping("/create")
//    public ResponseEntity<ApiResponse<NotificationResponse>> createNotification(@RequestParam User user,
//                                                                                @RequestParam String tittle,
//                                                                                @RequestParam String message,
//                                                                                @RequestParam NotificationType type) {
//
//        log.info("create Notification");
//
//        return ResponseEntity.status(HttpStatus.CREATED)
//                .body(ApiResponse.<NotificationResponse>builder()
//                        .code(1000)
//                        .message("create notification success")
//                        .result(notificationService.createNotification(user, tittle, message, type))
//                        .build());
//    }
}
