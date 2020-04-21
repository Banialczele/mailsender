package com.pachole.controllers;

import com.pachole.entities.Client;
import com.pachole.entities.User;
import com.pachole.serviceDAO.ClientFacade;
import com.pachole.serviceDAO.UserFacade;
import com.pachole.utils.SessionUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.flow.FlowScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

@Named
@FlowScoped(value="registration")
public class Registration implements Serializable {

    @Inject
    private UserFacade userDAO;

    @Inject
    private ClientFacade clientDAO;

    private String username;
    private String password;
    private String first;
    private String last;
    private String email;
    private User loggedUser;
    private List<Client> clientsList = new ArrayList<Client>();

    public Boolean validateEmail(String email) {
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return email.matches(regex);
    }

    public String register() {
        boolean validateEmail = validateEmail(email);
        User existingUser = userDAO.checkIfExists(username, email);
        if (existingUser == null && validateEmail) {
            User newUser = new User();
            newUser.setUsername(username);
            newUser.setPassword(password);
            newUser.setFirstName(first);
            newUser.setLastName(last);
            newUser.setUserMail(email);
            newUser.setUserRole("user");
            try {
                userDAO.add(newUser);
                HttpSession session = SessionUtil.getSession();
                System.out.println("Session id from registration: " + session.getId());
                session.setAttribute("username", username);
                session.setAttribute("user", newUser);
                session.setAttribute("firstname", first);
                loggedUser = newUser;
            } catch (Exception e) {
                throw new Error(e);
            }
            return "addClients?faces-redirect=true";
        } else if (validateEmail == false) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Provided email is invalid", null);
            FacesContext ctx = FacesContext.getCurrentInstance();
            ctx.addMessage("registerForm:register", message);
            System.out.println("wrong email");
        }
        return null;
    }

    public void addClients() {
        clientsList.add(new Client());
    }

    public String saveClient() {
        clientsList.forEach((client) -> {
            boolean validateEmail = validateEmail(client.getClientEmail());
            if (validateEmail) {
                Client newClient = new Client();
                newClient.setName(client.getName());
                newClient.setCompanyName(client.getCompanyName());
                newClient.setWebsite(client.getWebsite());
                newClient.setClientEmail(client.getClientEmail());
                newClient.setUserid(loggedUser);
                try {
                    clientDAO.add(newClient);
                } catch (Exception e) {
                    throw new Error(e);
                }
            } else if (validateEmail == false) {
                FacesContext ctx = FacesContext.getCurrentInstance();
                ctx.addMessage("clientsForm:saveClients", new FacesMessage("Provided email is invalid." + client.getClientEmail()));
            }
        });
        return "/protected/mainPage.xhtml?faces-redirect=true";
    }

    public void delete(Client client) {
        clientDAO.remove(client);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public User getLoggedUser() {
        return loggedUser;
    }

    public void setLoggedUser(User loggedUser) {
        this.loggedUser = loggedUser;
    }

    public List<Client> getClientsList() {
        return clientsList;
    }

    public void setClientsList(List<Client> clientsList) {
        this.clientsList = clientsList;
    }
}
