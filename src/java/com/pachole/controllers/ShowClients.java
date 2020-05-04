//package com.pachole.controllers;
//
//import com.pachole.entities.Client;
//import com.pachole.entities.User;
//import com.pachole.serviceDAO.ClientFacade;
//import com.pachole.utils.SessionUtil;
//import java.io.Serializable;
//import java.util.List;
//import javax.annotation.PostConstruct;
//import javax.enterprise.context.RequestScoped;
//import javax.faces.view.ViewScoped;
//import javax.inject.Inject;
//import javax.inject.Named;
//import javax.servlet.http.HttpSession;
//
//@Named
//@ViewScoped
//public class ShowClients implements Serializable{
//
//    @Inject
//    private ClientFacade clientDAO;
//
//    private List<Client> clientList;
//
//    @PostConstruct
//    public void init() {
//        HttpSession session = SessionUtil.getSession();
//        User user = (User) session.getAttribute("user");
//        clientList = clientDAO.findAllById(user);
//    }
//
//    public List<Client> getClientList() {
//        return clientList;
//    }
//
//    public void setClientList(List<Client> clientList) {
//        this.clientList = clientList;
//    }
//
//    public List<Client> showClients() {
//        System.out.println("Lista klientów: " + clientList);
//        return clientList;
//    }
//    
//     public void delete(Client client) {
//        clientList.remove(client);
//        clientDAO.remove(client);
//    }
//}
