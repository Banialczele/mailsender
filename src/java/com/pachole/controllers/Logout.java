package com.pachole.controllers;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.inject.Named;

@Named
@RequestScoped
public class Logout {

    public String logout() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        FacesContext facesContext = FacesContext.getCurrentInstance();
        Flash flash = facesContext.getExternalContext().getFlash();
        flash.setKeepMessages(true);
        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Successfully logged out!", null));
        return "/index.xhtml?faces-redirect=true";
    }
}
