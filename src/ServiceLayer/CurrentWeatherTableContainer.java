package ServiceLayer;

import BusinessLogic.RealFeel;
import BusinessLogic.Weather;
import DataAccessLayer.DBConnector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by yuraf_000 on 19.09.2014.
 */
public class CurrentWeatherTableContainer {
    private DBConnector db = new DBConnector();
    private Weather currentWeatherYandex = null;
    private Weather currentWeatherWUA = null;
    private RealFeel realFeel = null;
    private String cityName = null;
    private String countryName = null;
    private final String[] colNames = {"","Yandex", "WeatherUA"};


    private List<Object> rows = new ArrayList<>();

    public CurrentWeatherTableContainer(String cityName, String countryName){
        this.cityName = cityName;
        this.countryName = countryName;
        TableProcessor();
    }
    public void TableProcessor (){
        currentWeatherWUA = db.getCurrentWUA(cityName, countryName);
        currentWeatherYandex = db.getCurrentYandex(cityName, countryName);
        rows.clear();
        rows.add(Arrays.asList("Температура",(currentWeatherYandex!=null)?currentWeatherYandex.getTemperature():"N/A", (currentWeatherWUA!=null)?currentWeatherWUA.getTemperature():"N/A"));
        rows.add(Arrays.asList("Влажность", (currentWeatherYandex!=null)?currentWeatherYandex.getHumidity():"N/A", (currentWeatherWUA!=null)?currentWeatherWUA.getHumidity():"N/A"));
        rows.add(Arrays.asList("Атмосферное давление",(currentWeatherYandex!=null)?currentWeatherYandex.getPressure():"N/A", (currentWeatherWUA!=null)?currentWeatherWUA.getPressure():"N/A"));
        rows.add(Arrays.asList("Скорость ветра", (currentWeatherYandex!=null)?currentWeatherYandex.getWindSpeed():"N/A", (currentWeatherWUA!=null)?currentWeatherWUA.getWindSpeed():"N/A"));
        rows.add(Arrays.asList("Направление ветра", (currentWeatherYandex!=null)?currentWeatherYandex.getWindDirection():"N/A", (currentWeatherWUA!=null)?currentWeatherWUA.getWindDirection():"N/A"));
    }
    public Object GetValueAt(int row,int col){
        return ((List<Object>)rows.get(row)).get(col);
    }
    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }
    public String[] getColNames() {
        return colNames;
    }
    public List<Object> getRows() {
        return rows;
    }
    public void onClose(){
        db.connectionClose();
    }
}
