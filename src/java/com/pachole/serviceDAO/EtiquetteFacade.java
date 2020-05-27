/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pachole.serviceDAO;

import com.pachole.entities.Client;
import com.pachole.entities.Etiquette;
import com.pachole.entities.Mail;
import com.pachole.entities.User;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author marci
 */
@Stateless
public class EtiquetteFacade extends AbstractFacade<Etiquette> {

    @PersistenceContext(unitName = "MailSenderAPPPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EtiquetteFacade() {
        super(Etiquette.class);
    }

    @Override
    public void create(Etiquette etiquette) {
        getEntityManager().persist(etiquette);
    }

    @Override
    public void remove(Etiquette etiquette) {
        getEntityManager().remove(getEntityManager().merge(etiquette));
    }

    public Etiquette findByName(String name) {
        Etiquette result;
        try {
            result = getEntityManager().createNamedQuery("Etiquette.findByName", Etiquette.class).setParameter("name", name).getSingleResult();
        } catch (Exception e) {
            return null;
        }
        return result;
    }
    
    public List<Etiquette> findByEtiquetteName(List<String> name) {
        List<Etiquette> result = new ArrayList<Etiquette>();
        try {
            for (int i = 0; i < name.size(); i++) {
                result.addAll(getEntityManager().createNamedQuery("Etiquette.findByName", Etiquette.class).setParameter("name", name.get(i)).getResultList());
            }
        } catch (Exception e) {
            return null;
        }
        return result;
    }

    public List<Etiquette> getAllEtiquettes(User userId) {
        List<Etiquette> result;
        try {
            result = getEntityManager().createNamedQuery("Etiquette.findAllByUserId", Etiquette.class).setParameter("idUser", userId).getResultList();
        } catch (Exception e) {
            return null;
        }
        return result;
    }

    public List<Etiquette> getEtiquetteName(User userId) {
        List<Etiquette> result;
        try {
            result = getEntityManager().createNamedQuery("Etiquette.findAllNames", Etiquette.class).setParameter("idUser", userId).getResultList();
        } catch (Exception e) {
            return null;
        }
        return result;
    }

    public void updateEtiquetteMailCollection(Etiquette etiquette) {
        Etiquette result = null;
        try {
            result = getEntityManager().merge(etiquette);
        } catch (Exception e) {
            throw new Error(e);
        }
    }

}
