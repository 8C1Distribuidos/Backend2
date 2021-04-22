package com.example.org.services;

/*
import com.sun.jdi.IntegerValue;
import com.sun.mail.smtp.SMTPSaslAuthenticator;
 */

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class MailService {

    private final String from = "distribuidos8c1@gmail.com";
    private final String username = "distribuidos8c1";
    private final String password = "Chorizada123";

    private Session session;

    public MimeMessage getMimeMessage() {
        return mimeMessage;
    }

    private MimeMessage mimeMessage;

    public MailService() throws MessagingException {

        String host = "smtp.gmail.com";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "587");

        session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        //MimeMessage mimeMessage = draftEmail();

        //sendEmail(mimeMessage);
    }

    public void draftEmail(String emailContainer, String emailSubject, String emailBody) throws MessagingException {
        /*
        emailContainer = {"lesiet02@gmail.com", "teisel_02@hotmail.com", "adrianllanos753@gmail.com"};
        emailSubject = "Test mail";
        emailBody = "Test body of my email";
         */
        mimeMessage = new MimeMessage(session);
        mimeMessage.setFrom(new InternetAddress(from));
        mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(emailContainer));
        mimeMessage.setSubject(emailSubject);
        mimeMessage.setText(emailBody);
    }

    public void sendEmail() throws MessagingException {
        Transport.send(mimeMessage);
        System.out.println("Email successfully sent UwU");
    }

}