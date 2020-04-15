package com.pachole.serviceDAO;

import com.pachole.entities.User;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
 
@Stateless
public class UserFacade extends AbstractFacade<User> {

    @PersistenceContext(unitName = "MailSenderAPPPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UserFacade() {
        super(User.class);
    }
    
    public void add(User user) {
        getEntityManager().persist(user);
    }
    
    public void delete(User user) {
        getEntityManager().remove(getEntityManager().merge(user));  
    }
    
    public User checkIfExists(String username, String email) {
        User result;
        try {
            result = getEntityManager().createNamedQuery("User.findByLoginCredentials", User.class).setParameter("email", email).setParameter("username", username).getSingleResult();
        } catch (Exception e) {
            return null;
        }
        return result;
    }
    
    public User userLogin(String userMail, String password) {
        User result;
        try{
            result = getEntityManager().createNamedQuery("User.findByUserMailAndPassword", User.class).setParameter("userMail", userMail).setParameter("password", password).getSingleResult();
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
        return result;
    }
    
}
