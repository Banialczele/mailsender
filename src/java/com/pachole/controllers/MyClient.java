package com.pachole.controllers;

import com.pachole.entities.Client;
import com.pachole.entities.Etiquette;
import com.pachole.entities.User;
import com.pachole.serviceDAO.ClientFacade;
import com.pachole.serviceDAO.EtiquetteFacade;
import com.pachole.utils.SessionUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

@Named
@ViewScoped
public class MyClient implements Serializable {

    @Inject
    private ClientFacade clientDAO;

    private User loggedUser;
    private String clientName = "";
    private String clientEmail = "";
    private List<Client> clientList = new ArrayList<Client>();

    @PostConstruct
    public void init() {
        HttpSession session = SessionUtil.getSession();
        loggedUser = (User) session.getAttribute("user");
    }

    public void addClient() {
        Client client = new Client();
        clientList.add(client);
    }

    public Boolean validateEmail(String email) {
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return email.matches(regex);
    }

    public void saveClient() {
        boolean email;
        Client client;
        for (Client c : clientList) {
            email = validateEmail(c.getEmail());
            client = clientDAO.checkExistanceByEmail(c.getEmail(), loggedUser);
            if (email && client == null) {
                Client newClient = new Client();
                newClient.setName(c.getName());
                newClient.setEmail(c.getEmail());
                newClient.setStatus(1);
                newClient.setIdUser(loggedUser);
                try {
                    clientDAO.create(newClient);
                    FacesContext ctx = FacesContext.getCurrentInstance();
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, null ,"Pomyślnie dodano klienta " + c.getEmail());
                    ctx.addMessage(null, message);
                } catch (Error e) {
                    throw new Error(e);
                }
            } else if (email == false) {
                FacesContext ctx = FacesContext.getCurrentInstance();
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, null , "Nieprawidłowy format adresu email " + c.getEmail());
                ctx.addMessage(null, message);
            } else if (client != null) {
                FacesContext ctx = FacesContext.getCurrentInstance();
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, null , "Klient o podanym adresie email " + c.getEmail() + " już istnieje");
                ctx.addMessage(null, message);
            }
        }
    }

    public void delete(Client client) {
        clientList.remove(client);
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientEmail() {
        return clientEmail;
    }

    public void setClientEmail(String clientEmail) {
        this.clientEmail = clientEmail;
    }

    public List<Client> getClientList() {
        return clientList;
    }

    public void setClientList(List<Client> clientList) {
        this.clientList = clientList;
    }
}
