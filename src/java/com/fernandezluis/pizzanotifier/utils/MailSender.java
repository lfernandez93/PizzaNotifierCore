/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fernandezluis.pizzanotifier.utils;

import com.fernandezluis.pizzanotifier.domain.Subscriber;
import java.util.Properties;
import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author Luis Fernandez
 */
@Stateless
public  class MailSender {
    @Asynchronous
    public void sendMail(String from, String to, String body,String subject) {
        System.out.println("build email to " + to);
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.mum.edu");
        props.put("mail.smtp.port", "25");

        Session mailSession = Session.getDefaultInstance(props);
        Message simpleMessage = new MimeMessage(mailSession);

        InternetAddress fromAddress = null;
        InternetAddress toAddress = null;
        try {
            fromAddress = new InternetAddress(from);
            toAddress = new InternetAddress(to);
        } catch (AddressException e) {
            System.out.println("Sending message failed");
        }

        try {
            simpleMessage.setFrom(fromAddress);
            simpleMessage.setRecipient(Message.RecipientType.TO, toAddress);
            simpleMessage.setSubject(subject);
            simpleMessage.setText(body);

            Transport.send(simpleMessage);
            System.out.println("sent email to " + toAddress);
        } catch (MessagingException e) {
            System.out.println("fail transport.send");
        }
    }
}
