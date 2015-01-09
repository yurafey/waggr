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
    private int tempRange = 5;
    private int presRange = 20;
    private int humRange = 30;
    private int windRange = 10;
    private DBConnector dbConnector = new DBConnector();

    public RealFeelWorker(String cityName, String countryName) {
        realFeel.setCityName(cityName);
        realFeel.setCountryName(countryName);
        weatherWorker = new WeatherWorker(cityName,countryName);
        currentWeatherYandex = weatherWorker.getCurrentWeatherYandex();
        currentWeatherWUA = weatherWorker.getCurrentWeatherWUA();
    }

    public boolean newRealFeel(String userLogin,RealFeel realFeelWeather) {
        realFeel.setUserLogin(userLogin);
        System.out.println(realFeelWeather.getTemperature()+" "+realFeelWeather.getPressure()+" "+ realFeelWeather.getHumidity()+" "+realFeelWeather.getWindSpeed());
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
        if (((currentTemperatureYandex - tempRange)<=realFeel.getTemperature())&&((currentTemperatureYandex + tempRange)>=realFeel.getTemperature())) {
            return true;
        } else if (((currentTemperatureWUA - tempRange)<=realFeel.getTemperature())&&((currentTemperatureWUA+ tempRange)>=realFeel.getTemperature())) {
            return true;
        } else {
            return false;
        }
    }

    private boolean checkPressure(RealFeel realFeel) {
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

    private boolean checkHumidity(RealFeel realFeel) {
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

    private boolean checkWindSpeed(RealFeel realFeel) {
        float currentWindSpeedYandex = currentWeatherYandex.getWindSpeed();
        float currentWindSpeedWUA = currentWeatherWUA.getWindSpeed();
        if (((currentWindSpeedYandex - windRange)<=realFeel.getWindSpeed())&&((currentWindSpeedYandex + windRange)>=realFeel.getWindSpeed())) {
            return true;
        } else if (((currentWindSpeedWUA - windRange)<=realFeel.getWindSpeed())&&((currentWindSpeedWUA + windRange)>=realFeel.getWindSpeed())) {
            return true;
        } else {
            return false;
        }
    }
}
