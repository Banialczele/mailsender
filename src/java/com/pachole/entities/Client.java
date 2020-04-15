/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pachole.entities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author marci
 */
@Entity
@Table(name = "client")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Client.findAll", query = "SELECT c FROM Client c")
    , @NamedQuery(name = "Client.findByIdClient", query = "SELECT c FROM Client c WHERE c.idClient = :idClient")
    , @NamedQuery(name = "Client.findByName", query = "SELECT c FROM Client c WHERE c.name = :name")
    , @NamedQuery(name = "Client.findByCompanyName", query = "SELECT c FROM Client c WHERE c.companyName = :companyName")
    , @NamedQuery(name = "Client.findByWebsite", query = "SELECT c FROM Client c WHERE c.website = :website")
    , @NamedQuery(name = "Client.findByClientEmail", query = "SELECT c FROM Client c WHERE c.clientEmail = :clientEmail")
    , @NamedQuery(name = "Client.findAllByUserId", query = "SELECT c FROM Client c WHERE c.userid = :userid")
    , @NamedQuery(name = "Client.findEmailsByUserId", query = "SELECT c.clientEmail FROM Client c WHERE c.userid = :userid")

})
public class Client implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idClient")
    private Integer idClient;
    @Basic(optional = false)
    @Column(name = "Name", nullable = false)
    private String name;
    @Basic(optional = false)
    @Column(name = "CompanyName", nullable = false)
    private String companyName;
    @Basic(optional = false)
    @Column(name = "Website", nullable = false)
    private String website;
    @Basic(optional = false)
    @Column(name = "clientEmail", nullable = false)
    private String clientEmail;
    @JoinColumn(name = "User_id", referencedColumnName = "idUser")
    @ManyToOne(optional = false)
    private User userid;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "clientid")
    private Collection<Mailaccounts> mailaccountsCollection;

    public Client() {
    }

    public Client(Integer idClient) {
        this.idClient = idClient;
    }

    public Client(Integer idClient, String name, String companyName, String website, String clientEmail) {
        this.idClient = idClient;
        this.name = name;
        this.companyName = companyName;
        this.website = website;
        this.clientEmail = clientEmail;
    }

    public Integer getIdClient() {
        return idClient;
    }

    public void setIdClient(Integer idClient) {
        this.idClient = idClient;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getClientEmail() {
        return clientEmail;
    }

    public void setClientEmail(String clientEmail) {
        this.clientEmail = clientEmail;
    }

    public User getUserid() {
        return userid;
    }

    public void setUserid(User userid) {
        this.userid = userid;
    }

    @XmlTransient
    public Collection<Mailaccounts> getMailaccountsCollection() {
        return mailaccountsCollection;
    }

    public void setMailaccountsCollection(Collection<Mailaccounts> mailaccountsCollection) {
        this.mailaccountsCollection = mailaccountsCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idClient != null ? idClient.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Client)) {
            return false;
        }
        Client other = (Client) object;
        if ((this.idClient == null && other.idClient != null) || (this.idClient != null && !this.idClient.equals(other.idClient))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pachole.entities.Client[ idClient=" + idClient + " ]";
    }

}
