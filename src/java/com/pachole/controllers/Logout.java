package com.pachole.controllers;
import javax.faces.context.FacesContext;

public class Logout {
    public String logout(){
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "/index.xhtml?faces-redirect=true";
    }
}
