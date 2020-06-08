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

    public void updateEtiquette(Etiquette etiquette, String name, boolean status) {
        int value = status ? 1 : 0;
        if (etiquette.getArchive() == value && !name.isEmpty()) {
            etiquette.setName(name);
        } else if (etiquette.getArchive() != value && !name.isEmpty()) {
            etiquette.setArchive(value);
            etiquette.setName(name);
        } else if (etiquette.getArchive() != value && name.isEmpty()) {
            etiquette.setArchive(value);
        }
        getEntityManager().merge(etiquette);
    }

    public List<Etiquette> findListBySubstring(String name) {
        List<Etiquette> result;
        try {
            result = getEntityManager().createQuery("SELECT e FROM Etiquette e WHERE (lower(e.name) LIKE CONCAT('%',:name,'%'))", Etiquette.class).setParameter("name", name).getResultList();
        } catch (Exception e) {
            return null;
        }
        return result;
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
