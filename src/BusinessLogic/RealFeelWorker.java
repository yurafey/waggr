package BusinessLogic;

import DataAccessLayer.DBConnector;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by yuraf_000 on 12.09.2014.
 */
public class RealFeelWorker {
    private WeatherWorker weatherWorker = null;
    private Weather currentWeatherWUA = null;
    private Weather currentWeatherYandex = null;
    private RealFeel realFeel = new RealFeel();
    private int range = 5;
    private DBConnector dbConnector = new DBConnector();

    public void setRange(int range){
        this.range = range;
    }

    public RealFeelWorker(String userLogin, String cityName, String countryName) {
        realFeel.setCityName(cityName);
        realFeel.setCountryName(countryName);
        realFeel.setUserLogin(userLogin);
        weatherWorker = new WeatherWorker(cityName,countryName);
        currentWeatherYandex = weatherWorker.getCurrentWeatherYandex();
        currentWeatherWUA = weatherWorker.getCurrentWeatherWUA();
    }

    public boolean newRealFeel(RealFeel realFeelWeather) {
        if (checkTemperature(realFeelWeather) && checkPressure(realFeelWeather) && checkHumidity(realFeelWeather) && checkWindSpeed(realFeelWeather)) {
            return dbConnector.newRealFeel(realFeelWeather.getUserLogin(), new Timestamp(new java.util.Date().getTime()),
                    realFeelWeather.getCityName(), realFeelWeather.getCountryName(), realFeelWeather.getTemperature(), realFeelWeather.getPressure(), realFeelWeather.getHumidity(), realFeelWeather.getWindSpeed(), realFeelWeather.getWindDirection());
        }
        return false;
    }

    public List<RealFeel> getListOfRealFeel(int numberOfResults) {
        return dbConnector.getRealFeel(realFeel.getCityName(),realFeel.getCountryName(),numberOfResults);
    }

    private boolean checkTemperature(RealFeel realFeel) {
        int currentTemperatureYandex = currentWeatherYandex.getTemperature();
        int currentTemperatureWUA = currentWeatherWUA.getTemperature();
        if (((currentTemperatureYandex - range)<realFeel.getTemperature())&&((currentTemperatureYandex + range)>realFeel.getTemperature())) {
            return true;
        } else if (((currentTemperatureWUA - range)<realFeel.getTemperature())&&((currentTemperatureWUA+ range)>realFeel.getTemperature())) {
            return true;
        } else {
            return false;
        }
    }

    private boolean checkPressure(RealFeel realFeel) {
        int currentPressureYandex = currentWeatherYandex.getPressure();
        int currentPressureWUA = currentWeatherWUA.getPressure();
        if (((currentPressureYandex - range)<realFeel.getPressure())&&((currentPressureYandex + range)>realFeel.getPressure())) {
            return true;
        } else if (((currentPressureWUA - range)<realFeel.getPressure())&&((currentPressureWUA + range)>realFeel.getPressure())) {
            return true;
        } else {
            return false;
        }
    }

    private boolean checkHumidity(RealFeel realFeel) {
        int currentHumidityYandex = currentWeatherYandex.getHumidity();
        int currentHumidityWUA = currentWeatherWUA.getHumidity();
        if (((currentHumidityYandex - range)<realFeel.getHumidity())&&((currentHumidityYandex + range)>realFeel.getHumidity())) {
            return true;
        } else if (((currentHumidityWUA - range)<realFeel.getHumidity())&&((currentHumidityWUA + range)>realFeel.getHumidity())) {
            return true;
        } else {
            return false;
        }
    }

    private boolean checkWindSpeed(RealFeel realFeel) {
        float currentWindSpeedYandex = currentWeatherYandex.getWindSpeed();
        float currentWindSpeedWUA = currentWeatherWUA.getWindSpeed();
        if (((currentWindSpeedYandex - range)<realFeel.getWindSpeed())&&((currentWindSpeedYandex + range)>realFeel.getWindSpeed())) {
            return true;
        } else if (((currentWindSpeedWUA - range)<realFeel.getWindSpeed())&&((currentWindSpeedWUA + range)>realFeel.getWindSpeed())) {
            return true;
        } else {
            return false;
        }
    }
//    public RealFeel getRealFeel(String city_Name,String country_Name) {
//        return realFeel;
//    }
}
