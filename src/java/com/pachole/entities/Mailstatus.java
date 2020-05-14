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
import javax.persistence.FetchType;
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
@Table(name = "mailstatus")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Mailstatus.findAll", query = "SELECT m FROM Mailstatus m")
    , @NamedQuery(name = "Mailstatus.findByIdMailStatus", query = "SELECT m FROM Mailstatus m WHERE m.idMailStatus = :idMailStatus")
    , @NamedQuery(name = "Mailstatus.findByStatus", query = "SELECT m FROM Mailstatus m WHERE m.status = :status")
    , @NamedQuery(name = "Mailstatus.findByDate", query = "SELECT m FROM Mailstatus m WHERE m.date = :date")
    , @NamedQuery(name = "Mailstatus.findByMailStatus", query = "SELECT m FROM Mailstatus m WHERE m.mailStatus = :mailStatus")})
public class Mailstatus implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idMailStatus")
    private Integer idMailStatus;
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
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Client idClient;
    @JoinColumn(name = "idMailAccounts", referencedColumnName = "idMailAccounts")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Mailaccount idMailAccounts;
    @JoinColumn(name = "idMail", referencedColumnName = "idMail")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Mail idMail;

    public Mailstatus() {
    }

    public Mailstatus(Integer idMailStatus) {
        this.idMailStatus = idMailStatus;
    }

    public Mailstatus(Integer idMailStatus, String status, String date, String mailStatus) {
        this.idMailStatus = idMailStatus;
        this.status = status;
        this.date = date;
        this.mailStatus = mailStatus;
    }

    public Integer idMailStatus() {
        return idMailStatus;
    }

    public void idMailStatus(Integer idMailStatus) {
        this.idMailStatus = idMailStatus;
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
        hash += (idMailStatus != null ? idMailStatus.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Mailstatus)) {
            return false;
        }
        Mailstatus other = (Mailstatus) object;
        if ((this.idMailStatus == null && other.idMailStatus != null) || (this.idMailStatus != null && !this.idMailStatus.equals(other.idMailStatus))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pachole.entities.Mailstatus[ idMailStatus=" + idMailStatus + " ]";
    }
    
}
