/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package waggr;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author URI
 */
@Stateless
@LocalBean
public class WaggrSession implements WaggrSessionRemote,WaggrSessionLocal {
    @EJB
    WeatherBeanLocal weatherBean;
    @PersistenceContext(unitName = "Waggr-ejbPU2")
    private EntityManager em;

    
    @Override
    public String getPasswordByUserName(String userName) {
        Query query = em.createNamedQuery("Users.findByLogin");
        query.setParameter("login", userName);
        List users = query.getResultList();
        
        if(users.size()>0) {
            return ((Users)users.get(0)).getPassword();
        } else {
            return null;
        }
    }
    
    @Override
    public void addNewUser(String userName, String userSurname, String userLogin, String userPassword, String userCity, String userCountry) {
        Users user = new Users();
        user.setName(userName);
        user.setSurname(userSurname);
        user.setLogin(userLogin);
        user.setPassword(userPassword);
        user.setCityName(userCity);
        user.setCountryName(userCountry);
        em.persist(user);
    }
    
    @Override
    public boolean doAuthorization(String userLogin, String userPassword) {
        Query query = em.createNamedQuery("Users.findByLoginAndPassword");
        query.setParameter("login", userLogin);
        query.setParameter("password", userPassword);
        List users = query.getResultList();
        if(users.size()>0) {
            return true;
        } else {
            return false;
        }
    }
    
    @Override
    public boolean checkUserExists(String userLogin) {
        Query query = em.createNamedQuery("Users.findByLogin");
        query.setParameter("login", userLogin);
        List users = query.getResultList();
        if(users.size()>0) {
            return true;
        } else {
            return false;
        }
    }
    
    @Override
    public boolean checkCityAndCountryExists(String cityName, String countryName) {
        if (weatherBean.getCurrentWUA(cityName, countryName)==null&&weatherBean.getCurrentYan(cityName, countryName)==null){
            return false;
        } else {
            return true;
        }
    }
    
    @Override
    public boolean changeUserCityAndCountry(String userLogin,String cityName,String countryName) {
        Query query = em.createNamedQuery("Users.findByLogin");
        query.setParameter("login", userLogin);
        List users = query.getResultList();
        if(users.size()>0) {
            Users u = ((Users)users.get(0));
            u.setCityName(cityName);
            u.setCountryName(countryName);
            em.persist(u);
            return true;
        } else {
            return false;
        }
    }
    
    @Override
    public String getUserCurrentCityName(String userLogin) {
        Query query = em.createNamedQuery("Users.findByLogin");
        query.setParameter("login", userLogin);
        List users = query.getResultList();
        if(users.size()>0) {
            return ((Users)users.get(0)).getCityName();
        } else {
            return null;
        }
    }
    
    @Override
    public String getUserCurrentCountryName(String userLogin) {
        Query query = em.createNamedQuery("Users.findByLogin");
        query.setParameter("login", userLogin);
        List users = query.getResultList();
        if(users.size()>0) {
            return ((Users)users.get(0)).getCountryName();
        } else {
            return null;
        }        
    }

    public void persist(Object object) {
        em.persist(object);
    }

   
}
