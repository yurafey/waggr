package ServiceLayer;

import BusinessLogic.*;

import java.util.List;

/**
 * Created by yuraf_000 on 26.12.2014.
 */
public class RealFeelService {
    private RealFeelWorker realFeelWorker = null;
    private User currentUser = null;
    //private UsersService usersService = new UsersService();
    private UserWorker userWorker = new UserWorker();

    public RealFeelService(String userLogin) {
        currentUser = userWorker.getUser(userLogin);
        realFeelWorker = new RealFeelWorker(userLogin, currentUser.getUserCity(), currentUser.getUserCountry());
    }

    public boolean newRealFeel(int temperature, int pressure, int humidity, float windSpeed) {
        RealFeel newRealFeel = new RealFeel();
        newRealFeel.setTemperature(temperature);
        newRealFeel.setPressure(pressure);
        newRealFeel.setHumidity(humidity);
        newRealFeel.setWindSpeed(windSpeed);
        newRealFeel.setWindDirection("N/A");
        return realFeelWorker.newRealFeel(newRealFeel);
    }

    public List<RealFeel> getRealFeels(int numberOfResults) {
        return realFeelWorker.getListOfRealFeel(numberOfResults);
    }


}
