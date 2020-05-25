package com.pachole.controllers;

import com.pachole.entities.Client;
import com.pachole.entities.User;
import com.pachole.serviceDAO.ClientFacade;
import com.pachole.utils.SessionUtil;
import java.util.List;
import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

@Named
@RequestScoped
public class DisplayClients {

    @Inject
    private ClientFacade clientDAO;

    private List<Client> clientList;
    private List<Client> filteredList;
    private List<String> clientEtiquettes;
    private User loggedUser;
    private Client selectedClient;

    @PostConstruct
    public void init() {
        HttpSession session = SessionUtil.getSession();
        loggedUser = (User) session.getAttribute("user");
        clientList = clientDAO.findClientsByLoggedUser(loggedUser);

    }
    
    public void delete(Client client){        
        clientList.remove(client);
        clientDAO.remove(client);
    }

    public boolean globalFilterFunction(Object value, Object filter, Locale locale) {
        String filterText = (filter == null) ? null : filter.toString().trim().toLowerCase();
        if (filterText == null || filterText.equals("")) {
            return true;
        }
        System.out.println(filterText);
        Client client = (Client) value;
        return client.getName().toLowerCase().contains(filterText) || client.getEmail().toLowerCase().contains(filterText);
    }

    public List<Client> getClientList() {
        return clientList;
    }

    public void setClientList(List<Client> clientList) {
        this.clientList = clientList;
    }

    public Client getSelectedClient() {
        return selectedClient;
    }

    public void setSelectedClient(Client selectedClient) {
        this.selectedClient = selectedClient;
    }

    public List<Client> getFilteredList() {
        return filteredList;
    }

    public void setFilteredList(List<Client> filteredList) {
        this.filteredList = filteredList;
    }

}
