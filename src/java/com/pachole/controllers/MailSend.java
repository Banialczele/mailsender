package com.pachole.controllers;

//import com.pachole.serviceDAO.ClientFacade;
import com.pachole.entities.Client;
import com.pachole.entities.User;
import com.pachole.serviceDAO.ClientFacade;
import com.pachole.utils.SessionUtil;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.inject.Named;
import javax.mail.*;
import java.util.Properties;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpSession;

@Named
@RequestScoped
public class MailSend implements Serializable {

    private String topic;
    private String content;
    private String subject;
    private Date date;

    private List<String> emailList;

    @Inject
    private ClientFacade clientDAO;

    @PostConstruct
    public void init() {
        HttpSession session = SessionUtil.getSession();
        User user = (User) session.getAttribute("user");
//        emailList = clientDAO.findEmailsById(user);
    }

    public String etiquetteList() {
        return "Siemanko";
    }

    public void addEmails() {
//        if (subject != null ) {
//            String[] emails = subject.split("\\s");
//            emailList.addAll(Arrays.asList(emails));
//        }
        for (int i = 0; i < emailList.size(); i++) {
            System.out.println(emailList.get(i));
        }
    }

    public void sendMessage() {

        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.ssl.trust", "smtp.gmail.com");

        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("banialczele@gmail.com", "dupa4ever");
            }
        });
        String[] emails = subject.split("\\s");
        System.out.println(emails);
        System.out.println("Trying to send an email");

        try {
            for (String email : emails) {
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress("banialczele@gmail.com"));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
                message.setSubject(topic);
                message.setText(content);
                Transport.send(message);
                System.out.println(message);
            }
            System.out.println("Done");
        } catch (MessagingException mes) {
            System.out.println(mes);
        }
    }

    public List<String> getEmailList() {
        return emailList;
    }

    public void setEmailList(List<String> emailList) {
        this.emailList = emailList;
    }

//    public List<Client> getEmailList() {
//        return emailList;
//    }
//
//    public void setEmailList(List<Client> emailList) {
//        this.emailList = emailList;
//    }
    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}
