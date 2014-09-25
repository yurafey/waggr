package ServiceLayer;

import BusinessLogic.RealFeel;
import BusinessLogic.Weather;
import DataAccessLayer.DBConnector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by yuraf_000 on 24.09.2014.
 */
public class WeatherForecastTableContainer {
    private DBConnector db = new DBConnector();
    //private List<Weather> weatherForecast = new ArrayList<>();
    private List<List<Weather>> forecastsList = new ArrayList<>();
    private RealFeel realFeel = null;
    private String cityName = null;
    private String countryName = null;
    private final String[] colNames = {"Дата","Температура","Влажность","Давление воздуха","Скорость ветра","Направление ветра"};
    private List<Object> rowsYandex = new ArrayList<>();
    private List<Object> rowsWeatherUA = new ArrayList<>();

    public WeatherForecastTableContainer(String cityName, String countryName){
        this.cityName = cityName;
        this.countryName = countryName;
        TableProcessor();
    }
   // public
    private void TableProcessor (){
        forecastsList = db.getForecastsByCityAndCountyName(cityName,countryName);
        rowsYandex.clear();
        rowsWeatherUA.clear();
        for (int listCounter = 0; listCounter < forecastsList.size();listCounter++){
            List <Weather> forecastList = forecastsList.get(listCounter);
            switch (listCounter) {
                case 0:
                    rowsYandex.addAll(getRows(forecastList));
                    break;
                case 1:
                    rowsWeatherUA.addAll(getRows(forecastList));
                    break;
                default:
            }
        }
    }

    private List<Object> getRows(List<Weather> forecastList){
        List<Object> resultList = new ArrayList<>();
        if (forecastList.size()==0) resultList.add(Arrays.asList("N/A","N/A","N/A","N/A"));
        else {
            for (int i = 0; i < forecastList.size(); i++){
                Weather forecastElement = forecastList.get(i);
                resultList.add(Arrays.asList(
                        forecastElement.getDate().toString(),forecastElement.getTemperature(),
                        forecastElement.getHumidity(),forecastElement.getPressure(),forecastElement.getWindSpeed(),forecastElement.getWindDirection()));
            }
        }
        return resultList;
    }

    public Object getValueAtYandex(int row, int col){
        return ((List<Object>) rowsYandex.get(row)).get(col);
    }

    public Object getValueAtWeatherUA (int row, int col){
        return ((List<Object>) rowsWeatherUA.get(row)).get(col);
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
    public List<Object> getRowsYandex() {
        return rowsYandex;
    }
    public List<Object> getRowsWeatherUA(){
        return rowsWeatherUA;
    }
    public void onClose(){
        db.connectionClose();
    }
}
