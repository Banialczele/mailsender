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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Named;
import javax.mail.*;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
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
    private List<Client> clientData = new ArrayList<Client>();
    private Mailaccount mailAccount;
    private Mail selectedMail;

    @Inject
    private ClientFacade clientDAO;
    @Inject
    private EtiquetteFacade etiquetteDAO;
    @Inject
    private MailFacade mailDAO;
    @Inject
    private MailaccountFacade accountDAO;
    @Inject
    private MailstatusFacade statusDAO;

    @PostConstruct
    public void init() {
        HttpSession session = SessionUtil.getSession();
        loggedUser = (User) session.getAttribute("user");
        etiquetteList = etiquetteDAO.getAllEtiquettes(loggedUser);
        mailAccount = accountDAO.getMailAccount();
    }

    public List<String> showNamesList(String query) {
        String queryToLowerCase = query.toLowerCase();
        List<String> allLabels = new ArrayList<String>();
        for (Etiquette e : etiquetteList) {
            allLabels.add(e.getName());
        }
        return allLabels.stream().filter(name -> name.toLowerCase().contains(queryToLowerCase)).collect(Collectors.toList());
    }

    public List<Client> messageClients(List<String> etiquetteName) {

        List<Client> clients = clientDAO.findClientEmailByEtiquetteName(etiquetteName);

        List<Client> clientsWithoutDuplicates = new ArrayList<>();

        clients.stream().filter((c) -> (!clientsWithoutDuplicates.contains(c))).forEachOrdered((c) -> {
            clientsWithoutDuplicates.add(c);
        });

        return clientsWithoutDuplicates;
    }

    public List<Etiquette> messageEtiquettes(List<String> etiquetteName) {
        List<Etiquette> etiquettes = etiquetteDAO.findByEtiquetteName(etiquetteName);
        return etiquettes;
    }

    public void sendMessage(Mail selectedMessage) throws MessagingException, ParseException {
        String author = loggedUser.getFirstName() + " " + loggedUser.getUserMail();
        DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm");
        Date dateobj = new Date();

        if (selectedMessage == null) {
            etiquetteCollection = messageEtiquettes(etiquetteName);
            clientData = messageClients(etiquetteName);
        } else if (selectedMessage != null) {
            topic = selectedMessage.getMessageTopic();
            content = selectedMessage.getMessageContent();
            for (Etiquette e : selectedMessage.getEtiquetteCollection()) {
                etiquetteName.add(e.getName());
            }
            etiquetteCollection = messageEtiquettes(etiquetteName);
            clientData = messageClients(etiquetteName);
        }

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
                return new PasswordAuthentication(mailAccount.getMailAddress(), mailAccount.getPassword());
            }
        });

        System.out.println("Trying to send an email");

        //Creating message and saving it in DB.
        Mail emailMessage = new Mail();
        emailMessage.setMessageContent(content.trim());
        emailMessage.setMessageTopic(topic.trim());
        emailMessage.setDate(df.parse(df.format(dateobj)));
        emailMessage.setAuthorName(author);
        emailMessage.setEtiquetteCollection(new ArrayList<Etiquette>());
        emailMessage.setIdUser(loggedUser);
        emailMessage.getEtiquetteCollection().addAll(etiquetteCollection);
        try {
            mailDAO.create(emailMessage);
            //Setting collection of labels assigned to message
            for (Etiquette e : etiquetteCollection) {
                try {
                    e.getMailCollection().add(emailMessage);
                    etiquetteDAO.updateEtiquetteMailCollection(e);
                } catch (Exception ex) {
                    throw new Error(ex);
                }
            }
        } catch (Exception e) {
            throw new Error(e);
        }

        for (Client client : clientData) {
            if (client.getStatus() == 1) {
                try {
                    receiver = client.getEmail();
                    Mailstatus emailStatus = new Mailstatus();

                    try {                  
//                        Setting status of a message.
                        emailStatus.setDate(df.parse(df.format(dateobj)));
                        emailStatus.setMailStatus("0");
                        emailStatus.setStatus("0");
                        emailStatus.setIdClient(client);
                        emailStatus.setIdMail(emailMessage);
                        emailStatus.setIdMailAccounts(mailAccount);
                        statusDAO.create(emailStatus);
                    } catch (ParseException e) {
                        emailStatus.setDate(df.parse(df.format(dateobj)));
                        emailStatus.setMailStatus("2");
                        emailStatus.setStatus("2");
                        emailStatus.setIdClient(client);
                        emailStatus.setIdMail(emailMessage);
                        emailStatus.setIdMailAccounts(mailAccount);
                        statusDAO.update(emailStatus);
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Nie udało się wysłać wiadomości", ""));
                        Logger.getLogger(MailSend.class.getName()).log(Level.SEVERE, null, e);
                        throw new Error(e);
                    }
                    Message message = new MimeMessage(session);
                    message.setFrom(new InternetAddress(mailAccount.getMailAddress()));
                    message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receiver));
                    message.setSubject(emailMessage.getMessageTopic());
                    message.setText(emailMessage.getMessageContent());

                    Transport.send(message);
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Pomyślnie wysłano wiadomość ", " " + message));

                    emailStatus.setDate(df.parse(df.format(dateobj)));
                    emailStatus.setMailStatus("1");
                    emailStatus.setStatus("1");
                    emailStatus.setIdClient(client);
                    emailStatus.setIdMail(emailMessage);
                    emailStatus.setIdMailAccounts(mailAccount);
                    statusDAO.update(emailStatus);
                } catch (ParseException ex) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Nie udało się wysłać wiadomości", ""));
                    Logger.getLogger(MailSend.class.getName()).log(Level.SEVERE, null, ex);
                }
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
