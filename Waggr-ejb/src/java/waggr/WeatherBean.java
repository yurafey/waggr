/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package waggr;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author URI
 */
@Stateless
@LocalBean
public class WeatherBean implements WeatherBeanRemote,WeatherBeanLocal {
    @PersistenceContext(unitName = "Waggr-ejbPU2")
    private EntityManager em;
    private List<List<String>> cellsCurrent = new ArrayList();
    private List<List<String>> cellsYan = new ArrayList();
    private List<List<String>> cellsWUA = new ArrayList();
    private static DateFormatSymbols myDateFormatSymbols = new DateFormatSymbols(){
        @Override
        public String[] getMonths() {
            return new String[]{"января", "февраля", "марта", "апреля", "мая", "июня",
                    "июля", "августа", "сентября", "октября", "ноября", "декабря"};
        }

    };
    
    private List<Weatherwua> getWUA(String cityName, String countryName) {
        Query query = em.createNamedQuery("Weatherwua.findByCityAndCountryName");
        query.setParameter("cityName", cityName);
        query.setParameter("countryName", countryName);
        List weatherwua = query.getResultList();
        if(weatherwua.size()>0) {
            return weatherwua;
        } else {
            return null;
        }
    }
    
    private List<Weatheryan> getYan(String cityName, String countryName) {
        Query query = em.createNamedQuery("Weatheryan.findByCityAndCountryName");
        query.setParameter("cityName", cityName);
        query.setParameter("countryName", countryName);
        List weatheryan = query.getResultList();
        if(weatheryan.size()>0) {
            return weatheryan;
        } else {
            return null;
        }
    }
     
    public Weatherwua getCurrentWUA(String cityName, String countryName) {
        Query query = em.createNamedQuery("Weatherwua.findCurrentWeather");
        query.setParameter("cityName", cityName);
        query.setParameter("countryName", countryName);
        List weatherwua = query.getResultList();
        if(weatherwua.size()>0) {
            return (Weatherwua)weatherwua.get(0);
        } else {
            return null;
        }
    }
    
    public Weatheryan getCurrentYan(String cityName, String countryName) {
        Query query = em.createNamedQuery("Weatheryan.findCurrentWeather");
        query.setParameter("cityName", cityName);
        query.setParameter("countryName", countryName);
        List weatheryan = query.getResultList();
        if(weatheryan.size()>0) {
            return (Weatheryan)weatheryan.get(0);
        } else {
            return null;
        }
    }
    
    private Realfeel getRealFeel(String cityName, String countryName) {
        Query query = em.createNamedQuery("Realfeel.findByCityNameAndCountryName");
        query.setParameter("cityName", cityName);
        query.setParameter("countryName", countryName);
        List realFeel = query.getResultList();
        if(realFeel.size()>0) {
            return (Realfeel)realFeel.get(0);
        } else {
            return null;
        }
    }
    
    private void updateCurrentCells(String cityName, String countryName) {
        Weatheryan currentWeatherYandex = getCurrentYan(cityName,countryName);
        Weatherwua currentWeatherWUA = getCurrentWUA(cityName,countryName);
        Realfeel currentRealFeel = getRealFeel(cityName,countryName);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM HH:mm", myDateFormatSymbols );
        cellsCurrent.clear();
        cellsCurrent.add(Arrays.asList("Дата",String.valueOf((currentWeatherYandex!=null)?dateFormat.format(currentWeatherYandex.getTimestamp()):"N/A"), String.valueOf(((currentWeatherWUA!=null)?dateFormat.format(currentWeatherWUA.getTimestamp()):"N/A")), String.valueOf((currentRealFeel!=null? (dateFormat.format(currentRealFeel.getTimestamp())+" от "+currentRealFeel.getLogin()):"N/A"))));
        cellsCurrent.add(Arrays.asList("Температура",String.valueOf((currentWeatherYandex!=null)?currentWeatherYandex.getTemperature():"N/A"), String.valueOf(((currentWeatherWUA!=null)?currentWeatherWUA.getTemperature():"N/A")), String.valueOf((currentRealFeel!=null?(currentRealFeel.getTemperature()):"N/A"))));
        cellsCurrent.add(Arrays.asList("Влажность", String.valueOf((currentWeatherYandex!=null)?currentWeatherYandex.getHumidity():"N/A"), String.valueOf(((currentWeatherWUA!=null)?currentWeatherWUA.getHumidity():"N/A")),String.valueOf((currentRealFeel!=null?(currentRealFeel.getHumidity()):"N/A"))));
        cellsCurrent.add(Arrays.asList("Атмосферное давление",String.valueOf((currentWeatherYandex!=null)?currentWeatherYandex.getPressure():"N/A"), String.valueOf(((currentWeatherWUA!=null)?currentWeatherWUA.getPressure():"N/A")),String.valueOf((currentRealFeel!=null?(currentRealFeel.getPressure()):"N/A"))));
        cellsCurrent.add(Arrays.asList("Скорость ветра", String.valueOf((currentWeatherYandex!=null)?currentWeatherYandex.getWindSpeed():"N/A"), String.valueOf(((currentWeatherWUA!=null)?currentWeatherWUA.getWindSpeed():"N/A")),String.valueOf((currentRealFeel!=null?(currentRealFeel.getWindSpeed()):"N/A"))));
        cellsCurrent.add(Arrays.asList("Направление ветра", String.valueOf((currentWeatherYandex!=null)?currentWeatherYandex.getWindDirection():"N/A"), String.valueOf(((currentWeatherWUA!=null)?currentWeatherWUA.getWindDirection():"N/A")),String.valueOf((currentRealFeel!=null?(currentRealFeel.getWindDirection()):"N/A"))));
    }

    private List<List<String>> makeRowsYan(List<Weatheryan> forecastList){
        List<List<String>> resultList = new ArrayList<>();
         if (forecastList==null) {
            resultList.add(Arrays.asList("N/A","N/A","N/A","N/A","N/A","N/A"));
            return resultList;
        }
        if (forecastList.isEmpty()) resultList.add(Arrays.asList("N/A","N/A","N/A","N/A","N/A","N/A"));
        else {
            for (int i = 0; i < forecastList.size(); i++){
                Weatheryan forecastElement = forecastList.get(i);
                java.util.Date fdate = forecastElement.getTimestamp();
                Calendar cal = Calendar.getInstance();
                cal.setTime(fdate);
                String partOfDay = "";
                switch (cal.get(Calendar.HOUR_OF_DAY)){
                    case 3: partOfDay = "(ночь)";
                        break;
                    case 9: partOfDay = "(утро)";
                        break;
                    case 15: partOfDay = "(день)";
                        break;
                    case 21: partOfDay = "(вечер)";
                        break;
                    default:
                }
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM", myDateFormatSymbols );
                double windSpeed = new BigDecimal(forecastElement.getWindSpeed()).setScale(1, RoundingMode.UP).doubleValue();
                resultList.add(Arrays.asList(String.valueOf(dateFormat.format(fdate)+" "+partOfDay),String.valueOf(forecastElement.getTemperature()),
                        String.valueOf(forecastElement.getHumidity()),String.valueOf(forecastElement.getPressure()),String.valueOf(windSpeed),String.valueOf(forecastElement.getWindDirection())));
            }
        }
        return resultList;
    }
    
    private List<List<String>> makeRowsWUA(List<Weatherwua> forecastList){
        List<List<String>> resultList = new ArrayList<>();
        if (forecastList==null) {
            resultList.add(Arrays.asList("N/A","N/A","N/A","N/A","N/A","N/A"));
            return resultList;
        }
        if (forecastList.isEmpty()) resultList.add(Arrays.asList("N/A","N/A","N/A","N/A","N/A","N/A"));
        else {
            for (int i = 0; i < forecastList.size(); i++){
                Weatherwua forecastElement = forecastList.get(i);
                java.util.Date fdate = forecastElement.getTimestamp();
                Calendar cal = Calendar.getInstance();
                cal.setTime(fdate);
                String partOfDay = "";
                switch (cal.get(Calendar.HOUR_OF_DAY)){
                    case 3: partOfDay = "(ночь)";
                        break;
                    case 9: partOfDay = "(утро)";
                        break;
                    case 15: partOfDay = "(день)";
                        break;
                    case 21: partOfDay = "(вечер)";
                        break;
                    default:
                }
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM", myDateFormatSymbols );
                resultList.add(Arrays.asList(
                        String.valueOf(dateFormat.format(fdate)+" "+partOfDay),String.valueOf(forecastElement.getTemperature()),
                        String.valueOf(forecastElement.getHumidity()),String.valueOf(forecastElement.getPressure()),String.valueOf(forecastElement.getWindSpeed()),String.valueOf(forecastElement.getWindDirection())));
            }
        }
        return resultList;
    }
    
    private void updateWUATable(String cityName, String countryName) {
        List<Weatherwua> weatherWUAList = getWUA(cityName, countryName);
        cellsWUA.clear();
        cellsWUA.addAll(makeRowsWUA(weatherWUAList));
    }
    
    private void updateYanTable(String cityName, String countryName) {
        List<Weatheryan> weatherYanList = getYan(cityName, countryName);
        cellsYan.clear();
        cellsYan.addAll(makeRowsYan(weatherYanList));
    }   
    
    @Override
    public List<List<String>> getCurrentTable(String cityName, String countryName) {
        updateCurrentCells(cityName,countryName);
        return cellsCurrent;
    }
    
    @Override
    public List<List<String>> getYandexTable(String cityName, String countryName) {
        updateYanTable(cityName,countryName);
        return cellsYan;
    }
    
    @Override
    public List<List<String>> getWuaTable(String cityName, String countryName) {
        updateWUATable(cityName,countryName);
        return cellsWUA;
    }
    @Override
    public List<List<String>> getCurrentTableByLogin(String userLogin) {
        Query query = em.createNamedQuery("Users.findByLogin");
        query.setParameter("login", userLogin);
        List users = query.getResultList();
        if(!users.isEmpty()) {
            System.out.println("DONE CUR TABLE.");
            return getCurrentTable(((Users)users.get(0)).getCityName(),((Users)users.get(0)).getCountryName());
        } else {
            System.out.println("NULL CUR TABLE.");
            return null;
        }        
    }
    @Override
    public List<List<String>> getWuaTableByLogin(String userLogin) {
        Query query = em.createNamedQuery("Users.findByLogin");
        query.setParameter("login", userLogin);
        List users = query.getResultList();
        if(!users.isEmpty()) {
            return getWuaTable(((Users)users.get(0)).getCityName(),((Users)users.get(0)).getCountryName());
        } else {
            return null;
        }        
    }
    
    @Override
    public List<List<String>> getYanTableByLogin(String userLogin) {
        Query query = em.createNamedQuery("Users.findByLogin");
        query.setParameter("login", userLogin);
        List users = query.getResultList();
        if(!users.isEmpty()) {
            return getYandexTable(((Users)users.get(0)).getCityName(),((Users)users.get(0)).getCountryName());
        } else {
            return null;
        }        
    }
    
    @Override
    public void persist(Object object) {
        em.persist(object);
    }
}
