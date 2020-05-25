package com.pachole.controllers;

import com.pachole.entities.Client;
import com.pachole.entities.Etiquette;
import com.pachole.entities.User;
import com.pachole.serviceDAO.ClientFacade;
import com.pachole.serviceDAO.EtiquetteFacade;
import com.pachole.utils.SessionUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

@Named
@RequestScoped
public class assignLabel implements Serializable {

    private User loggedUser;
    private List<Client> clientList;
    private List<Etiquette> etiquetteList;
    private static Client selectedClient;
    private String etiquette;
    private Etiquette chosenEtiquette;

    @Inject
    private ClientFacade clientDAO;

    @Inject
    private EtiquetteFacade etiquetteDAO;

    @PostConstruct
    public void init() {
        HttpSession session = SessionUtil.getSession();
        loggedUser = (User) session.getAttribute("user");
        clientList = clientDAO.findClientsWithoutLabel(loggedUser);
    }

    public List<Etiquette> findAllEtiquettes() {
        etiquetteList = new ArrayList<Etiquette>();
        try {
            etiquetteList = etiquetteDAO.getAllEtiquettes(loggedUser);
        } catch (Exception e) {
            throw new Error(e);
        }
        return etiquetteList;
    }

    public void updateClient() {
        chosenEtiquette = etiquetteDAO.checkExistance(etiquette);
        List<Etiquette> etiquetteList = new ArrayList<Etiquette>();
        etiquetteList.add(chosenEtiquette);
        selectedClient.setEtiquetteCollection(etiquetteList);
        clientDAO.edit(selectedClient);
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

    public void setselectedClient(Client selectedClient) {
        this.selectedClient = selectedClient;
    }

    public List<Etiquette> getEtiquetteList() {
        return etiquetteList;
    }

    public void setEtiquetteList(List<Etiquette> etiquetteList) {
        this.etiquetteList = etiquetteList;
    }

    public String getEtiquette() {
        return etiquette;
    }

    public void setEtiquette(String etiquette) {
        this.etiquette = etiquette;
    }

    public void setChosenEtiquette(Etiquette chosenEtiquette) {
        this.chosenEtiquette = chosenEtiquette;
    }

    public Etiquette getChosenEtiquette() {
        return chosenEtiquette;
    }

}
