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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
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
@Table(name = "mail")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Message.findAll", query = "SELECT m FROM Mail m")
    , @NamedQuery(name = "Message.findByIdMail", query = "SELECT m FROM Mail m WHERE m.idMail = :idMail")
    , @NamedQuery(name = "Message.findByMessageTopic", query = "SELECT m FROM Mail m WHERE m.messageTopic = :messageTopic")
    , @NamedQuery(name = "Message.findByDate", query = "SELECT m FROM Mail m WHERE m.date = :date")
    , @NamedQuery(name = "Message.findByAuthorName", query = "SELECT m FROM Mail m WHERE m.authorName = :authorName")
    , @NamedQuery(name = "Message.findByLoggedUser", query = "SELECT DISTINCT m FROM Mail m WHERE m.idUser = :idUser GROUP BY m.messageTopic")
})
public class Mail implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idMail")
    private Integer idMail;
    @Basic(optional = false)
    @Lob
    @Column(name = "receiver")
    private String receiver;
    @Basic(optional = false)
    @Lob
    @Column(name = "messageContent")
    private String messageContent;
    @Basic(optional = false)
    @Column(name = "messageTopic")
    private String messageTopic;
    @Basic(optional = false)
    @Column(name = "date")
    private String date;
    @Basic(optional = false)
    @Column(name = "authorName")
    private String authorName;
    @ManyToMany(mappedBy = "mailCollection")
    private Collection<Etiquette> etiquetteCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idMail")
    private Collection<Mailstatus> mailstatusCollection;
    @JoinColumn(name = "idUser", referencedColumnName = "idUser")
    @ManyToOne(optional = false)
    private User idUser;

    public Mail() {
    }

    public Mail(Integer idMail) {
        this.idMail = idMail;
    }

    public Mail(Integer idMail, String receiver, String messageContent, String messageTopic, String date, String authorName) {
        this.idMail = idMail;
        this.receiver = receiver;
        this.messageContent = messageContent;
        this.messageTopic = messageTopic;
        this.date = date;
        this.authorName = authorName;
    }

    public Integer getIdMail() {
        return idMail;
    }

    public void setIdMail(Integer idMail) {
        this.idMail = idMail;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public String getMessageTopic() {
        return messageTopic;
    }

    public void setMessageTopic(String messageTopic) {
        this.messageTopic = messageTopic;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    @XmlTransient
    public Collection<Etiquette> getEtiquetteCollection() {
        return etiquetteCollection;
    }

    public void setEtiquetteCollection(Collection<Etiquette> eitquetteCollection) {
        this.etiquetteCollection = eitquetteCollection;
    }

    @XmlTransient
    public Collection<Mailstatus> getMailstatusCollection() {
        return mailstatusCollection;
    }

    public void setMailstatusCollection(Collection<Mailstatus> mailstatusCollection) {
        this.mailstatusCollection = mailstatusCollection;
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
        hash += (idMail != null ? idMail.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Mail)) {
            return false;
        }
        Mail other = (Mail) object;
        if ((this.idMail == null && other.idMail != null) || (this.idMail != null && !this.idMail.equals(other.idMail))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pachole.entities.Message[ idMail=" + idMail + " ]";
    }

}
