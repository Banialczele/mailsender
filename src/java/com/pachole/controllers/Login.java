package com.pachole.controllers;

import com.pachole.entities.User;
import com.pachole.serviceDAO.UserFacade;
import com.pachole.utils.SessionUtil;
import java.io.IOException;
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;

@Named
@SessionScoped
public class Login implements Serializable {

    @Inject
    private UserFacade userDAO;

    private String email;
    private String password;
    private User loggedUser;
    private boolean loggedIn;

    //validate credentials
    public String doLogin() throws IOException, ServletException {
        User user = userDAO.validateCredentials(email, password);
        if (user != null) {
            loggedIn = true;
            HttpSession session = SessionUtil.getSession();
            session.setAttribute("username", user.getUsername());
            session.setAttribute("userid", user.getIdUser());
            loggedUser = user;
            session.setAttribute("user", loggedUser);
            return "/protected/mainPage?faces-redirect=true";
        } else {
            FacesContext ctx = FacesContext.getCurrentInstance();
            ctx.addMessage(null, new FacesMessage("Unable to login, please check username and password"));
            return "index?faces-redirect=true";
        }
    }

    public String logout() {
        loggedIn = false;
        FacesMessage msg = new FacesMessage("Logout success", "INFO MSG");
        msg.setSeverity(FacesMessage.SEVERITY_INFO);
        FacesContext.getCurrentInstance().addMessage(null, msg);
        return "/index?faces-redirect=true";
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User getLoggedUser() {
        return loggedUser;
    }

    public void setLoggedUser(User loggedUser) {
        this.loggedUser = loggedUser;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public Login() {
    }
}
