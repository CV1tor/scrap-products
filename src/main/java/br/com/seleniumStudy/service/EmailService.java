package br.com.seleniumStudy.service;

import io.github.cdimascio.dotenv.Dotenv;

import javax.mail.*;
import javax.mail.internet.*;
import java.io.File;
import java.io.IOException;
import java.util.Properties;

public class EmailService {
    public static void sendEmail(File csv) throws MessagingException, IOException {
        Dotenv dotenv = Dotenv.load();
        final String email = dotenv.get("USER_EMAIL");
        final String password = dotenv.get("USER_PASSWORD");

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");


        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(email, password);
            }
        });
        session.setDebug(true);
        Message message = buildMessage(csv, session, email);
        Transport.send(message);
    }

    private static Message buildMessage(File csv, Session session, String receiver) throws MessagingException, IOException {
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(receiver));
        message.setRecipients(
                Message.RecipientType.TO,
                InternetAddress.parse(receiver)
        );
        message.setSubject("Produtos encontrados");

        MimeBodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setText("Olá, segue os produtos encontrados de hoje chefe!");

        MimeBodyPart attachmentPart = new MimeBodyPart();
        attachmentPart.attachFile(csv);

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);
        multipart.addBodyPart(attachmentPart);


        message.setContent(multipart);
        return message;
    }
}
