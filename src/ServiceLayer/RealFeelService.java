package ServiceLayer;

import BusinessLogic.*;

import java.util.List;

/**
 * Created by yuraf_000 on 26.12.2014.
 */
public class RealFeelService {
    private RealFeelWorker realFeelWorker = null;
    private User currentUser = null;
    private UserWorker userWorker = new UserWorker();

    public RealFeelService(String userLogin) {
        currentUser = userWorker.getUser(userLogin);
        realFeelWorker = new RealFeelWorker(currentUser.getUserCity(), currentUser.getUserCountry());
    }

    public RealFeelService(String userLogin, String cityName, String countryName) {
        currentUser = userWorker.getUser(userLogin);
        realFeelWorker = new RealFeelWorker(cityName, countryName);
    }

    public boolean newRealFeel(int temperature, int pressure, int humidity, float windSpeed) {
        RealFeel newRealFeel = new RealFeel();
        newRealFeel.setTemperature(temperature);
        newRealFeel.setPressure(pressure);
        newRealFeel.setHumidity(humidity);
        newRealFeel.setWindSpeed(windSpeed);
        newRealFeel.setWindDirection("N/A");
        newRealFeel.setUserLogin(currentUser.getUserLogin());
        newRealFeel.setCityName(currentUser.getUserCity());
        newRealFeel.setCountryName(currentUser.getUserCountry());
        return realFeelWorker.newRealFeel(currentUser.getUserLogin(),newRealFeel);
    }

    public List<RealFeel> getRealFeels(int numberOfResults) {
        return realFeelWorker.getListOfRealFeel(numberOfResults);
    }

    public String getCurrentUserCityName() {
        return currentUser.getUserCity();
    }
}
