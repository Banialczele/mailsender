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
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

@Named
@RequestScoped
public class EtiquetteManagement implements Serializable{

    private User loggedUser;
    private List<Client> clientList;
    private List<Etiquette> etiquetteList;
    private static Client selectedClient;
    private String etiquetteName;
    private Etiquette choosenEtiquette;

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

    public void updateClient() {
        choosenEtiquette = etiquetteDAO.findByName(etiquetteName);
        List<Etiquette> etiquetteList = new ArrayList<Etiquette>();
        etiquetteList.add(choosenEtiquette);
        selectedClient.setEtiquetteCollection(etiquetteList);
        clientDAO.edit(selectedClient);
    }

    public List<Etiquette> findAllEtiquettes() {
        try {
            etiquetteList = etiquetteDAO.getAllEtiquettes(loggedUser);
        } catch (Exception e) {
            throw new Error(e);
        }
        return etiquetteList;
    }

    public void saveEtiquette() {
        Etiquette etiqutte;
        try {
            etiqutte = etiquetteDAO.findByName(etiquetteName);
        } catch (Exception e) {
            throw new Error(e);
        }
        if (etiqutte == null) {
            Etiquette newEtiquette = new Etiquette();
            newEtiquette.setName(etiquetteName);
            newEtiquette.setArchive("Test");
            newEtiquette.setIdUser(loggedUser);
            try {
                etiquetteDAO.create(newEtiquette);
                FacesContext ctx = FacesContext.getCurrentInstance();
                ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, null,
                        "Pomyślnie dodano etykiete!"));
            } catch (Exception e) {
                FacesContext ctx = FacesContext.getCurrentInstance();
                ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, null, "Coś poszło nie tak!"));
                throw new Error(e);
            }
        } else {
            FacesContext ctx = FacesContext.getCurrentInstance();
            ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, null, "Etykieta o podanej nazwie już istnieje"));
        }
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

    public String getEtiquetteName() {
        return etiquetteName;
    }

    public void setEtiquetteName(String etiquetteName) {
        this.etiquetteName = etiquetteName;
    }

}
