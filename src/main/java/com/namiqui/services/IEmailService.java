package com.namiqui.services;

public interface IEmailService {
    void sendEmail(String subject, String to, String textMessage, Integer type);
}
