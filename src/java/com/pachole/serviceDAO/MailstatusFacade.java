/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pachole.serviceDAO;

import com.pachole.entities.Mailstatus;
import com.pachole.entities.User;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author marci
 */
@Stateless
public class MailstatusFacade extends AbstractFacade<Mailstatus> {

    @PersistenceContext(unitName = "MailSenderAPPPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MailstatusFacade() {
        super(Mailstatus.class);
    }

    @Override
    public void create(Mailstatus status) {
        try {
            getEntityManager().persist(status);
        } catch (Exception e) {
            throw new Error(e);
        }
    }

    public void update(Mailstatus status) {
        try {
            getEntityManager().merge(status);
        } catch (Exception e) {
            throw new Error(e);
        }
    }

    public List<Mailstatus> getMessages(User user) {
        List<Mailstatus> result;
        try {
            result = getEntityManager().createQuery("SELECT s FROM Mailstatus s WHERE s.idMail IN ( SELECT m FROM Mail m WHERE m.idUser = :idUser)")
                    .setParameter("idUser", user)
                    .getResultList();
        } catch (Exception e) {
            throw new Error(e);
        }
        return result;
    }

    public List<Mailstatus> getMailAndClientEmail(User loggedUser) {
        List<Mailstatus> result;
        try {
            result = getEntityManager().createQuery("SELECT s FROM Mailstatus s WHERE s.idMail IN ( SELECT m FROM Mail m WHERE m.idUser = :idUser ) ", Mailstatus.class).setParameter("idUser", loggedUser).getResultList();
        } catch (Exception e) {
            throw new Error(e);
        }
        return result;
    }

    public List<Mailstatus> filterByParameters(String author, Date date, String receiver, String topic, String status) {

        List<Mailstatus> result;
        try {
            result = getEntityManager().createQuery("SELECT s FROM Mailstatus s WHERE (( :status IS NULL OR s.mailStatus = :status ) AND ( :author IS NULL OR s.idMail.authorName LIKE CONCAT ('%',:author, '%')) AND ( :topic IS NULL OR s.idMail.messageTopic LIKE CONCAT ('%',:topic, '%')) AND ( :receiver IS NULL OR s.idClient.email LIKE CONCAT ('%',:receiver, '%')))", Mailstatus.class)
                    .setParameter("status", status)
                    .setParameter("author", author)
                    .setParameter("topic", topic)
                    .setParameter("receiver", receiver)
                    .getResultList();
        } catch (Exception e) {
            throw new Error(e);
        }
        return result;
    }

}
