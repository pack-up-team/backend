package com.swygbro.packup.notification.service;

import com.swygbro.packup.notification.mapper.NotificationMapper;
import com.swygbro.packup.notification.mapper.UserTemplateNoticeMapper;
import com.swygbro.packup.notification.service.SseEmitterService;
import com.swygbro.packup.notification.vo.NotificationVo;
import com.swygbro.packup.notification.vo.UserTemplateNoticeVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {

    private final UserTemplateNoticeMapper stepMapper;
    private final NotificationMapper notificationMapper;
    private final SseEmitterService sseEmitterService;

    public void checkAndSendNotifications() {
        String alarmDtTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

        Map<String, String> param = new HashMap<>();
        param.put("alarmDtTime", alarmDtTime);

        List<UserTemplateNoticeVo> targetList = stepMapper.selectNoticeTargetList(param);
        log.info("[Notification scheduler] {} count : {}", alarmDtTime, targetList.size());

        for (UserTemplateNoticeVo target : targetList) {
            String message = String.format("[%s] reserved notificaion.", alarmDtTime.substring(11));

            NotificationVo notification = new NotificationVo();
            notification.setUserId(target.getUserId());
            notification.setMessage(message);
            notification.setTemplateNm(target.getTemplateNm());
            notification.setTemplateNo(target.getTemplateNo());
            notification.setNotificationTime(alarmDtTime);
            notification.setSentAt(alarmDtTime);
            notification.setSent(true);
            notification.setReadYn(false);

            // 알림 테이블에 저장
            notificationMapper.insertNotification(notification);
            log.info("Notification save success : userId={}, templateNo={}", notification.getUserId(), notification.getTemplateNo());

            // SSE 알림 전송
            sseEmitterService.send(notification.getUserId(), notification.getMessage());
        }
    }

    public int countUnread(String userId) {
        return notificationMapper.countUnread(userId);
    }

    public void markAllAsRead(String userId) {
        notificationMapper.markAllAsRead(userId);
    }

    public List<NotificationVo> getRecentNotifications(String userId, int limit) {
        return notificationMapper.selectRecentNotifications(userId, limit);
    }
}