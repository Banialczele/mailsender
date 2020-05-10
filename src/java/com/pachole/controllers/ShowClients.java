package com.pachole.controllers;

import com.pachole.entities.Client;
import com.pachole.entities.Etiquette;
import com.pachole.entities.User;
import com.pachole.serviceDAO.ClientFacade;
import com.pachole.serviceDAO.EtiquetteFacade;
import com.pachole.utils.SessionUtil;
import static com.pachole.utils.SessionUtil.getRequest;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import org.primefaces.component.ajaxstatus.AjaxStatus;

@Named
@ViewScoped
public class ShowClients implements Serializable {

    @Inject
    private ClientFacade clientDAO;

    @Inject
    private EtiquetteFacade etiquetteDAO;

    private List<Client> clientList;
    private List<Etiquette> etiquetteList;

    @PostConstruct
    public void init() {
        HttpSession session = SessionUtil.getSession();
        User user = (User) session.getAttribute("user");
        etiquetteList = etiquetteDAO.getAllEtiquettes(user);
    }

    public List<Client> showClientsForEtiquette(String etiquetteName) {
        clientList = clientDAO.findClientsByEtiquetteName(etiquetteName);
        return clientList;
    }
    
    public void deleteEverything(){
        clientList.clear();
    }
    

    public void deleteEtiquette(Etiquette etiquette) {
        etiquetteDAO.remove(etiquette);
    }

    public void delete(Client client) {
        clientList.remove(client);
        clientDAO.remove(client);
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

}
