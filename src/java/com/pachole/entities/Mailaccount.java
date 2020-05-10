/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pachole.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author marci
 */
@Entity
@Table(name = "mailaccount")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Mailaccount.findAll", query = "SELECT m FROM Mailaccount m")
    , @NamedQuery(name = "Mailaccount.findByIdMailAccounts", query = "SELECT m FROM Mailaccount m WHERE m.idMailAccounts = :idMailAccounts")
    , @NamedQuery(name = "Mailaccount.findByMailAddress", query = "SELECT m FROM Mailaccount m WHERE m.mailAddress = :mailAddress")
    , @NamedQuery(name = "Mailaccount.findByDailyLimit", query = "SELECT m FROM Mailaccount m WHERE m.dailyLimit = :dailyLimit")
    , @NamedQuery(name = "Mailaccount.findByPassword", query = "SELECT m FROM Mailaccount m WHERE m.password = :password")})
public class Mailaccount implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idMailAccounts")
    private Integer idMailAccounts;
    @Basic(optional = false)
    @Column(name = "mailAddress")
    private String mailAddress;
    @Basic(optional = false)
    @Column(name = "dailyLimit")
    private int dailyLimit;
    @Column(name = "password")
    private String password;
    @JoinColumn(name = "idUser", referencedColumnName = "idUser")
    @ManyToOne(optional = false)
    private User idUser;

    public Mailaccount() {
    }

    public Mailaccount(Integer idMailAccounts) {
        this.idMailAccounts = idMailAccounts;
    }

    public Mailaccount(Integer idMailAccounts, String mailAddress, int dailyLimit) {
        this.idMailAccounts = idMailAccounts;
        this.mailAddress = mailAddress;
        this.dailyLimit = dailyLimit;
    }

    public Integer getIdMailAccounts() {
        return idMailAccounts;
    }

    public void setIdMailAccounts(Integer idMailAccounts) {
        this.idMailAccounts = idMailAccounts;
    }

    public String getMailAddress() {
        return mailAddress;
    }

    public void setMailAddress(String mailAddress) {
        this.mailAddress = mailAddress;
    }

    public int getDailyLimit() {
        return dailyLimit;
    }

    public void setDailyLimit(int dailyLimit) {
        this.dailyLimit = dailyLimit;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User getIdUser() {
        return idUser;
    }

    public void setIdUser(User idUser) {
        this.idUser = idUser;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idMailAccounts != null ? idMailAccounts.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Mailaccount)) {
            return false;
        }
        Mailaccount other = (Mailaccount) object;
        if ((this.idMailAccounts == null && other.idMailAccounts != null) || (this.idMailAccounts != null && !this.idMailAccounts.equals(other.idMailAccounts))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pachole.entities.Mailaccount[ idMailAccounts=" + idMailAccounts + " ]";
    }
    
}
