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

    @Inject
    private EtiquetteFacade etiquetteDAO;

    private User loggedUser;
    private String clientName = "";
    private String clientEmail = "";
    private List<Client> clientList = new ArrayList<Client>();
    private List<Etiquette> existingEtiquettes = new ArrayList<Etiquette>();
    private String etiquetteName = "";
    private String etiquetteNameFromInput = "";

    @PostConstruct
    public void init() {
        HttpSession session = SessionUtil.getSession();
        loggedUser = (User) session.getAttribute("user");
        existingEtiquettes = etiquetteDAO.getAllEtiquettes(loggedUser);
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
        List<Etiquette> etiquettes = new ArrayList<Etiquette>();        
        if (etiquetteName != null) {
            Etiquette foundLabel = etiquetteDAO.checkExistance(etiquetteName);
            etiquettes.add(foundLabel);
            clientList.forEach((client) -> {
                boolean email = validateEmail(client.getEmail());
                if (email) {
                    Client newClient = new Client();
                    newClient.setName(client.getName());
                    newClient.setEmail(client.getEmail());
                    newClient.setIdUser(loggedUser);
                    newClient.setEtiquetteCollection(etiquettes);
                    try {
                        clientDAO.create(newClient);
                        FacesContext ctx = FacesContext.getCurrentInstance();
                        FacesMessage message = new FacesMessage("Successfully added client " + client.getEmail());
                        ctx.addMessage("clientForm:saveClients", message);
                    } catch (Error e) {
                        throw new Error(e);
                    }   
                } else if (email != true) {
                    FacesContext ctx = FacesContext.getCurrentInstance();
                    FacesMessage message = new FacesMessage("Invalid email, please check email and try again." + client.getEmail());
                    ctx.addMessage("clientForm:clientEmail", message);
                }
            });
        } else {
            Etiquette newEtiquette = new Etiquette();
            newEtiquette.setName(etiquetteNameFromInput);
            newEtiquette.setArchive("Siemaneczko");
            newEtiquette.setIdUser(loggedUser);
            try {
                etiquetteDAO.create(newEtiquette);
                etiquettes.add(newEtiquette);
            } catch (Exception e) {
                throw new Error(e);
            }
            clientList.forEach((client) -> {
                boolean email = validateEmail(client.getEmail());
                if (email) {
                    Client newClient = new Client();
                    newClient.setName(client.getName());
                    newClient.setEmail(client.getEmail());
                    newClient.setIdUser(loggedUser);
                    newClient.setEtiquetteCollection(etiquettes);
                    try {
                        clientDAO.create(newClient);
                        FacesContext ctx = FacesContext.getCurrentInstance();
                        FacesMessage message = new FacesMessage("Successfully added client " + client.getEmail());
                        ctx.addMessage("clientForm:saveClients", message);
                    } catch (Error e) {
                        throw new Error(e);
                    }
                } else if (email != true) {
                    FacesContext ctx = FacesContext.getCurrentInstance();
                    FacesMessage message = new FacesMessage("Invalid email, please check email and try again." + client.getEmail());
                    ctx.addMessage("clientForm:clientEmail", message);
                }
            });
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

    public List<Etiquette> getExistingEtiquettes() {
        return existingEtiquettes;
    }

    public void setExistingEtiquettes(List<Etiquette> existingEtiquettes) {
        this.existingEtiquettes = existingEtiquettes;
    }

    public String getEtiquetteName() {
        return etiquetteName;
    }

    public void setEtiquetteName(String etiquetteName) {
        this.etiquetteName = etiquetteName;
    }

    public String getEtiquetteNameFromInput() {
        return etiquetteNameFromInput;
    }

    public void setEtiquetteNameFromInput(String etiquetteNameFromInput) {
        this.etiquetteNameFromInput = etiquetteNameFromInput;
    }

}
    