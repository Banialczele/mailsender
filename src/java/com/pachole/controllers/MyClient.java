package com.pachole.controllers;

import com.pachole.entities.Client;
import com.pachole.entities.User;
import com.pachole.serviceDAO.ClientFacade;
import com.pachole.utils.SessionUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

@Named
@ViewScoped
public class MyClient implements Serializable {

    @Inject
    private ClientFacade clientDAO;

    private String clientName;
    private String companyName;
    private String clientWebsite;
    private String clientEmail;
    private List<Client> clientList = new ArrayList<Client>();

    public void addClient() {
        Client client = new Client();
        clientList.add(client);
    }

    public Boolean validateEmail(String email) {
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return email.matches(regex);
    }

    public void saveClient() {
        HttpSession session = SessionUtil.getSession();
        User loggedUser = (User) session.getAttribute("user");
        clientList.forEach((client) -> {
            boolean email = validateEmail(client.getClientEmail());          
//            Client existingClient = clientDAO.findByEmail(client.getClientEmail());
             
            if (email == true) {
                Client newClient = new Client();
                newClient.setName(client.getName());
                newClient.setCompanyName(client.getCompanyName());
                newClient.setWebsite(client.getWebsite());
                newClient.setClientEmail(client.getClientEmail());
                newClient.setUserid(loggedUser);
                try {
                    clientDAO.add(newClient);
                } catch (Error e) {
                    throw new Error(e);
                }
            } else {

            }
        });
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getClientWebsite() {
        return clientWebsite;
    }

    public void setClientWebsite(String clientWebsite) {
        this.clientWebsite = clientWebsite;
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
