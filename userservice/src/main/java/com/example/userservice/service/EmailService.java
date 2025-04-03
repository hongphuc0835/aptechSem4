package com.example.userservice.service;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class EmailService {

    private final JavaMailSender mailSender;
    private final ConcurrentHashMap<String, String> otpStorage = new ConcurrentHashMap<>();

    //  Constructor đúng, không có void
    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    //  Tạo OTP (6 chữ số)
    public String generateOtp(String email) {
        String otp = String.valueOf(new Random().nextInt(900000) + 100000);
        otpStorage.put(email, otp);
        return otp;
    }

    //  Xác minh OTP
    public boolean verifyOtp(String email, String otp) {
        return otpStorage.containsKey(email) && otpStorage.get(email).equals(otp);
    }

    //  Xóa OTP sau khi dùng
    public void removeOtp(String email) {
        otpStorage.remove(email);
    }

    // Gửi OTP qua Email
    public void sendOtpEmail(String to) {
        String otp = generateOtp(to);
        String subject = "Mã OTP của bạn";
        String body = "<h3>Mã OTP của bạn là: <strong>" + otp + "</strong></h3>";

        sendEmail(to, subject, body);
        System.out.println("📩 OTP gửi tới: " + to + " - Mã OTP: " + otp);
    }

    //  Gửi email bất kỳ
    public void sendEmail(String to, String subject, String body) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom("hongphuc0835@gmail.com");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body, true);

            mailSender.send(message);
            System.out.println("📩 Email đã gửi đến: " + to);

        } catch (MessagingException e) {
            throw new RuntimeException("Lỗi khi gửi email: " + e.getMessage());
        }
    }
}
