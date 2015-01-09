package ServiceLayer;

import BusinessLogic.RealFeel;
import BusinessLogic.Weather;
import BusinessLogic.WeatherWorker;
import DataAccessLayer.DBConnector;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by yuraf_000 on 19.09.2014.
 */
public class WeatherCurrentTableService {
    private DBConnector db = new DBConnector();
    private WeatherWorker weatherWorker = null;
    private List<RealFeel> realFeelList = null;
    private String[] colNames = null;
    private List<Object> rows = new ArrayList<>();
    private int numOfRealFeels = 1;

    private static DateFormatSymbols myDateFormatSymbols = new DateFormatSymbols(){

        @Override
        public String[] getMonths() {
            return new String[]{"января", "февраля", "марта", "апреля", "мая", "июня",
                    "июля", "августа", "сентября", "октября", "ноября", "декабря"};
        }

    };


    public WeatherCurrentTableService(String login, String cityName, String countryName) {
        RealFeelService realFeelService = new RealFeelService(login, cityName, countryName);
        realFeelList = realFeelService.getRealFeels(numOfRealFeels);
        colNames = new String[]{"", "Yandex", "WeatherUA", "RealFeel(ощущается)"};
        weatherWorker = new WeatherWorker(cityName, countryName);
        TableProcessor();
    }

    public void TableProcessor (){
        Weather currentWeatherYandex = weatherWorker.getCurrentWeatherYandex();
        Weather currentWeatherWUA = weatherWorker.getCurrentWeatherWUA();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM HH:mm", myDateFormatSymbols );
        rows.clear();
        rows.add(Arrays.asList("Дата",(currentWeatherYandex!=null)?dateFormat.format(currentWeatherYandex.getDate()):"N/A", ((currentWeatherWUA!=null)?dateFormat.format(currentWeatherWUA.getDate()):"N/A"), (realFeelList!=null? (dateFormat.format(realFeelList.get(0).getDate())+" от "+realFeelList.get(0).getUserLogin()):"N/A")));
        rows.add(Arrays.asList("Температура",(currentWeatherYandex!=null)?currentWeatherYandex.getTemperature():"N/A", ((currentWeatherWUA!=null)?currentWeatherWUA.getTemperature():"N/A"), (realFeelList!=null?(realFeelList.get(0).getTemperature()):"N/A")));
        rows.add(Arrays.asList("Влажность", (currentWeatherYandex!=null)?currentWeatherYandex.getHumidity():"N/A", ((currentWeatherWUA!=null)?currentWeatherWUA.getHumidity():"N/A"),(realFeelList!=null?(realFeelList.get(0).getHumidity()):"N/A")));
        rows.add(Arrays.asList("Атмосферное давление",(currentWeatherYandex!=null)?currentWeatherYandex.getPressure():"N/A", ((currentWeatherWUA!=null)?currentWeatherWUA.getPressure():"N/A"),(realFeelList!=null?(realFeelList.get(0).getPressure()):"N/A")));
        rows.add(Arrays.asList("Скорость ветра", (currentWeatherYandex!=null)?currentWeatherYandex.getWindSpeed():"N/A", ((currentWeatherWUA!=null)?currentWeatherWUA.getWindSpeed():"N/A"),(realFeelList!=null?(realFeelList.get(0).getWindSpeed()):"N/A")));
        rows.add(Arrays.asList("Направление ветра", (currentWeatherYandex!=null)?currentWeatherYandex.getWindDirection():"N/A", ((currentWeatherWUA!=null)?currentWeatherWUA.getWindDirection():"N/A"),(realFeelList!=null?(realFeelList.get(0).getWindDirection()):"N/A")));
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
