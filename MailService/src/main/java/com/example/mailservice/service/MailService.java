package com.example.mailservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
@RequiredArgsConstructor
public class MailService{
    private final JavaMailSender mailSender;
    @Value("${spring.mail.username}")
    private String from ;
    public void send(String to, String subject, String body){
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(to);
        mailMessage.setSubject(subject);
        mailMessage.setText(body);
        mailMessage.setFrom(from);
        mailSender.send(mailMessage);
    }
    // Метод для отправки письма с подтверждением регистрации
    public void sendConfirmation(String userEmail, String token, String baseUrl) {
        String subject = "Подтверждение регистрации";
        String confirmationUrl = baseUrl + "/confirmation?token=" + token;
        String message = "Чтобы подтвердить регистрацию, перейдите по следующей ссылке: " + confirmationUrl;

        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(userEmail);
        email.setSubject(subject);
        email.setText(message);

        mailSender.send(email);
    }

    // Метод для отправки письма для сброса пароля
    public void sendPasswordResetEmail(String email, String token, String baseUrl) {
        String subject = "Запрос на сброс пароля";
        String resetUrl = baseUrl + "/reset-password?token=" + token;
        String text = "Чтобы сбросить пароль, нажмите ссылку ниже:\n" + resetUrl;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject(subject);
        message.setText(text);

        mailSender.send(message);
    }

}

