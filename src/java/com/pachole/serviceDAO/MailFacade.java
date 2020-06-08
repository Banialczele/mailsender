/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pachole.serviceDAO;

import com.pachole.entities.Mail;
import com.pachole.entities.User;
import java.util.ArrayList;
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
public class MailFacade extends AbstractFacade<Mail> {

    @PersistenceContext(unitName = "MailSenderAPPPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MailFacade() {
        super(Mail.class);
    }

    @Override
    public void create(Mail mail) {
        try {
            getEntityManager().persist(mail);
        } catch (Exception e) {
            throw new Error(e);
        }
    }

    public List<Mail> filterMails(String author, Date date, String topic, User user) {
        List<Mail> result;        
        try {
            result = getEntityManager().createQuery("SELECT m FROM Mail m WHERE ((m.authorName = :author IS NULL OR m.authorName LIKE CONCAT('%', :author, '%')) AND (m.date = :date IS NULL OR m.date LIKE CONCAT('%', :date, '%')) AND (m.messageTopic = :topic IS NULL OR m.messageTopic LIKE CONCAT('%', :topic, '%')) AND (m.idUser = :idUser IS NULL OR m.idUser LIKE CONCAT('%', :idUser, '%')))", Mail.class)
                    .setParameter("author", author)
                    .setParameter("date", date)
                    .setParameter("topic", topic)
                    .setParameter("idUser", user)
                    .getResultList();
        } catch (Exception e) {
            return null;
        }
        return result;
    }
    
    public List<Mail> filterMailsWithoutDate(String author, String topic, User user) {
        List<Mail> result;
        
        try {
            result = getEntityManager().createQuery("SELECT m FROM Mail m WHERE ( ( :author IS NULL OR m.authorName LIKE CONCAT('%', :author, '%')) AND ( :topic IS NULL OR m.messageTopic LIKE CONCAT('%', :topic, '%')) AND ( m.idUser =:idUser ) )", Mail.class)
                    .setParameter("author", author)
                    .setParameter("topic", topic)
                    .setParameter("idUser", user)
                    .getResultList();
        } catch (Exception e) {
            throw new Error(e);
        }
        return result;
    }

    public List<Mail> findByLoggedUser(User user) {
        List<Mail> result;
        try {
            result = getEntityManager().createNativeQuery("SELECT DISTINCT * FROM Mail WHERE idUser = " + user.getIdUser() + " GROUP BY messageTopic", Mail.class).getResultList();
        } catch (Exception e) {
            throw new Error(e);
        }
        return result;
    }

}
