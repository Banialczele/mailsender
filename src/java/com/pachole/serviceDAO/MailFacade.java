/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pachole.serviceDAO;

import com.pachole.entities.Mail;
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
    
    public List<Mail> findByLoggedUser(User user){
        List<Mail> result; 
       try{
            result = getEntityManager().createNativeQuery("SELECT * FROM Mail WHERE idUser = "+user.getIdUser()+" GROUP BY BINARY messageTopic", Mail.class).getResultList();
        } catch(Exception e){
            throw new Error(e);
        }
        return result;
    }

}
