package com.pachole.serviceDAO;

import com.pachole.entities.Client;
import com.pachole.entities.User;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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

    public void add(Client client) {
        getEntityManager().persist(client);
    }

    @Override
    public void remove(Client client) {
        getEntityManager().remove(getEntityManager().merge(client));
    }

    public List<String> findEmailsById(User user) {
        List<Client> result = getEntityManager().createNamedQuery("Client.findEmailsByUserId", Client.class).setParameter("userid", user).getResultList();

//        List<String> clientList = result.stream().map(res -> Objects.toString(res, null)).collect(Collectors.toList());
        List<String> clientList = new ArrayList<>(result.size());
        for(Object object : result){
            clientList.add(Objects.toString(object, null));
        }
        return clientList;
    }
    
    public List<Client> findAllById(User user){
        List<Client> result = getEntityManager().createNamedQuery("Client.findAllByUserId", Client.class).setParameter("userid", user).getResultList();
        
        return result;
    }

}
