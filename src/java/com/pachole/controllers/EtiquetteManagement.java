package com.pachole.controllers;

import com.pachole.entities.Client;
import com.pachole.entities.Etiquette;
import com.pachole.entities.User;
import com.pachole.serviceDAO.ClientFacade;
import com.pachole.serviceDAO.EtiquetteFacade;
import com.pachole.utils.SessionUtil;
import java.io.Serializable;
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
public class EtiquetteManagement implements Serializable {

    private User loggedUser;
    private List<Client> clientsWithoutLabel;
    private List<Client> clientsWithLabel;
    private List<Etiquette> etiquetteList;
    private Etiquette chosenEtiquette;
    private static Client selectedClient;
    private String etiquetteName;

    @Inject
    private ClientFacade clientDAO;

    @Inject
    private EtiquetteFacade etiquetteDAO;

    @PostConstruct
    public void init() {
        HttpSession session = SessionUtil.getSession();
        loggedUser = (User) session.getAttribute("user");
        clientsWithoutLabel = clientDAO.findClientsWithoutLabel(loggedUser);
    }

    public List<Client> showClients() {
        clientsWithLabel = clientDAO.findClientsWithLabel(loggedUser);
        return clientsWithLabel;
    }

    public void updateClient() {
        chosenEtiquette = etiquetteDAO.findByName(etiquetteName);
        List<Etiquette> updateLabelList;
        updateLabelList = (List<Etiquette>) selectedClient.getEtiquetteCollection();
        updateLabelList.add(chosenEtiquette);        
        clientDAO.edit(selectedClient);
    }

    public List<Etiquette> findAllEtiquettes() {
        etiquetteList = etiquetteDAO.getAllEtiquettes(loggedUser);
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

    public List<Client> getClientsWithoutLabel() {
        return clientsWithoutLabel;
    }

    public void setClientsWithoutLabel(List<Client> clientsWithoutLabel) {
        this.clientsWithoutLabel = clientsWithoutLabel;
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

    public List<Client> getClientsWithLabel() {
        return clientsWithLabel;
    }

    public void setClientsWithLabel(List<Client> clientsWithLabel) {
        this.clientsWithLabel = clientsWithLabel;
    }

    public Etiquette getChosenEtiquette() {
        return chosenEtiquette;
    }

    public void setChosenEtiquette(Etiquette chosenEtiquette) {
        this.chosenEtiquette = chosenEtiquette;
    }

}
