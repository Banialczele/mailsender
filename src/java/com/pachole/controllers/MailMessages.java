package com.pachole.controllers;

import com.pachole.entities.Client;
import com.pachole.entities.Mail;
import com.pachole.entities.User;
import com.pachole.serviceDAO.MailFacade;
import com.pachole.utils.SessionUtil;
import java.io.Serializable;
import java.util.List;
import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Named("mailMessages")
@RequestScoped
public class MailMessages implements Serializable {

    @Inject
    private MailFacade mailDAO;

    private List<Mail> messageList;
    private List<Mail> filteredMails;
    private User loggedUser;
    private Mail selectedMail;

    @PostConstruct
    public void init() {
        HttpSession session = SessionUtil.getSession();
        loggedUser = (User) session.getAttribute("user");
        messageList = mailDAO.findByLoggedUser(loggedUser);
    }

    public boolean globalFilterFunction(Object value, Object filter, Locale locale) {
        String filterText = (filter == null) ? null : filter.toString().trim().toLowerCase();
        if (filterText == null || filterText.equals("")) {
            return true;
        }
        Mail mail = (Mail) value;
        return mail.getMessageTopic().toLowerCase().contains(filterText);
    }

    public List<Mail> getMessageList() {
        return messageList;
    }

    public void setMessageList(List<Mail> messageList) {
        this.messageList = messageList;
    }

    public Mail getSelectedMail() {
        return selectedMail;
    }

    public void setSelectedMail(Mail selectedMail) {
        this.selectedMail = selectedMail;
    }

    public List<Mail> getFilteredMails() {
        return filteredMails;
    }

    public void setFilteredMails(List<Mail> filteredMails) {
        this.filteredMails = filteredMails;
    }

}
