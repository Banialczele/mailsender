package com.pachole.controllers;

import com.pachole.entities.Mailstatus;
import com.pachole.entities.User;
import com.pachole.serviceDAO.MailFacade;
import com.pachole.serviceDAO.MailstatusFacade;
import com.pachole.utils.SessionUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

@Named
@RequestScoped
public class MessagePending implements Serializable {

    @Inject
    private MailstatusFacade statusDAO;

    @Inject
    private MailFacade mailDAO;

    @Inject
    private MessageStatusController controller;

    private List<Mailstatus> messageList;
    private List<Mailstatus> pendingList = new ArrayList<Mailstatus>();
    private User loggedUser;

    @PostConstruct
    public void init() {
        messageList = controller.getMessageList();
        for (Mailstatus m : messageList) {
            if (m.getStatus().equals("0")) {
                pendingList.add(m);
            }
        }
    }

    public List<Mailstatus> getMessageList() {
        return messageList;
    }

    public void setMessageList(List<Mailstatus> messageList) {
        this.messageList = messageList;
    }

    public List<Mailstatus> getPendingList() {
        return pendingList;
    }

    public void setPendingList(List<Mailstatus> pendingList) {
        this.pendingList = pendingList;
    }

    public MessageStatusController getController() {
        return controller;
    }

}
