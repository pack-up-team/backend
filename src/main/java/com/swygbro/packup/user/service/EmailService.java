package com.swygbro.packup.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;

    @Value("${app.base-url:http://localhost:8080}")
    private String baseUrl;

    @Value("${spring.mail.username}")
    private String fromEmail;

    public void sendPasswordResetEmail(String toEmail, String token) {
        String resetUrl = baseUrl + "/api/auth/reset-password?token=" + token;
        
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(toEmail);
        message.setSubject("비밀번호 재설정");
        message.setText(
            "안녕하세요.\n\n" +
            "비밀번호 재설정을 요청하셨습니다.\n" +
            "아래 링크를 클릭하여 새로운 비밀번호를 설정해주세요.\n\n" +
            resetUrl + "\n\n" +
            "본 링크는 1시간 후 만료됩니다.\n" +
            "요청하지 않으셨다면 이 메일을 무시해주세요.\n\n" +
            "감사합니다."
        );
        
        javaMailSender.send(message);
    }
}