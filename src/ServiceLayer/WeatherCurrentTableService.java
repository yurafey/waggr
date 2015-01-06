package ServiceLayer;

import BusinessLogic.RealFeel;
import BusinessLogic.RealFeelWorker;
import BusinessLogic.Weather;
import BusinessLogic.WeatherWorker;
import DataAccessLayer.DBConnector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by yuraf_000 on 19.09.2014.
 */
public class WeatherCurrentTableService {
    private DBConnector db = new DBConnector();
    private WeatherWorker weatherWorker = null;
    private RealFeelWorker realFeelWorker = null;
    private String cityName = null;
    private String countryName = null;
    private final String[] colNames = {"","Yandex", "WeatherUA", "RealFeel(ощущается)"};
    private List<Object> rows = new ArrayList<>();
    private int numOfRealFeels = 1;

    public WeatherCurrentTableService(String login, String cityName, String countryName){
        this.cityName = cityName;
        this.countryName = countryName;
        realFeelWorker = new RealFeelWorker(login,cityName,countryName);
        weatherWorker = new WeatherWorker(cityName,countryName);
        TableProcessor();
    }
    public void TableProcessor (){
        Weather currentWeatherYandex = weatherWorker.getCurrentWeatherYandex();
        Weather currentWeatherWUA = weatherWorker.getCurrentWeatherWUA();
        List<RealFeel> realFeel = realFeelWorker.getListOfRealFeel(numOfRealFeels);
        rows.clear();
        rows.add(Arrays.asList("Температура",(currentWeatherYandex!=null)?currentWeatherYandex.getTemperature():"N/A", ((currentWeatherWUA!=null)?currentWeatherWUA.getTemperature():"N/A"), (realFeel!=null?(realFeel.get(0).getTemperature()):"N/A")));
        rows.add(Arrays.asList("Влажность", (currentWeatherYandex!=null)?currentWeatherYandex.getHumidity():"N/A", ((currentWeatherWUA!=null)?currentWeatherWUA.getHumidity():"N/A"),(realFeel!=null?(realFeel.get(0).getHumidity()):"N/A")));
        rows.add(Arrays.asList("Атмосферное давление",(currentWeatherYandex!=null)?currentWeatherYandex.getPressure():"N/A", ((currentWeatherWUA!=null)?currentWeatherWUA.getPressure():"N/A"),(realFeel!=null?(realFeel.get(0).getPressure()):"N/A")));
        rows.add(Arrays.asList("Скорость ветра", (currentWeatherYandex!=null)?currentWeatherYandex.getWindSpeed():"N/A", ((currentWeatherWUA!=null)?currentWeatherWUA.getWindSpeed():"N/A"),(realFeel!=null?(realFeel.get(0).getWindSpeed()):"N/A")));
        rows.add(Arrays.asList("Направление ветра", (currentWeatherYandex!=null)?currentWeatherYandex.getWindDirection():"N/A", ((currentWeatherWUA!=null)?currentWeatherWUA.getWindDirection():"N/A"),(realFeel!=null?(realFeel.get(0).getWindDirection()):"N/A")));
    }
    public void setNumOfRealFeels(int numOfRealFeels) {
        this.numOfRealFeels = numOfRealFeels;
    }
    public int getNumOfRealFeels() {
        return numOfRealFeels;
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
