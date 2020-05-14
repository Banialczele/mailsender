package com.pachole.controllers;

import com.pachole.entities.Mail;
import com.pachole.entities.Mailstatus;
import com.pachole.entities.User;
import com.pachole.serviceDAO.MailFacade;
import com.pachole.serviceDAO.MailstatusFacade;
import com.pachole.utils.SessionUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

@Named
@ViewScoped
public class MessageStatus implements Serializable {

    @Inject
    private MailstatusFacade statusDAO;

    @Inject
    private MailFacade mailDAO;

    private List<Mailstatus> messageList;
    private List<Mailstatus> successfulList = new ArrayList<Mailstatus>();
    private List<Mailstatus> pendingList = new ArrayList<Mailstatus>();
    private List<Mailstatus> errorList = new ArrayList<Mailstatus>();

    private List<Mail> mailList;
    private User loggedUser;

    @PostConstruct
    public void init() {
        HttpSession session = SessionUtil.getSession();
        loggedUser = (User) session.getAttribute("user");
        messageList = statusDAO.getMailAndClientEmail(loggedUser);
        for (Mailstatus m : messageList) {
            if(m.getStatus().equals("Wysłano")){
                successfulList.add(m);
            } else if(m.getStatus().equals("Oczekuje na wysłanie")){
                pendingList.add(m);
            } else {
                errorList.add(m);
            }
        }
    }

    public List<Mailstatus> getMessageList() {
        return messageList;
    }

    public void setMessageList(List<Mailstatus> messageList) {
        this.messageList = messageList;
    }

    public List<Mailstatus> getSuccessfulList() {
        return successfulList;
    }

    public void setSuccessfulList(List<Mailstatus> successfulList) {
        this.successfulList = successfulList;
    }

    public List<Mailstatus> getPendingList() {
        return pendingList;
    }

    public void setPendingList(List<Mailstatus> pendingList) {
        this.pendingList = pendingList;
    }

    public List<Mailstatus> getErrorList() {
        return errorList;
    }

    public void setErrorList(List<Mailstatus> errorList) {
        this.errorList = errorList;
    }

}
