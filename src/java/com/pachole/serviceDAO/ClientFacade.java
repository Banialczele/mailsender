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

    @Override
    public void edit(Client client) {
        getEntityManager().merge(client);
    }

    public Client checkExistanceByEmail(String email, User user) {
        Client result;
        try {
            result = getEntityManager().createNamedQuery("Client.findByEmail", Client.class).setParameter("email", email).setParameter("idUser", user).getSingleResult();
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

    public List<Client> filterClientsByData(String name, String email, String status) {
        List<Client> result;
        try {
            result = getEntityManager().createQuery("SELECT c FROM Client c WHERE (( :name IS NULL OR c.name LIKE CONCAT('%',:name,'%')) AND ( :email IS NULL OR c.email LIKE CONCAT ('%',:email, '%')) AND ( :status IS NULL OR c.status LIKE CONCAT ('%',:status, '%')))", Client.class)
                    .setParameter("name", name)
                    .setParameter("email", email)
                    .setParameter("status", status)
                    .getResultList();
        } catch (Exception e) {
            throw new Error(e);
        }
        return result;
    }

    public List<Client> findClientsByEtiquette(String etiquetteName) {
        List<Client> result = new ArrayList<>();
        try {
            result.addAll(em.createQuery("SELECT c FROM Client c INNER JOIN c.etiquetteCollection x WHERE x.idGroup IN (SELECT e.idGroup FROM Etiquette e INNER JOIN e.clientCollection h WHERE e.name = :name)", Client.class).setParameter("name", etiquetteName).getResultList());

        } catch (Exception e) {
            throw new Error(e);
        }
        return result;
    }

    public List<Client> findClientEmailByEtiquetteName(List<String> etiquetteName) {
        List<Client> result = new ArrayList<>();
        try {
            for (int i = 0; i < etiquetteName.size(); ++i) {
                result.addAll(em.createQuery("SELECT c FROM Client c INNER JOIN c.etiquetteCollection x WHERE x.idGroup IN (SELECT e.idGroup FROM Etiquette e INNER JOIN e.clientCollection h WHERE e.name = :name)", Client.class).setParameter("name", etiquetteName.get(i)).getResultList());
            }
        } catch (Exception e) {
            throw new Error(e);
        }
        return result;
    }

    public List<Client> findClientsWithoutLabel(User user) {
        List<Client> result = new ArrayList<>();
        try {
            result.addAll(em.createQuery("SELECT c FROM Client c WHERE c.idClient NOT IN ( SELECT h.idClient FROM Etiquette e INNER JOIN e.clientCollection h ) AND c.idUser = :idUser").setParameter("idUser", user).getResultList());
        } catch (Exception e) {
            throw new Error(e);
        }
        return result;
    }

    public List<Client> findClientsWithLabel(User user) {
        List<Client> result = new ArrayList<>();
        try {
            result.addAll(em.createQuery("SELECT c FROM Client c WHERE c.idClient IN ( SELECT h.idClient FROM Etiquette e INNER JOIN e.clientCollection h ) AND c.idUser = :idUser").setParameter("idUser", user).getResultList());
        } catch (Exception e) {
            throw new Error(e);
        }
        return result;
    }
}
