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
@Table(name = "mailaccounts")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Mailaccounts.findAll", query = "SELECT m FROM Mailaccounts m")
    , @NamedQuery(name = "Mailaccounts.findByIdMailAccounts", query = "SELECT m FROM Mailaccounts m WHERE m.idMailAccounts = :idMailAccounts")
    , @NamedQuery(name = "Mailaccounts.findByMailAddress", query = "SELECT m FROM Mailaccounts m WHERE m.mailAddress = :mailAddress")
    , @NamedQuery(name = "Mailaccounts.findByCompanyName", query = "SELECT m FROM Mailaccounts m WHERE m.companyName = :companyName")
    , @NamedQuery(name = "Mailaccounts.findByCompanyAddress", query = "SELECT m FROM Mailaccounts m WHERE m.companyAddress = :companyAddress")})
public class Mailaccounts implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idMailAccounts")
    private Integer idMailAccounts;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "MailAddress")
    private String mailAddress;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "CompanyName")
    private String companyName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "CompanyAddress")
    private String companyAddress;
    @JoinColumn(name = "Client_id", referencedColumnName = "idClient")
    @ManyToOne(optional = false)
    private Client clientid;
    @JoinColumn(name = "User_id", referencedColumnName = "idUser")
    @ManyToOne(optional = false)
    private User userid;

    public Mailaccounts() {
    }

    public Mailaccounts(Integer idMailAccounts) {
        this.idMailAccounts = idMailAccounts;
    }

    public Mailaccounts(Integer idMailAccounts, String mailAddress, String companyName, String companyAddress) {
        this.idMailAccounts = idMailAccounts;
        this.mailAddress = mailAddress;
        this.companyName = companyName;
        this.companyAddress = companyAddress;
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

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public Client getClientid() {
        return clientid;
    }

    public void setClientid(Client clientid) {
        this.clientid = clientid;
    }

    public User getUserid() {
        return userid;
    }

    public void setUserid(User userid) {
        this.userid = userid;
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
        if (!(object instanceof Mailaccounts)) {
            return false;
        }
        Mailaccounts other = (Mailaccounts) object;
        if ((this.idMailAccounts == null && other.idMailAccounts != null) || (this.idMailAccounts != null && !this.idMailAccounts.equals(other.idMailAccounts))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pachole.entities.Mailaccounts[ idMailAccounts=" + idMailAccounts + " ]";
    }
    
}
