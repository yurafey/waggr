package ServiceLayer;

import BusinessLogic.Weather;
import DataAccessLayer.DBConnector;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import sun.awt.windows.WEmbeddedFrame;

/**
 * Created by URI on 10.01.2015.
 */
public class JSONBuilderService {
    public static JSONObject prepareAndGetJSON (String provider, int cityId) {
        DBConnector dbConnector = new DBConnector();
        switch (provider) {
            case "WUA": {
                Weather currentWUA = dbConnector.getCurrentWUA(cityId);
                if (currentWUA != null) {
                    String cityName = dbConnector.getCityNameWUA(cityId);
                    if (cityName!=null) {
                        return prepareJSON(currentWUA,cityName);
                    }
                }
                return null;
            }
            case "Yandex": {
                Weather currentYandex = dbConnector.getCurrentYandex(cityId);
                if (currentYandex != null) {
                    String cityName = dbConnector.getCityNameYandex(cityId);
                    if (cityName!=null) {
                        return prepareJSON(currentYandex,cityName);
                    }
                }
                return null;
            }
            default:
                return null;
        }

    }
    private static JSONObject prepareJSON(Weather currentWeather, String cityName) {
        JSONObject result = new JSONObject();
        result.put("cityName",cityName);
        result.put("temperature", currentWeather.getTemperature());
        result.put("pressure",currentWeather.getPressure());
        result.put("humidity",currentWeather.getHumidity());
        result.put("windSpeed",currentWeather.getWindSpeed());
        result.put("windDirection",currentWeather.getWindDirection());
        result.put("date", currentWeather.getDate());
        return result;
    }
}
