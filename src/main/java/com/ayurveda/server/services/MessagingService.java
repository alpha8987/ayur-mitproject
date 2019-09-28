package com.ayurveda.server.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Properties;

@Service
public class MessagingService {

    @Value("${ayur.email.username}")
    private String username;
    @Value("${ayur.email.password}")
    private String password;
    @Value("${ayur.email.mail-host}")
    private String host;

    private JavaMailSenderImpl sender;

    @PostConstruct
    public void init() {
        this.sender = new JavaMailSenderImpl();
        this.sender.setHost(this.host);
        this.sender.setPort(587);
        this.sender.setUsername(this.username);
        this.sender.setPassword(this.password);
        this.sender.setProtocol("smtp");

        Properties javaMailProperties = new Properties();
        javaMailProperties.put("mail.smtp.starttls.enable", true);
        javaMailProperties.put("mail.smtp.auth", true);

        this.sender.setJavaMailProperties(javaMailProperties);
    }

    public boolean sendSms(String message, String receiverNo) {
        try {
            URL textit = new URL("http://textit.biz/sendmsg/index.php?id=94778132872&pw=3925&to=" + receiverNo + "&text=[Ayur]" + message);
            BufferedReader in = new BufferedReader(new InputStreamReader(textit.openStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null)
                System.out.println(inputLine);
            in.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean sendEmail(String messageBody, String receiver) {

        try {
            MimeMessage message = sender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(receiver);
            helper.setText(messageBody, true);
            sender.send(message);
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return false;
    }
}
