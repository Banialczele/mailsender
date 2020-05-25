package com.pachole.controllers;

import com.pachole.entities.Mailstatus;
import com.pachole.entities.User;
import com.pachole.serviceDAO.MailFacade;
import com.pachole.serviceDAO.MailstatusFacade;
import com.pachole.utils.SessionUtil;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

@Named
@RequestScoped
public class MessageQueued {

    @Inject
    private MailstatusFacade statusDAO;

    @Inject
    private MailFacade mailDAO;

    @Inject
    private MessageStatusController controller;
    
    private List<Mailstatus> messageList;
    private List<Mailstatus> queuedList = new ArrayList<Mailstatus>();
    private User loggedUser;

    @PostConstruct
    public void init() {
        HttpSession session = SessionUtil.getSession();
        loggedUser = (User) session.getAttribute("user");
        messageList = statusDAO.getMailAndClientEmail(loggedUser);

        for (Mailstatus m : messageList) {
            if (m.getStatus().equals("3")) {
                queuedList.add(m);
            }
        }

    }

    public List<Mailstatus> getMessageList() {
        return messageList;
    }

    public void setMessageList(List<Mailstatus> messageList) {
        this.messageList = messageList;
    }

    public List<Mailstatus> getQueuedList() {
        return queuedList;
    }

    public void setQueuedList(List<Mailstatus> queuedList) {
        this.queuedList = queuedList;
    }

    public MessageStatusController getController() {
        return controller;
    }
    
    

}
