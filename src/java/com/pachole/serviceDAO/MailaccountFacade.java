/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pachole.serviceDAO;

import com.pachole.entities.Mailaccount;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author marci
 */
@Stateless
public class MailaccountFacade extends AbstractFacade<Mailaccount> {

    @PersistenceContext(unitName = "MailSenderAPPPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MailaccountFacade() {
        super(Mailaccount.class);
    }
    
}
