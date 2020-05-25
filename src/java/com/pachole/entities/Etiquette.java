/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pachole.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author marci
 */
@Entity
@Table(name = "etiquette")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Etiquette.findAll", query = "SELECT e FROM Etiquette e")
    , @NamedQuery(name = "Etiquette.findByIdGroup", query = "SELECT e FROM Etiquette e WHERE e.idGroup = :idGroup")
    , @NamedQuery(name = "Etiquette.findByName", query = "SELECT e FROM Etiquette e WHERE e.name = :name")
    , @NamedQuery(name = "Etiquette.findByArchive", query = "SELECT e FROM Etiquette e WHERE e.archive = :archive")
    , @NamedQuery(name = "Etiquette.findAllByUserId", query = "SELECT e FROM Etiquette e WHERE e.idUser = :idUser")
    , @NamedQuery(name = "Etiquette.findAllNamesByUser", query = "SELECT e.name FROM Etiquette e WHERE e.idUser = :idUser")
        , @NamedQuery(name = "Etiquette.findAllNames", query = "SELECT e.name FROM Etiquette e WHERE e.idUser = :idUser")
})
public class Etiquette implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idGroup")
    private Integer idGroup;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @Column(name = "archive")
    private String archive;
    @JoinTable(name = "mail_has_etiquette", joinColumns = {
        @JoinColumn(name = "idGroup", referencedColumnName = "idGroup")}, inverseJoinColumns = {
        @JoinColumn(name = "idMail", referencedColumnName = "idMail")})
    @ManyToMany()
    private Collection<Mail> mailCollection;
    @ManyToMany(mappedBy = "etiquetteCollection", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Collection<Client> clientCollection = new ArrayList<>();
    @JoinColumn(name = "idUser", referencedColumnName = "idUser")
    @ManyToOne(optional = false)
    private User idUser;

    public Etiquette() {
    }

    public Etiquette(Integer idGroup) {
        this.idGroup = idGroup;
    }

    public Etiquette(Integer idGroup, String name, String archive) {
        this.idGroup = idGroup;
        this.name = name;
        this.archive = archive;
    }

    public Integer getIdGroup() {
        return idGroup;
    }

    public void setIdGroup(Integer idGroup) {
        this.idGroup = idGroup;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArchive() {
        return archive;
    }

    public void setArchive(String archive) {
        this.archive = archive;
    }

    @XmlTransient
    public Collection<Mail> getMailCollection() {
        return mailCollection;
    }

    public void setMailCollection(Collection<Mail> mailCollection) {
        this.mailCollection = mailCollection;
    }

    @XmlTransient
    public Collection<Client> getClientCollection() {
        return clientCollection;
    }

    public void setClientCollection(Collection<Client> clientCollection) {
        this.clientCollection = clientCollection;
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
        hash += (idGroup != null ? idGroup.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Etiquette)) {
            return false;
        }
        Etiquette other = (Etiquette) object;
        if ((this.idGroup == null && other.idGroup != null) || (this.idGroup != null && !this.idGroup.equals(other.idGroup))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pachole.entities.Etiquette[ idGroup=" + idGroup + " ]";
    }

}
