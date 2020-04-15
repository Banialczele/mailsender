package com.pachole.controllers;

import com.pachole.entities.User;
import com.pachole.serviceDAO.UserFacade;
import com.pachole.utils.SessionUtil;
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

@Named
@SessionScoped
public class Registration implements Serializable {
    @Inject
    private UserFacade userDAO;

    private String username;
    private String password;
    private String first;
    private String last;
    private String email;

    private User user;

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

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
                session.setAttribute("userId", newUser.getIdUser());
                session.setAttribute("firstname", first);
                setUser(newUser);
            } catch (Exception e) {
                return null;
            }
            return "addClients?faces-redirect=true";
        } else if (validateEmail == false) {            
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Provided email is invalid", null);
            FacesContext ctx = FacesContext.getCurrentInstance();
            ctx.addMessage("registerForm:register", message);
            System.out.println("wrong email");
        } else {
            return null;
        }
        return null;
    }
}
