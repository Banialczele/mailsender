package com.pachole.controllers;

import com.pachole.entities.Client;
import com.pachole.entities.Etiquette;
import com.pachole.entities.User;
import com.pachole.serviceDAO.ClientFacade;
import com.pachole.serviceDAO.EtiquetteFacade;
import com.pachole.utils.SessionUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import org.primefaces.event.ToggleEvent;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import org.primefaces.event.CellEditEvent;

@Named
@ViewScoped
public class ShowClients implements Serializable {

    @Inject
    private ClientFacade clientDAO;

    @Inject
    private EtiquetteFacade etiquetteDAO;

    private List<Client> clientList = new ArrayList<Client>();
    private List<Etiquette> etiquetteList;
    private User loggedUser;
    private Client showClientDetails;

    @PostConstruct
    public void init() {
        HttpSession session = SessionUtil.getSession();
        loggedUser = (User) session.getAttribute("user");
        etiquetteList = etiquetteDAO.getAllEtiquettes(loggedUser);
    }

    public void delete(Client client) {
        System.out.println(client);
        etiquetteList.forEach(etiquette -> {
            System.out.println("przed: " + etiquette.getClientCollection());
            etiquette.getClientCollection().remove(client);
            System.out.println("po: " + etiquette.getClientCollection());
        });
        clientDAO.remove(client);
    }

    public void onCellEdit(CellEditEvent event) {
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();
        FacesContext context = FacesContext.getCurrentInstance();
        Client client = context.getApplication().evaluateExpressionGet(context, "#{client}", Client.class);

        if (newValue != null && !newValue.equals(oldValue)) {
            client.setStatus((int) newValue);
            try {
                clientDAO.edit(client);
            } catch(Exception e){
                throw new Error(e);
            }
        }
    }

    public void onToggle(ToggleEvent event) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, event.getComponent().getId() + " toggled", "Status:" + event.getVisibility().name());
        FacesContext.getCurrentInstance().addMessage("groupsForm:msgs", message);
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

    public Client getShowClientDetails() {
        return showClientDetails;
    }

    public void setShowClientDetails(Client showClientDetails) {
        this.showClientDetails = showClientDetails;
    }

}
