package com.pachole.controllers;

import com.pachole.entities.Client;
import com.pachole.entities.Etiquette;
import com.pachole.entities.User;
import com.pachole.serviceDAO.ClientFacade;
import com.pachole.serviceDAO.EtiquetteFacade;
import com.pachole.utils.SessionUtil;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

@Named
@RequestScoped
public class GroupManagement implements Serializable {

    private Part file;
    private String groupName;
    private List<Client> clientsList = new ArrayList<Client>();
    private User loggedUser;

    @Inject
    private EtiquetteFacade etiquetteDAO;

    @Inject
    private ClientFacade clientDAO;

    @PostConstruct
    public void init() {
        HttpSession session = SessionUtil.getSession();
        loggedUser = (User) session.getAttribute("user");
    }

    public String save() {
        Etiquette label = etiquetteDAO.checkExistance(groupName);
        if (label == null) {
            Etiquette newEtiquette = new Etiquette();
            newEtiquette.setName(groupName);
            newEtiquette.setArchive("test");
            newEtiquette.setIdUser(loggedUser);
            newEtiquette.setIdGroup(Integer.MIN_VALUE);
            clientsList.forEach(client -> {
                boolean validation = validateEmail(client.getEmail());
                if (validation) {
                    Client newClient = new Client();
                    newClient.setName(client.getName());
                    newClient.setEmail(client.getEmail());
                    newClient.setIdUser(loggedUser);
                    try {
                        clientDAO.create(newClient);
                    } catch (Exception e) {
                        throw new Error(e);
                    }
                } else if (validation == false) {
                    FacesContext ctx = FacesContext.getCurrentInstance();
                    ctx.addMessage("clientsForm:saveClients", new FacesMessage("Provided email is invalid." + client.getEmail()));
                }
            });
            try {
                etiquetteDAO.create(newEtiquette);
            } catch (Exception e) {
                throw new Error(e);
            }
            return "/mainPage.xhtml";
        } else if (label != null) {
            System.out.println("Your group:     " + label);
            FacesContext ctx = FacesContext.getCurrentInstance();
            ctx.addMessage("groupForm:gropNameId", new FacesMessage("Podana etykieta już istnieje."));
            return null;
        }
        return "/mainPage.xhtml";
    }

    public void addClients() {
        clientsList.add(new Client());
    }

    public Boolean validateEmail(String email) {
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return email.matches(regex);
    }

    public Part getFile() {
        return file;
    }

    public void setFile(Part file) {
        this.file = file;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public List<Client> getClientsList() {
        return clientsList;
    }

    public void setClientsList(List<Client> clientsList) {
        this.clientsList = clientsList;
    }

}
