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
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

@Named
@ViewScoped
public class ShowClients implements Serializable {

    @Inject
    private ClientFacade clientDAO;

    @Inject
    private EtiquetteFacade etiquetteDAO;

    private List<Client> clientList;

    private List<Etiquette> etiquetteList;
    private Etiquette selectedEtiquette;

    private User loggedUser;

    private String newEtiquetteName;
    private boolean archiveValue;
    private String searchString;
    private String name;
    private String status;
    private String email;
    private boolean showTable = false;

    @PostConstruct
    public void init() {
        HttpSession session = SessionUtil.getSession();
        loggedUser = (User) session.getAttribute("user");
        etiquetteList = etiquetteDAO.getAllEtiquettes(loggedUser);
    }

    public void delete(Client client) {
        etiquetteList.forEach(etiquette -> {
            etiquette.getClientCollection().remove(client);
        });
        clientDAO.remove(client);
    }

    public List<Etiquette> filterEtiquettes() {
        etiquetteList = etiquetteDAO.findListBySubstring(searchString);
        return etiquetteList;
    }

    public List<Client> clientsForChosenLabel() {
        showTable = true;
        clientList = clientDAO.findClientsByEtiquette(selectedEtiquette.getName());
        return clientList;
    }

    public void updateMessage() {
        System.out.println("xD");
    }

    public void deleteEtiquette() {
        etiquetteList.remove(selectedEtiquette);
        etiquetteDAO.remove(selectedEtiquette);
    }

    public void updateEtiquette() {
        etiquetteDAO.updateEtiquette(selectedEtiquette, newEtiquetteName, archiveValue);
        newEtiquetteName = null;
        archiveValue = false;
    }

    public List<Client> filterClients() {
        clientList = clientDAO.filterClientsByData(name, email, status);
        return clientList;
    }

    public List<Client> getClientList() {
        return clientList;
    }

    public void setClientList(List<Client> clientList) {
        this.clientList = clientList;
    }

    public List<Etiquette> getEtiquetteList() {
        return etiquetteList;
    }

    public void setEtiquetteList(List<Etiquette> etiquetteList) {
        this.etiquetteList = etiquetteList;
    }

    public Etiquette getSelectedEtiquette() {
        return selectedEtiquette;
    }

    public void setSelectedEtiquette(Etiquette selectedEtiquette) {
        this.selectedEtiquette = selectedEtiquette;
    }

    public boolean isShowTable() {
        return showTable;
    }

    public void setShowTable(boolean showTable) {
        this.showTable = showTable;
    }

    public String getSearchString() {
        return searchString;
    }

    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNewEtiquetteName() {
        return newEtiquetteName;
    }

    public void setNewEtiquetteName(String newEtiquetteName) {
        this.newEtiquetteName = newEtiquetteName;
    }

    public boolean isArchiveValue() {
        return archiveValue;
    }

    public void setArchiveValue(boolean archiveValue) {
        this.archiveValue = archiveValue;
    }

}
