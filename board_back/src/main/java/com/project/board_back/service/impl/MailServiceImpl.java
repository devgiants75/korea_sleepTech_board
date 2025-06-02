package com.project.board_back.service.impl;

import com.project.board_back.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {
    private final JavaMailSender mailSender;
    private final Map<String, String> verificationTokens = new ConcurrentHashMap<>();

    @Override
    public Mono<ResponseEntity<String>> sendSimpleMessage(String email) {
        return Mono.fromSupplier(() -> {
            String token = UUID.randomUUID().toString();
            verificationTokens.put(token, email);

            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email);
            message.setSubject("이메일 인증 요청");
            message.setText("인증을 위해 아래 링크를 클릭하세요:\n" +
                    "http://localhost:8080/api/v1/auth/verify?token=" + token);
            mailSender.send(message);

            return ResponseEntity.ok("인증 메일 전송 완료");
        });
    }

    @Override
    public Mono<ResponseEntity<String>> verifyEmail(String token) {
        return Mono.fromSupplier(() -> {
            String email = verificationTokens.remove(token);
            if (email != null) {
                return ResponseEntity.ok("이메일 인증 성공: " + email);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("유효하지 않은 또는 만료된 토큰입니다.");
            }
        });
    }
}
