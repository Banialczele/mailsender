package com.pachole.controllers;

import com.pachole.entities.Mail;
import com.pachole.entities.User;
import com.pachole.serviceDAO.MailFacade;
import com.pachole.utils.SessionUtil;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

@Named("mailMessages")
@RequestScoped
public class MailMessages implements Serializable {

    @Inject
    private MailFacade mailDAO;

    private List<Mail> messageList;
    private User loggedUser;

    @PostConstruct
    public void init() {
        HttpSession session = SessionUtil.getSession();
        loggedUser = (User) session.getAttribute("user");
        messageList = mailDAO.findByLoggedUser(loggedUser);
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
}
