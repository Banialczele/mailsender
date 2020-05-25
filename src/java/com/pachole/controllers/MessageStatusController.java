package com.pachole.controllers;

import com.pachole.entities.Mailstatus;
import com.pachole.entities.User;
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
public class MessageStatusController {

    @Inject
    private MailstatusFacade statusDAO;

    private List<Mailstatus> messageList;
    private List<Mailstatus> filteredList;

    private User loggedUser;

    @PostConstruct
    public void init() {
        HttpSession session = SessionUtil.getSession();
        loggedUser = (User) session.getAttribute("user");
        messageList = statusDAO.getMailAndClientEmail(loggedUser);
    }

    public boolean globalFilterFunction(Object value, Object filter, Locale locale) {
        String filterText = (filter == null) ? null : filter.toString().trim().toLowerCase();
        if (filterText == null || filterText.equals("")) {
            return true;
        }

        Mailstatus mailStatus = (Mailstatus) value;
        return mailStatus.getIdMail().getMessageTopic().toLowerCase().contains(filterText) || mailStatus.getDate().contains(filterText) || mailStatus.getIdClient().getEmail().toLowerCase().contains(filterText);
    }

    public List<Mailstatus> getMessageList() {
        return messageList;
    }

    public void setMessageList(List<Mailstatus> messageList) {
        this.messageList = messageList;
    }

    public List<Mailstatus> getFilteredList() {
        return filteredList;
    }

    public void setFilteredList(List<Mailstatus> filteredList) {
        this.filteredList = filteredList;
    }

}