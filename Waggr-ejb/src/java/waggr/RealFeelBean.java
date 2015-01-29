/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package waggr;

import java.sql.Timestamp;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.inject.Inject;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author URI
 */
@Stateless
@LocalBean
public class RealFeelBean implements RealFeelBeanRemote {
    @EJB
    WeatherBeanLocal weatherBeanLocal;
    
    @PersistenceContext(unitName = "Waggr-ejbPU2")
    private EntityManager em;
    private final int tempRange = 5;
    private final int presRange = 20;
    private final int humRange = 30;
    private final int windRange = 10;
    private Weatherwua currentWeatherWUA;
    private Weatheryan currentWeatherYandex;
    
    public boolean newRealFeel(String login,String cityName, String countryName, int temperature,int pressure,int humidity,float windSpeed) {
        currentWeatherWUA = weatherBeanLocal.getCurrentWUA(cityName, countryName);
        currentWeatherYandex = weatherBeanLocal.getCurrentYan(cityName, countryName);
        if (currentWeatherWUA==null&&currentWeatherYandex==null) {
            System.out.println("wuayan NULL");
            return false;
        } else {
            if (currentWeatherYandex==null) {
                currentWeatherYandex = new Weatheryan();
                currentWeatherYandex.setTemperature(currentWeatherWUA.getTemperature());
                currentWeatherYandex.setPressure(currentWeatherWUA.getPressure());
                currentWeatherYandex.setHumidity(currentWeatherWUA.getHumidity());
                currentWeatherYandex.setWindSpeed(currentWeatherWUA.getWindSpeed());
            } else if (currentWeatherWUA==null){
                currentWeatherWUA = new Weatherwua();
                currentWeatherWUA.setTemperature(currentWeatherYandex.getTemperature());
                currentWeatherWUA.setPressure(currentWeatherYandex.getPressure());
                currentWeatherWUA.setHumidity(currentWeatherYandex.getHumidity());
                currentWeatherWUA.setWindSpeed(currentWeatherYandex.getWindSpeed());
            }
        }
        Realfeel newRealFeel = new Realfeel();
        newRealFeel.setCityName(cityName);
        newRealFeel.setCountryName(countryName);
        newRealFeel.setLogin(login);
        newRealFeel.setTemperature(temperature);
        newRealFeel.setPressure(pressure);
        newRealFeel.setHumidity(humidity);
        newRealFeel.setWindSpeed(Double.valueOf(windSpeed));
        newRealFeel.setWindDirection("N/A");
        newRealFeel.setTimestamp(new Timestamp(new java.util.Date().getTime()));
        if (checkTemperature(newRealFeel) && checkPressure(newRealFeel) && checkHumidity(newRealFeel) && checkWindSpeed(newRealFeel)) {
            em.persist(newRealFeel);
            return true;
        }
        return false;
    }
    private boolean checkTemperature(Realfeel realFeel) {
        int currentTemperatureYandex = currentWeatherYandex.getTemperature();
        int currentTemperatureWUA = currentWeatherWUA.getTemperature();
        if (((currentTemperatureYandex - tempRange)<=realFeel.getTemperature())&&((currentTemperatureYandex + tempRange)>=realFeel.getTemperature())) {
            return true;
        } else if (((currentTemperatureWUA - tempRange)<=realFeel.getTemperature())&&((currentTemperatureWUA+ tempRange)>=realFeel.getTemperature())) {
            return true;
        } else {
            return false;
        }
    }

    private boolean checkPressure(Realfeel realFeel) {
        int currentPressureYandex = currentWeatherYandex.getPressure();
        int currentPressureWUA = currentWeatherWUA.getPressure();
        if (((currentPressureYandex - presRange)<=realFeel.getPressure())&&((currentPressureYandex + presRange)>=realFeel.getPressure())) {
            return true;
        } else if (((currentPressureWUA - presRange)<=realFeel.getPressure())&&((currentPressureWUA + presRange)>=realFeel.getPressure())) {
            return true;
        } else {
            return false;
        }
    }

    private boolean checkHumidity(Realfeel realFeel) {
        int currentHumidityYandex = currentWeatherYandex.getHumidity();
        int currentHumidityWUA = currentWeatherWUA.getHumidity();
        if (((currentHumidityYandex - humRange)<=realFeel.getHumidity())&&((currentHumidityYandex + humRange)>=realFeel.getHumidity())) {
            return true;
        } else if (((currentHumidityWUA - humRange)<=realFeel.getHumidity())&&((currentHumidityWUA + humRange)>=realFeel.getHumidity())) {
            return true;
        } else {
            return false;
        }
    }

    private boolean checkWindSpeed(Realfeel realFeel) {
        double currentWindSpeedYandex = currentWeatherYandex.getWindSpeed();
        double currentWindSpeedWUA = currentWeatherWUA.getWindSpeed();
        if (((currentWindSpeedYandex - windRange)<=realFeel.getWindSpeed())&&((currentWindSpeedYandex + windRange)>=realFeel.getWindSpeed())) {
            return true;
        } else if (((currentWindSpeedWUA - windRange)<=realFeel.getWindSpeed())&&((currentWindSpeedWUA + windRange)>=realFeel.getWindSpeed())) {
            return true;
        } else {
            return false;
        }
    }
//    private Weatherwua getCurrentWUA (String cityName, String countryName) {
//        Query query = em.createNamedQuery("Weatherwua.findCurrentWeather");
//        query.setParameter("cityName", cityName);
//        query.setParameter("countryName", countryName);
//        List weatherwua = query.getResultList();
//        if(weatherwua.size()>0) {
//            return (Weatherwua)weatherwua.get(0);
//        } else {
//            return null;
//        }
//    }
//    
//    private Weatheryan getCurrentYan (String cityName, String countryName) {
//        Query query = em.createNamedQuery("Weatheryan.findCurrentWeather");
//        query.setParameter("cityName", cityName);
//        query.setParameter("countryName", countryName);
//        List weatheryan = query.getResultList();
//        if(weatheryan.size()>0) {
//            return (Weatheryan)weatheryan.get(0);
//        } else {
//            return null;
//        }
//    }
    public void persist(Object object) {
        em.persist(object);
    }
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
