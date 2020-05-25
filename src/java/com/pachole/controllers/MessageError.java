package com.pachole.controllers;

import com.pachole.entities.Mailstatus;
import com.pachole.entities.User;
import com.pachole.serviceDAO.MailFacade;
import com.pachole.serviceDAO.MailstatusFacade;
import com.pachole.utils.SessionUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

@Named
@RequestScoped
public class MessageError {

    @Inject
    private MailstatusFacade statusDAO;

    @Inject
    private MailFacade mailDAO;

    @Inject
    private MessageStatusController controller;

    private List<Mailstatus> messageList;
    private List<Mailstatus> errorList = new ArrayList<Mailstatus>();
    private User loggedUser;

    @PostConstruct
    public void init() {
        messageList = controller.getMessageList();

        for (Mailstatus m : messageList) {
            if (m.getStatus().equals("2")) {
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

    public List<Mailstatus> getErrorList() {
        return errorList;
    }

    public void setErrorList(List<Mailstatus> errorList) {
        this.errorList = errorList;
    }

    public MessageStatusController getController() {
        return controller;
    }
}
