/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pachole.serviceDAO;

import com.pachole.entities.User;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author marci
 */
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
    
    public User checkIfExists(String username, String email){
        User result;
        try {
            result = getEntityManager().createNamedQuery("User.findByUsernameEmail", User.class).setParameter("username", username).setParameter("userMail", email).getSingleResult();
        } catch(Exception e){
            return null;
        }
        return result;
    }
    
    @Override
    public void create(User user){
        try{
            getEntityManager().persist(user);
        }catch(Exception e){
            throw new Error(e);
        }
    }
    
    public User validateCredentials(String email, String password){
        User result;
        try {
            result = getEntityManager().createNamedQuery("User.findByLoginCredentials", User.class).setParameter("userMail", email).setParameter("password", password).getSingleResult();
        }catch(Exception e){
            return null;
        }
        return result;
    }
    
    public User checkIfEmailInUse(String email){
        User result;
        try{
            result = getEntityManager().createNamedQuery("User.findByUserMail", User.class).setParameter("userMail", email).getSingleResult();
        } catch(Exception e){
            return null;
        }
        return result;
    }
}