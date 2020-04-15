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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author marci
 */
@Entity
@Table(name = "messagestatus")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Messagestatus.findAll", query = "SELECT m FROM Messagestatus m")
    , @NamedQuery(name = "Messagestatus.findByIdMessageStatus", query = "SELECT m FROM Messagestatus m WHERE m.idMessageStatus = :idMessageStatus")
    , @NamedQuery(name = "Messagestatus.findByMessageIsDelivered", query = "SELECT m FROM Messagestatus m WHERE m.messageIsDelivered = :messageIsDelivered")
    , @NamedQuery(name = "Messagestatus.findByMessageIsSent", query = "SELECT m FROM Messagestatus m WHERE m.messageIsSent = :messageIsSent")})
public class Messagestatus implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idMessageStatus")
    private Integer idMessageStatus;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "MessageIsDelivered")
    private String messageIsDelivered;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "MessageIsSent")
    private String messageIsSent;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "messageStatusid")
    private Collection<Sentmessage> sentmessageCollection;

    public Messagestatus() {
    }

    public Messagestatus(Integer idMessageStatus) {
        this.idMessageStatus = idMessageStatus;
    }

    public Messagestatus(Integer idMessageStatus, String messageIsDelivered, String messageIsSent) {
        this.idMessageStatus = idMessageStatus;
        this.messageIsDelivered = messageIsDelivered;
        this.messageIsSent = messageIsSent;
    }

    public Integer getIdMessageStatus() {
        return idMessageStatus;
    }

    public void setIdMessageStatus(Integer idMessageStatus) {
        this.idMessageStatus = idMessageStatus;
    }

    public String getMessageIsDelivered() {
        return messageIsDelivered;
    }

    public void setMessageIsDelivered(String messageIsDelivered) {
        this.messageIsDelivered = messageIsDelivered;
    }

    public String getMessageIsSent() {
        return messageIsSent;
    }

    public void setMessageIsSent(String messageIsSent) {
        this.messageIsSent = messageIsSent;
    }

    @XmlTransient
    public Collection<Sentmessage> getSentmessageCollection() {
        return sentmessageCollection;
    }

    public void setSentmessageCollection(Collection<Sentmessage> sentmessageCollection) {
        this.sentmessageCollection = sentmessageCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idMessageStatus != null ? idMessageStatus.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Messagestatus)) {
            return false;
        }
        Messagestatus other = (Messagestatus) object;
        if ((this.idMessageStatus == null && other.idMessageStatus != null) || (this.idMessageStatus != null && !this.idMessageStatus.equals(other.idMessageStatus))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pachole.entities.Messagestatus[ idMessageStatus=" + idMessageStatus + " ]";
    }
    
}
