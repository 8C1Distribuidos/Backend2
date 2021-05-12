package com.example.org.services;


import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MailService {

    private final String from = "distribuidos8c1@gmail.com";
    private final String username = "distribuidos8c1";
    private final String password = "Chorizada123";

    private Session session;

    /*
    public MimeMessage getMimeMessage() {
        return mimeMessage;
    }*/

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
        if(checkParams(emailContainer, emailSubject, emailBody) == true)
        {
            mimeMessage = new MimeMessage(session);
            mimeMessage.setFrom(new InternetAddress(from));
            mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(emailContainer));
            mimeMessage.setSubject(emailSubject);
            mimeMessage.setText(emailBody);
            sendEmail();
        }
        else{
            System.out.println("Invalid");
        }


    }

    private boolean checkParams(String email, String Subject, String Body){
        boolean boolEmail = checkEmail(email);
        boolean boolSubject = checkText(Subject);
        boolean boolBody = checkText(Body);

        if (boolEmail == true && boolSubject == true && boolBody == true)
        {
            return true;
        }
        return false;
    }

    private boolean checkEmail(String email)
    {
        Pattern pattern = Pattern
                .compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

        Matcher matcher = pattern.matcher(email);
        if (matcher.find() == true)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    private boolean checkText(String text)
    {
        if (text.length() > 1000)
        {
           return false;
        }
        return true;
    }

    private void sendEmail() throws MessagingException {
        Transport.send(mimeMessage);
        System.out.println("Email successfully sent UwU");
    }

}