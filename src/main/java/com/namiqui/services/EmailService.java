package com.namiqui.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
public class EmailService implements IEmailService {

    @Value("${namiqui.smtp.host}")
    private String smtpHost;

    @Value("${namiqui.smtp.account}")
    private String smtpAccount;

    @Value("${namiqui.smtp.from}")
    private String smtpFrom;

    @Value("${namiqui.smtp.pwd}")
    private String smtpPassword;

    @Value("${namiqui.smtp.auth}")
    private boolean smtpAuth;

    @Value("${namiqui.smtp.port}")
    private String smtpPort;

    @Value("${namiqui.smtp.ssl}")
    private boolean smtpSsl;

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailService.class);

    @Override
    public void sendEmail(String subject, String to, String textMessage, Integer type) {

        LOGGER.info("Sending email");
        Properties props = System.getProperties();
        props.put("mail.smtp.host", smtpHost);
        props.put("mail.smtp.auth", smtpAuth);
        props.put("mail.smtp.ssl.enable", smtpSsl);
        props.put("mail.smtp.port", smtpPort);

        Session session = Session.getDefaultInstance(props);
        MimeMessage message = new MimeMessage(session);

        try {

            message.setFrom(new InternetAddress(smtpFrom));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));   //Se podrían añadir varios de la misma manera
            message.setSubject(subject);

            if (type.equals(1)) {
                message.setContent(recoveryEmailTemplate(textMessage), "text/html; charset=utf-8");
            } else if (type.equals(2)) {
                message.setContent(registerEmailTemplate(subject, textMessage), "text/html; charset=utf-8");
            } else if (type.equals(3)) {
                message.setContent(recoveryCodeEmailTemplate(textMessage), "text/html; charset=utf-8");
            } else {

                message.setText(textMessage);
            }

            Transport transport = session.getTransport("smtp");
            transport.connect(smtpHost, smtpAccount, smtpPassword);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
            LOGGER.info("Email sended!");
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
        }
    }

    private String recoveryEmailTemplate(String temporalPassword) {
        return "<html> <head> <style>body{background-color: #f4f4f4; text-align: center; font-family: Arial, Helvetica, sans-serif;}.table-content, tr{text-align: center; width: 800px;}.td-border{border: 0.8rem solid #f4f4f4;}td{text-align: center;}.title{color: #ed2743; text-align: center; font-size: 1.2rem; font-weight: bold; width: 100%; padding: 1rem;}.h2-content{text-align: center;}.background-header{background-color: black; background-repeat: no-repeat; background-size: auto; background-position: center; background-size: 230px; background-image: url(\"https://namiqui.s3.amazonaws.com/Namiqui_01.png\"); height: 120px; padding: 1rem; border-radius: 0.5em;}.text-black{padding: 20px; color: #1e1e1f; font-size: 1.2rem;}.pwd-temporal{width: 200px; border: 0.1rem solid #ed2743; padding: 1rem; text-align: center; border-radius: 0.5em; color: #ed2743; font-size: 1.2rem;}.background-content{background-color: white; border-radius: 0.5em; padding: 20px;}.images{align-content: space-between;}</style> </head> <body> <div style=\"text-align: center\"> <table style=\"border: 0;\" class=\"table-content\"> <tr> <td class=\"td-border\"> <div class=\"background-header\"></div></td></tr><tr> <td class=\"td-border\"> <div class=\"background-content\"> <table style=\"width: 100%\"> <tr> <td colspan=\"2\"> <div class=\"title\"> Hola, con gusto te ayudamos a acceder de nuevo </div></td></tr><tr style=\"height: 125px; vertical-align: top\"> <td class=\"text-black\"> Usa esta contrase&ntilde;a temporal </td><td style=\"align-content: center\"> <div class=\"pwd-temporal\">{{TEMPORAL_PWD}}</div></td></tr></table> </div></td></tr><tr> <td class=\"td-border\"> <div class=\"background-content\"> <table style=\"width: 100%\"> <tr> <td> <p class=\"text-black\">Siguenos</p><div class=\"images\"> <img style=\"width: 20px\" src=\"https://namiqui.s3.amazonaws.com/facebook_logo.png\" alt=\"facebook\"/> <img style=\"width: 20px\" src=\"https://namiqui.s3.amazonaws.com/Twitter_Logo.png\" alt=\"twitter\"/> <img style=\"width: 20px\" src=\"https://namiqui.s3.amazonaws.com/IG_Logo.png\" alt=\"instagram\"/> </div></td><td> <p class=\"text-black\">Contacto</p><p>soporte@namiqui.com</p></td></tr></table> </div></td></tr></table> </div></body></html>"
                .replace("{{TEMPORAL_PWD}}", temporalPassword);
    }

    private String recoveryCodeEmailTemplate(String code) {
        return "<html> <head> <style>body{background-color: #f4f4f4; text-align: center; font-family: Arial, Helvetica, sans-serif;}.table-content, tr{text-align: center; width: 800px;}.td-border{border: 0.8rem solid #f4f4f4;}td{text-align: center;}.title{color: #ed2743; text-align: center; font-size: 1.2rem; font-weight: bold; width: 100%; padding: 1rem;}.h2-content{text-align: center;}.background-header{background-color: black; background-repeat: no-repeat; background-size: auto; background-position: center; background-size: 230px; background-image: url(\"https://namiqui.s3.amazonaws.com/Namiqui_01.png\"); height: 120px; padding: 1rem; border-radius: 0.5em;}.text-black{padding: 20px; color: #1e1e1f; font-size: 1.2rem;}.pwd-temporal{width: 200px; border: 0.1rem solid #ed2743; padding: 1rem; text-align: center; border-radius: 0.5em; color: #ed2743; font-size: 1.2rem;}.background-content{background-color: white; border-radius: 0.5em; padding: 20px;}.images{align-content: space-between;}</style> </head> <body> <div style=\"text-align: center\"> <table style=\"border: 0;\" class=\"table-content\"> <tr> <td class=\"td-border\"> <div class=\"background-header\"></div></td></tr><tr> <td class=\"td-border\"> <div class=\"background-content\"> <table style=\"width: 100%\"> <tr> <td colspan=\"2\"> <div class=\"title\"> Hola, con gusto te ayudamos a acceder de nuevo </div></td></tr><tr style=\"height: 125px; vertical-align: top\"> <td class=\"text-black\"> Recibimos tu solicitud para recuperar tu password, utiliza este c&oacute;digo: </td><td style=\"align-content: center\"> <div class=\"pwd-temporal\">{{TEMPORAL_PWD}}</div></td></tr></table> </div></td></tr><tr> <td class=\"td-border\"> <div class=\"background-content\"> <table style=\"width: 100%\"> <tr> <td> <p class=\"text-black\">Siguenos</p><div class=\"images\"> <img style=\"width: 20px\" src=\"https://namiqui.s3.amazonaws.com/facebook_logo.png\" alt=\"facebook\"/> <img style=\"width: 20px\" src=\"https://namiqui.s3.amazonaws.com/Twitter_Logo.png\" alt=\"twitter\"/> <img style=\"width: 20px\" src=\"https://namiqui.s3.amazonaws.com/IG_Logo.png\" alt=\"instagram\"/> </div></td><td> <p class=\"text-black\">Contacto</p><p>soporte@namiqui.com</p></td></tr></table> </div></td></tr></table> </div></body></html>"
                .replace("{{TEMPORAL_PWD}}", code);
    }

    private String registerEmailTemplate(String title, String message) {
        return "<html> <head> <style>body{background-color: #f4f4f4; text-align: center; font-family: Arial, Helvetica, sans-serif;}.table-content, tr{text-align: center; width: 800px;}.td-border{border: 0.8rem solid #f4f4f4;}td{text-align: center;}.title{color: #ed2743; text-align: center; font-size: 1.2rem; font-weight: bold; width: 100%; padding: 1rem;}.h2-content{text-align: center;}.background-header{background-color: black; background-repeat: no-repeat; background-size: auto; background-position: center; background-size: 230px; background-image: url(\"https://namiqui.s3.amazonaws.com/Namiqui_01.png\"); height: 120px; padding: 1rem; border-radius: 0.5em;}.text-black{padding: 20px; color: #1e1e1f; font-size: 1.2rem;}.pwd-temporal{width: 200px; border: 0.1rem solid #ed2743; padding: 1rem; text-align: center; border-radius: 0.5em; color: #ed2743; font-size: 1.2rem;}.background-content{background-color: white; border-radius: 0.5em; padding: 20px;}.images{align-content: space-between;}</style> </head> <body> <div style=\"text-align: center\"> <table style=\"border: 0;\" class=\"table-content\"> <tr> <td class=\"td-border\"> <div class=\"background-header\"></div></td></tr><tr> <td class=\"td-border\"> <div class=\"background-content\"> <table style=\"width: 100%\"> <tr> <td> <div class=\"title\">{{TITLE_MSG}}</div></td></tr><tr style=\"height: 125px; vertical-align: top\"> <td class=\"text-black\">{{TEXT_MSG}}</td></tr></table> </div></td></tr><tr> <td class=\"td-border\"> <div class=\"background-content\"> <table style=\"width: 100%\"> <tr> <td> <p class=\"text-black\">Siguenos</p><div class=\"images\"> <img style=\"width: 20px\" src=\"https://namiqui.s3.amazonaws.com/facebook_logo.png\" alt=\"facebook\"/> <img style=\"width: 20px\" src=\"https://namiqui.s3.amazonaws.com/Twitter_Logo.png\" alt=\"twitter\"/> <img style=\"width: 20px\" src=\"https://namiqui.s3.amazonaws.com/IG_Logo.png\" alt=\"instagram\"/> </div></td><td> <p class=\"text-black\">Contacto</p><p>soporte@namiqui.com</p></td></tr></table> </div></td></tr></table> </div></body></html>"
                .replace("{{TITLE_MSG}}", title).replace("{{TEXT_MSG}}", message);
    }
}
