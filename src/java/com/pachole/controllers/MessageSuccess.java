package com.pachole.controllers;

import com.pachole.entities.Mailstatus;
import com.pachole.entities.User;
import com.pachole.serviceDAO.MailstatusFacade;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class MessageSuccess implements Serializable {

    @Inject
    private MailstatusFacade statusDAO;
    
    @Inject
    private MessageStatusController controller;
    

    private List<Mailstatus> messageList;
    private List<Mailstatus> successfulList = new ArrayList<Mailstatus>();

    private User loggedUser;

    @PostConstruct
    public void init() {
        messageList = controller.getMessageList();
        System.out.println("Lista wiadomości: " + messageList);
        for (Mailstatus m : messageList) {
            if (m.getStatus().equals("1")) {
                successfulList.add(m);
            }
        }
    }

    public List<Mailstatus> getSuccessfulList() {
        return successfulList;
    }

    public void setSuccessfulList(List<Mailstatus> successfulList) {
        this.successfulList = successfulList;
    }

    public List<Mailstatus> getMessageList() {
        return messageList;
    }

    public void setMessageList(List<Mailstatus> messageList) {
        this.messageList = messageList;
    }

    public MessageStatusController getController() {
        return controller;
    }
}
