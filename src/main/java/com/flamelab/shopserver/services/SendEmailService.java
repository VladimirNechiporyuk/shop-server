package com.flamelab.shopserver.services;

public interface SendEmailService {

    void sendEmail(String receiverEmail, String subject, String text);

}
