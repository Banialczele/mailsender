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
@Table(name = "mailstatus")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Mailstatus.findAll", query = "SELECT m FROM Mailstatus m")
    , @NamedQuery(name = "Mailstatus.findByIdMessageHistory", query = "SELECT m FROM Mailstatus m WHERE m.idMessageHistory = :idMessageHistory")
    , @NamedQuery(name = "Mailstatus.findByStatus", query = "SELECT m FROM Mailstatus m WHERE m.status = :status")
    , @NamedQuery(name = "Mailstatus.findByDate", query = "SELECT m FROM Mailstatus m WHERE m.date = :date")
    , @NamedQuery(name = "Mailstatus.findByMailStatus", query = "SELECT m FROM Mailstatus m WHERE m.mailStatus = :mailStatus")})
public class Mailstatus implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "idMessageHistory")
    private Integer idMessageHistory;
    @Basic(optional = false)
    @Column(name = "status")
    private String status;
    @Basic(optional = false)
    @Column(name = "date")
    private String date;
    @Basic(optional = false)
    @Column(name = "mailStatus")
    private String mailStatus;
    @JoinColumn(name = "idClient", referencedColumnName = "idClient")
    @ManyToOne(optional = false)
    private Client idClient;
    @JoinColumn(name = "idMailAccounts", referencedColumnName = "idMailAccounts")
    @ManyToOne(optional = false)
    private Mailaccount idMailAccounts;
    @JoinColumn(name = "idMail", referencedColumnName = "idMail")
    @ManyToOne(optional = false)
    private Mail idMail;

    public Mailstatus() {
    }

    public Mailstatus(Integer idMessageHistory) {
        this.idMessageHistory = idMessageHistory;
    }

    public Mailstatus(Integer idMessageHistory, String status, String date, String mailStatus) {
        this.idMessageHistory = idMessageHistory;
        this.status = status;
        this.date = date;
        this.mailStatus = mailStatus;
    }

    public Integer getIdMessageHistory() {
        return idMessageHistory;
    }

    public void setIdMessageHistory(Integer idMessageHistory) {
        this.idMessageHistory = idMessageHistory;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMailStatus() {
        return mailStatus;
    }

    public void setMailStatus(String mailStatus) {
        this.mailStatus = mailStatus;
    }

    public Client getIdClient() {
        return idClient;
    }

    public void setIdClient(Client idClient) {
        this.idClient = idClient;
    }

    public Mailaccount getIdMailAccounts() {
        return idMailAccounts;
    }

    public void setIdMailAccounts(Mailaccount idMailAccounts) {
        this.idMailAccounts = idMailAccounts;
    }

    public Mail getIdMail() {
        return idMail;
    }

    public void setIdMail(Mail idMail) {
        this.idMail = idMail;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idMessageHistory != null ? idMessageHistory.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Mailstatus)) {
            return false;
        }
        Mailstatus other = (Mailstatus) object;
        if ((this.idMessageHistory == null && other.idMessageHistory != null) || (this.idMessageHistory != null && !this.idMessageHistory.equals(other.idMessageHistory))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pachole.entities.Mailstatus[ idMessageHistory=" + idMessageHistory + " ]";
    }
    
}
