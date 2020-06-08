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
    private String searchString;

    @Inject
    private ClientFacade clientDAO;

    @Inject
    private EtiquetteFacade etiquetteDAO;

    @PostConstruct
    public void init() {
        HttpSession session = SessionUtil.getSession();
        loggedUser = (User) session.getAttribute("user");
        clientsWithoutLabel = clientDAO.findClientsWithoutLabel(loggedUser);
        etiquetteList = etiquetteDAO.getAllEtiquettes(loggedUser);
    }

    public List<Etiquette> findAllEtiquettes() {
        return etiquetteList;
    }

    public List<Client> showClients() {
        clientsWithLabel = clientDAO.findClientsWithLabel(loggedUser);
        return clientsWithLabel;
    }

    public void updateClient() {
        chosenEtiquette = etiquetteDAO.findByName(etiquetteName);
        List<Etiquette> updateLabelList;
        updateLabelList = (List<Etiquette>) selectedClient.getEtiquetteCollection();
        try {
            updateLabelList.add(chosenEtiquette);
            clientDAO.edit(selectedClient);
            FacesContext ctx = FacesContext.getCurrentInstance();
            ctx.addMessage("assignLabelMessage", new FacesMessage(FacesMessage.SEVERITY_INFO, "Pomyślnie dodano etykiete do klienta!",
                    null));
        } catch (Exception e) {
            FacesContext ctx = FacesContext.getCurrentInstance();
            ctx.addMessage("assignLabelMessage", new FacesMessage(FacesMessage.SEVERITY_INFO, "Nie udało się dodać etykiety",
                    null));
            throw new Error(e);
        }
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
            newEtiquette.setArchive(1);
            newEtiquette.setIdUser(loggedUser);
            try {
                etiquetteDAO.create(newEtiquette);
                FacesContext ctx = FacesContext.getCurrentInstance();
                ctx.addMessage("addingLabelMessage", new FacesMessage(FacesMessage.SEVERITY_INFO, "Pomyślnie dodano etykiete!",
                        null));
            } catch (Exception e) {
                FacesContext ctx = FacesContext.getCurrentInstance();
                ctx.addMessage("addingLabelMessage", new FacesMessage(FacesMessage.SEVERITY_INFO, "Coś poszło nie tak!", null));
                throw new Error(e);
            }
        } else {
            FacesContext ctx = FacesContext.getCurrentInstance();
            ctx.addMessage("addingLabelMessage", new FacesMessage(FacesMessage.SEVERITY_INFO, "Etykieta o podanej nazwie już istnieje", null));
        }
    }

    public List<Etiquette> filterEtiquettes() {
        etiquetteList = etiquetteDAO.findListBySubstring(searchString);
        return etiquetteList;
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

    public List<Etiquette> getEtiquetteList() {
        return etiquetteList;
    }

    public void setEtiquetteList(List<Etiquette> etiquetteList) {
        this.etiquetteList = etiquetteList;
    }

    public String getSearchString() {
        return searchString;
    }

    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }

}
