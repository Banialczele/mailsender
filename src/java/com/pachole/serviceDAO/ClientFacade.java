/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pachole.serviceDAO;

import com.pachole.entities.Client;
import com.pachole.entities.Etiquette;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author marci
 */
@Stateless
public class ClientFacade extends AbstractFacade<Client> {

    @PersistenceContext(unitName = "MailSenderAPPPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ClientFacade() {
        super(Client.class);
    }

    @Override
    public void create(Client client) {
        try {            
            getEntityManager().persist(client);
        } catch (Exception e) {
            throw new Error(e);
        }
    }
    
    public void updateEtiquette(List<Client> clientList, int id){
        Etiquette update;
        try{
            update = getEntityManager().createNamedQuery("Etiquette.findByIdGroup", Etiquette.class).setParameter("idGroup", id).getSingleResult();
            update.setClientCollection(clientList);
        } catch(Exception e){
            throw new Error(e);
        }
    }
}
