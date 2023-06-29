package com.flamelab.shopserver.services.impl;

import com.flamelab.shopserver.services.SendEmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

//@Service
//@RequiredArgsConstructor
public class SendEmailServiceImpl implements SendEmailService {

    @Value("\\${spring.mail.sender.email}")
    private String sender;

//    private final JavaMailSender javaMailSender;

    @Override
    public void sendEmail(String receiverEmail, String subject, String text) {
//        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
//        MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
//        try {
//            message.setFrom(new InternetAddress(sender));
//            message.setTo(receiverEmail);
//            message.setSubject(subject);
//            message.setText(text, true);
//            javaMailSender.send(mimeMessage);
//        } catch (MessagingException e) {
//            throw new RuntimeException(e);
//        }
    }

}
