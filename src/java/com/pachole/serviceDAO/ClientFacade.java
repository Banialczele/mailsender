/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pachole.serviceDAO;

import com.pachole.entities.Client;
import com.pachole.entities.Etiquette;
import com.pachole.entities.User;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

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

    public List<Client> findClientsEmailsByLoggedUser(User user) {
        List<Client> result;
        try {
            result = getEntityManager().createNamedQuery("Client.findEmailById", Client.class).setParameter("idUser", user).getResultList();
        } catch (Exception e) {
            return null;
        }
        return result;
    }

    public List<Client> findClientsByLoggedUser(User user) {
        List<Client> result;
        try {
            result = getEntityManager().createNamedQuery("Client.findByUserId", Client.class).setParameter("idUser", user).getResultList();
        } catch (Exception e) {
            return null;
        }
        return result;
    }

    public List<Client> findClientEmailByEtiquetteName(List<String> etiquetteName) {
        List<Client> result = new ArrayList<>();

        try {
            for (int i = 0; i < etiquetteName.size(); ++i) {
                result.addAll(em.createQuery("SELECT c.email FROM Client c INNER JOIN c.etiquetteCollection x WHERE x.idGroup IN (SELECT e.idGroup FROM Etiquette e INNER JOIN e.clientCollection h WHERE e.name = :name)", Client.class).setParameter("name", etiquetteName.get(i)).getResultList());
            }
        } catch (Exception e) {
            throw new Error(e);
        }
        return result;
    }

    public List<Client> findClientsByEtiquetteName(String etiquetteName) {
        List<Client> result = new ArrayList<>();

        try {
            result.addAll(em.createQuery("SELECT c FROM Client c INNER JOIN c.etiquetteCollection x WHERE x.idGroup IN (SELECT e.idGroup FROM Etiquette e INNER JOIN e.clientCollection h WHERE e.name = :name)", Client.class).setParameter("name", etiquetteName).getResultList());

        } catch (Exception e) {
            throw new Error(e);
        }
        return result;
    }
}
