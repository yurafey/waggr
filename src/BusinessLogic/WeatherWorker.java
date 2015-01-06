package BusinessLogic;

import DataAccessLayer.DBConnector;

/**
 * Created by yuraf_000 on 24.12.2014.
 */
public class WeatherWorker {
    private DBConnector dbConnector = new DBConnector();
    private String cityName = null;
    private String countryName = null;
    private Weather currentWeatherYandex = null;
    private Weather currentWeatherWUA = null;

    public WeatherWorker(String cityName, String countryName) {
        this.cityName = cityName;
        this.countryName = countryName;
        currentWeatherYandex = dbConnector.getCurrentYandex(cityName,countryName);
        currentWeatherWUA = dbConnector.getCurrentWUA(cityName,countryName);
    }

    public Weather getCurrentWeatherYandex() {
        return currentWeatherYandex;
    }

    public Weather getCurrentWeatherWUA() {
        return currentWeatherWUA;
    }

}