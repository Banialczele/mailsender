package com.pachole.controllers;

import com.pachole.entities.Mail;
import com.pachole.entities.User;
import com.pachole.serviceDAO.MailFacade;
import com.pachole.utils.SessionUtil;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

@Named("mailMessages")
@ViewScoped
public class MailMessages implements Serializable {

    @Inject
    private MailFacade mailDAO;

    private List<Mail> messageList;
    private User loggedUser;
    private String author;
    private Date date;
    private String topic;
    private String receiver;
    private Mail selectedMessage;

    @PostConstruct
    public void init() {
        HttpSession session = SessionUtil.getSession();
        loggedUser = (User) session.getAttribute("user");
        messageList = mailDAO.findByLoggedUser(loggedUser);
    }

    public List<Mail> resetFilter() {
        messageList = mailDAO.findByLoggedUser(loggedUser);
        author = null;
        date = null;
        topic = null;
        receiver = null;
        return messageList;
    }

    public List<Mail> filterMails() throws ParseException {
        if (date != null) {
            SimpleDateFormat df = new SimpleDateFormat("d/MM/yyyy HH:mm");
            Date dataObj = df.parse(df.format(date));
            messageList = mailDAO.filterMails(author, dataObj, topic, loggedUser);
        } else {
            messageList = mailDAO.filterMailsWithoutDate(author, topic, loggedUser);
        }
        return messageList;
    }

    public List<Mail> getMessageList() {
        return messageList;
    }

    public void setMessageList(List<Mail> messageList) {
        this.messageList = messageList;
    }

    public User getLoggedUser() {
        return loggedUser;
    }

    public void setLoggedUser(User loggedUser) {
        this.loggedUser = loggedUser;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public Mail getSelectedMessage() {
        return selectedMessage;
    }

    public void setSelectedMessage(Mail selectedMessage) {
        this.selectedMessage = selectedMessage;
    }

}
