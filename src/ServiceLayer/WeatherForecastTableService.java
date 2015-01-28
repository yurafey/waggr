package ServiceLayer;

import BusinessLogic.Weather;
import BusinessLogic.WeatherWorker;
import RemoteServiceLayer.RealFeelService;
import RemoteServiceLayer.WeatherService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

/**
 * Created by yuraf_000 on 24.09.2014.
 */
public class WeatherForecastTableService {
    private List<List<Weather>> forecastsList = new ArrayList<>();
    private final String[] colNames = {"Дата","Температура","Влажность","Давление воздуха","Скорость ветра","Направление ветра"};
    private List<Object> rowsYandex = new ArrayList<>();
    private List<Object> rowsWeatherUA = new ArrayList<>();
    private WeatherService weatherService;
    private String currentCityName;
    private String currentCountryName;

    private static DateFormatSymbols myDateFormatSymbols = new DateFormatSymbols(){

        @Override
        public String[] getMonths() {
            return new String[]{"января", "февраля", "марта", "апреля", "мая", "июня",
                    "июля", "августа", "сентября", "октября", "ноября", "декабря"};
        }

    };

    public WeatherForecastTableService(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    public void initializeWeatherForecastTableService(String cityName, String countryName) throws RemoteException {
        this.currentCityName = cityName;
        this.currentCountryName = countryName;
        TableProcessor();
    }

    private void TableProcessor () throws RemoteException {
        forecastsList = weatherService.getForecastList(currentCityName, currentCountryName);
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
        if (forecastList.size()==0) resultList.add(Arrays.asList("N/A","N/A","N/A","N/A","N/A","N/A"));
        else {
            for (int i = 0; i < forecastList.size(); i++){
                Weather forecastElement = forecastList.get(i);
                java.util.Date fdate = forecastElement.getDate();
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
                        dateFormat.format(fdate)+" "+partOfDay,forecastElement.getTemperature(),
                        forecastElement.getHumidity(),forecastElement.getPressure(),forecastElement.getWindSpeed(),forecastElement.getWindDirection()));
            }
        }
        return resultList;
    }



    public Object getValueAtYandex(int row, int col) {
        return ((List<Object>) rowsYandex.get(row)).get(col);
    }

    public Object getValueAtWeatherUA (int row, int col) {
        return ((List<Object>) rowsWeatherUA.get(row)).get(col);
    }
    public String[] getColNames() {
        return colNames;
    }
    public List<Object> getRowsYandex() {
        return rowsYandex;
    }
    public List<Object> getRowsWeatherUA() {
        return rowsWeatherUA;
    }
//    public void onClose() throws RemoteException {
//        weatherWorker.onClose();
//    }
}
