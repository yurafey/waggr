package RemoteServiceLayer;

import BusinessLogic.RealFeel;
import BusinessLogic.RealFeelWorker;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * Created by URI on 12.01.2015.
 */
public interface RealFeelService extends Remote {
    public void initializeRealFeel(String userLogin, String cityName, String countryName) throws RemoteException;
    public void initializeRealFeel(String userLogin) throws RemoteException;
    public boolean newRealFeel(int temperature, int pressure, int humidity, float windSpeed) throws RemoteException;
    public List<RealFeel> getRealFeels(int numberOfResults) throws RemoteException;
    public String getCurrentUserCityName() throws RemoteException;
}
