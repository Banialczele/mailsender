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
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import org.primefaces.event.CellEditEvent;

@Named
@RequestScoped
public class DisplayClients implements Serializable {

    @Inject
    private ClientFacade clientDAO;

    private List<Client> clientList;
    private User loggedUser;

    @PostConstruct
    public void init() {
        HttpSession session = SessionUtil.getSession();
        loggedUser = (User) session.getAttribute("user");
        clientList = clientDAO.findClientsByLoggedUser(loggedUser);
    }

    public void delete(Client client) {
        clientList.remove(client);
        clientDAO.remove(client);
    }

    public void onCellEdit(CellEditEvent event) {
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();

        FacesContext context = FacesContext.getCurrentInstance();
        Client client = context.getApplication().evaluateExpressionGet(context, "#{c}", Client.class);
        if (newValue != null && !newValue.equals(oldValue)) {            
            client.setStatus((int) newValue);
            try {
                clientDAO.edit(client);
            } catch (Exception e) {
                throw new Error(e);
            }
        }
    }

    public List<Client> getClientList() {
        return clientList;
    }

    public void setClientList(List<Client> clientList) {
        this.clientList = clientList;
    }

}
