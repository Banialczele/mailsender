package com.pachole.controllers;

import com.pachole.entities.Client;
import com.pachole.entities.User;
import com.pachole.serviceDAO.ClientFacade;
import com.pachole.utils.SessionUtil;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

@Named
@ViewScoped
public class ClientManagement implements Serializable {

    private List<Client> clientsList;
    private List<String> clientsEmail;
    private List<Client> allClients;
    private User loggedUser;

    @Inject
    private ClientFacade clientDAO;

    @PostConstruct
    public void init() {
        clientsList = clientDAO.findAll();
        HttpSession session = SessionUtil.getSession();
        User user = (User) session.getAttribute("user");
        loggedUser = user;
    }

    public List<Client> getClientsList() {
        return clientsList;
    }

    public void setClientsList(List<Client> clientsList) {
        this.clientsList = clientsList;
    }

    public void addClients() {
        clientsList.add(new Client());
    }

    public List<String> getClientsEmail() {
        return clientsEmail;
    }

    public void setClientsEmail(List<String> clientsEmail) {
        this.clientsEmail = clientsEmail;
    }

    public List<Client> allClients() {
        return clientsList;
    }

    public void setAllClients(List<Client> allClients) {
        this.allClients = allClients;
    }

    public User getLoggedUser() {
        return loggedUser;
    }

    public void setLoggedUser(User loggedUser) {
        this.loggedUser = loggedUser;
    }

    public Boolean validateEmail(String email) {
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return email.matches(regex);
    }

    public void delete(Client client) {
        clientDAO.remove(client);
    }

    public List<Client> getAllClients() {
        allClients = clientDAO.findAll();
        return allClients;
    }

    public String saveClient() {
        try {
            clientsList.forEach((client) -> {
                boolean validateEmail = validateEmail(client.getClientEmail());
                if (validateEmail) {
                    Client newClient = new Client();
                    newClient.setName(client.getName());
                    newClient.setCompanyName(client.getCompanyName());
                    newClient.setWebsite(client.getWebsite());
                    newClient.setClientEmail(client.getClientEmail());
                    newClient.setUserid(loggedUser);
                    try {
                        clientDAO.add(newClient);
                    } catch (Exception e) {
                        throw new Error(e);
                    }
                } else if (validateEmail == false) {
                    FacesContext ctx = FacesContext.getCurrentInstance();
                    ctx.addMessage("clientsForm:saveClients", new FacesMessage("Provided email is invalid." + client.getClientEmail()));
                }
            });
            return "../protected/index.xhtml?faces-redirect=true";
        } catch (Error e) {
            throw new Error(e);
        }

    }

    public void getEmails() {
        for (int i = 0; i < clientsList.size(); i++) {
            clientsEmail.add(clientsList.get(i).getClientEmail());
        }
        System.out.println(clientsEmail);
    }
}
