package com.swygbro.packup.notification.controller;

import com.swygbro.packup.notification.service.NotificationService;
import com.swygbro.packup.notification.service.SseEmitterService;
import com.swygbro.packup.notification.vo.NotificationVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/notifications")
public class NotificationController {

    private final SseEmitterService sseEmitterService;
    private final NotificationService notificationService;

    /**
     * 클라이언트에서 SSE 연결 요청
     * ex) EventSource('/notifications/subscribe?userId=user01')
     */
    @GetMapping(value = "/subscribe", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter subscribe(@RequestParam("userId") String userId) {
        log.info("SSE require subscribe : userId={}", userId);
        return sseEmitterService.subscribe(userId);
    }

    @GetMapping("/unread_count")
    public Map<String, Integer> countUnread(@RequestParam String userId) {
        int count = notificationService.countUnread(userId);
        return Map.of("count", count);
    }

    @PostMapping("/readAll")
    public ResponseEntity<Void> markAllAsRead(@RequestBody Map<String, String> request) {
        String userId = request.get("userId");
        notificationService.markAllAsRead(userId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/list")
    public ResponseEntity<List<NotificationVo>> getNotificationList(
            @RequestParam("userId") String userId,
            @RequestParam(defaultValue = "10") int limit) {

        List<NotificationVo> notifications = notificationService.getRecentNotifications(userId, limit);
        return ResponseEntity.ok(notifications);
    }
}