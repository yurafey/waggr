package RemoteServiceLayer;

import BusinessLogic.*;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

/**
 * Created by yuraf_000 on 26.12.2014.
 */
public class RealFeelServiceImpl extends UnicastRemoteObject implements RealFeelService {
    private RealFeelWorker realFeelWorker = null;
    private User currentUser = null;
    private UserWorker userWorker = new UserWorker();

    public RealFeelServiceImpl() throws RemoteException {
        //
    }

    public void initializeRealFeel(String userLogin, String cityName, String countryName) throws RemoteException {
        currentUser = userWorker.getUser(userLogin);
        realFeelWorker = new RealFeelWorker(cityName, countryName);
    }

    public void initializeRealFeel(String userLogin) throws RemoteException {
        currentUser = userWorker.getUser(userLogin);
        realFeelWorker = new RealFeelWorker(currentUser.getUserCity(), currentUser.getUserCountry());
    }

    public boolean newRealFeel(int temperature, int pressure, int humidity, float windSpeed) throws RemoteException {
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

    public List<RealFeel> getRealFeels(int numberOfResults) throws RemoteException {
        return realFeelWorker.getListOfRealFeel(numberOfResults);
    }

    public String getCurrentUserCityName() throws RemoteException {
        return currentUser.getUserCity();
    }
}
