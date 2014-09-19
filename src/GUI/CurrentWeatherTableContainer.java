package GUI;

import BusinessLogic.RealFeel;
import BusinessLogic.Weather;
import DBProcessor.DBConnector;

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
    private final String[] colNames = {"Yandex", "WeatherUA"};


    private List<Object> rows = new ArrayList<>();

    public CurrentWeatherTableContainer(String cityName, String countryName){
        this.cityName = cityName;
        this.countryName = countryName;
        TableProcessor();
    }
    private void TableProcessor (){
        currentWeatherWUA = db.GetCurrentWUA(cityName,countryName);
        currentWeatherYandex = db.GetCurrentYandex(cityName, countryName);
        rows.clear();
        rows.add(Arrays.asList(currentWeatherYandex.GetTemperature(), currentWeatherWUA.GetTemperature()));
        rows.add(Arrays.asList(currentWeatherYandex.GetHumidity(), currentWeatherWUA.GetHumidity()));
        rows.add(Arrays.asList(currentWeatherYandex.GetPressure(), currentWeatherWUA.GetPressure()));
        rows.add(Arrays.asList(currentWeatherYandex.GetWindSpeed(), currentWeatherWUA.GetWindSpeed()));
        rows.add(Arrays.asList(currentWeatherYandex.GetWindDirection(), currentWeatherWUA.GetWindDirection()));
    }
    public Object GetValueAt(int row,int col){
        return ((List<Object>)rows.get(row)).get(col);
    }

    public String[] getColNames() {
        return colNames;
    }
    public List<Object> getRows() {
        return rows;
    }

}
