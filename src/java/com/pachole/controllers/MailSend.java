package com.pachole.controllers;

import com.pachole.entities.Client;
import com.pachole.entities.Etiquette;
import com.pachole.entities.Mail;
import com.pachole.entities.Mailaccount;
import com.pachole.entities.Mailstatus;
import com.pachole.entities.User;
import com.pachole.serviceDAO.ClientFacade;
import com.pachole.serviceDAO.EtiquetteFacade;
import com.pachole.serviceDAO.MailFacade;
import com.pachole.serviceDAO.MailaccountFacade;
import com.pachole.serviceDAO.MailstatusFacade;
import com.pachole.utils.SessionUtil;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.inject.Named;
import javax.mail.*;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpSession;

@Named
@RequestScoped
public class MailSend implements Serializable {

    private String topic;
    private String content;
    private String receiver;
    private Date date;
    private User loggedUser;
    private List<String> etiquetteName = new ArrayList<String>();
    private List<Etiquette> etiquetteList = new ArrayList<Etiquette>();
    private List<Etiquette> etiquetteCollection = new ArrayList<Etiquette>();
    private List<Client> clientEmails;
    private List<Mailaccount> mailAccount;

    @Inject
    private ClientFacade clientDAO;
    @Inject
    private EtiquetteFacade etiquetteDAO;
    @Inject
    private MailFacade mailDAO;
    @Inject
    private MailaccountFacade mailAccountDAO;

    @PostConstruct
    public void init() {
        HttpSession session = SessionUtil.getSession();
        loggedUser = (User) session.getAttribute("user");
        etiquetteList = etiquetteDAO.getAllEtiquettes(loggedUser);
        mailAccount = mailAccountDAO.getMailAccount();
    }

    public List<String> showNamesList(String query) {
        String queryToLowerCase = query.toLowerCase();
        List<String> allLabels = new ArrayList<String>();
        for(Etiquette e : etiquetteList){
            allLabels.add(e.getName());
        }      
        
        return allLabels.stream().filter( name -> name.toLowerCase().contains(queryToLowerCase)).collect(Collectors.toList()); 
    }

    public void sendMessage() throws MessagingException {
        clientEmails = clientDAO.findClientEmailByEtiquetteName(etiquetteName);
        etiquetteCollection = etiquetteDAO.findByEtiquetteName(etiquetteName);
        String author = loggedUser.getFirstName() + " " + loggedUser.getLastName() + " " + loggedUser.getUserMail();

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
        System.out.println("Trying to send an email");

        Mail emailMessage;
        Mailstatus emailStatus;
        for (int i = 0; i < clientEmails.size(); ++i) {
            try {
                receiver = String.valueOf(clientEmails.get(i));
                emailMessage = new Mail();
                emailStatus = new Mailstatus();
                
                emailMessage.setReceiver(receiver);
                emailMessage.setMessageContent(content);
                emailMessage.setMessageTopic(topic);
                emailMessage.setDate("07/05/2020");
                emailMessage.setAuthorName(author);
                emailMessage.setEtiquetteCollection(new ArrayList<Etiquette>());
                emailMessage.setIdUser(loggedUser);
               
                for (Etiquette e : etiquetteCollection) {
                    emailMessage.getEtiquetteCollection().add(e);
                }
                try {
                    for( Etiquette e : etiquetteCollection ){
                        e.getMailCollection().add(emailMessage);
                        
                        etiquetteDAO.updateEtiquetteMailCollection(e);
                    }
                } catch (Exception e) {
                    throw new Error(e);
                }
                
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress("banialczele@gmail.com"));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receiver));
                message.setSubject(topic);
                message.setText(content);
                Transport.send(message);
                
            } catch (AddressException ex) {
                Logger.getLogger(MailSend.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        System.out.println("Done");
    }

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
        return receiver;
    }

    public void setSubject(String subject) {
        this.receiver = subject;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<Etiquette> getEtiquetteList() {
        return etiquetteList;
    }

    public void setEtiquetteList(List<Etiquette> etiquetteList) {
        this.etiquetteList = etiquetteList;
    }

    public List<String> getEtiquetteName() {
        return etiquetteName;
    }

    public void setEtiquetteName(List<String> etiquetteName) {
        this.etiquetteName = etiquetteName;
    }
}
