package com.pachole.controllers;

import com.pachole.entities.Mailstatus;
import com.pachole.entities.User;
import com.pachole.serviceDAO.MailstatusFacade;
import com.pachole.utils.SessionUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

@Named
@ViewScoped
public class MessageStatus implements Serializable {

    @Inject
    private MailstatusFacade statusDAO;

    private List<Mailstatus> successfulList;
    private String topic;
    private Date date;
    private String author;
    private String receiver;
    private User loggedUser;
    private String status;

    @PostConstruct
    public void init() {
        HttpSession session = SessionUtil.getSession();
        loggedUser = (User) session.getAttribute("user");
        successfulList = statusDAO.getMessages(loggedUser);
    }

    public List<Mailstatus> filterMails() {
        successfulList = statusDAO.filterByParameters(author, date, receiver, topic, status);
        return successfulList;
    }

    public List<Mailstatus> resetFilter() {
        topic = null;
        author = null;
        receiver = null;
        status = null;
        successfulList = statusDAO.getMessages(loggedUser);        
        return successfulList;
    }

    public List<Mailstatus> getSuccessfulList() {
        return successfulList;
    }

    public void setSuccessfulList(List<Mailstatus> successfulList) {
        this.successfulList = successfulList;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
