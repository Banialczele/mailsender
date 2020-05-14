/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pachole.serviceDAO;

import com.pachole.entities.Mailstatus;
import com.pachole.entities.User;
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

    public List<Mailstatus> getMailAndClientEmail(User loggedUser) {
        List<Mailstatus> result;
        try {
            result = getEntityManager().createQuery("SELECT s FROM Mailstatus s WHERE s.idMail IN ( SELECT m FROM Mail m WHERE m.idUser = :idUser ) ", Mailstatus.class).setParameter("idUser", loggedUser).getResultList();
        } catch (Exception e) {
            throw new Error(e);
        }
        return result;
    }
}
