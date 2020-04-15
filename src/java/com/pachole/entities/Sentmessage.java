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
import javax.persistence.Lob;
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
@Table(name = "sentmessage")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Sentmessage.findAll", query = "SELECT s FROM Sentmessage s")
    , @NamedQuery(name = "Sentmessage.findByIdSentMessage", query = "SELECT s FROM Sentmessage s WHERE s.idSentMessage = :idSentMessage")
    , @NamedQuery(name = "Sentmessage.findBySubject", query = "SELECT s FROM Sentmessage s WHERE s.subject = :subject")
    , @NamedQuery(name = "Sentmessage.findByMessageTopic", query = "SELECT s FROM Sentmessage s WHERE s.messageTopic = :messageTopic")
    , @NamedQuery(name = "Sentmessage.findByDate", query = "SELECT s FROM Sentmessage s WHERE s.date = :date")
    , @NamedQuery(name = "Sentmessage.findByAuthorName", query = "SELECT s FROM Sentmessage s WHERE s.authorName = :authorName")})
public class Sentmessage implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idSentMessage")
    private Integer idSentMessage;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "Subject")
    private String subject;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 2147483647)
    @Column(name = "MessageContent")
    private String messageContent;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "MessageTopic")
    private String messageTopic;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "Date")
    private String date;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "AuthorName")
    private String authorName;
    @JoinColumn(name = "MessageStatus_id", referencedColumnName = "idMessageStatus")
    @ManyToOne(optional = false)
    private Messagestatus messageStatusid;
    @JoinColumn(name = "User_id", referencedColumnName = "idUser")
    @ManyToOne(optional = false)
    private User userid;

    public Sentmessage() {
    }

    public Sentmessage(Integer idSentMessage) {
        this.idSentMessage = idSentMessage;
    }

    public Sentmessage(Integer idSentMessage, String subject, String messageContent, String messageTopic, String date, String authorName) {
        this.idSentMessage = idSentMessage;
        this.subject = subject;
        this.messageContent = messageContent;
        this.messageTopic = messageTopic;
        this.date = date;
        this.authorName = authorName;
    }

    public Integer getIdSentMessage() {
        return idSentMessage;
    }

    public void setIdSentMessage(Integer idSentMessage) {
        this.idSentMessage = idSentMessage;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
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

    public Messagestatus getMessageStatusid() {
        return messageStatusid;
    }

    public void setMessageStatusid(Messagestatus messageStatusid) {
        this.messageStatusid = messageStatusid;
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
        hash += (idSentMessage != null ? idSentMessage.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Sentmessage)) {
            return false;
        }
        Sentmessage other = (Sentmessage) object;
        if ((this.idSentMessage == null && other.idSentMessage != null) || (this.idSentMessage != null && !this.idSentMessage.equals(other.idSentMessage))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pachole.entities.Sentmessage[ idSentMessage=" + idSentMessage + " ]";
    }
    
}
