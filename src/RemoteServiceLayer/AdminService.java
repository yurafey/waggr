package RemoteServiceLayer;

import ServiceLayer.WeatherForecastUpdateService;

import javax.swing.*;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * Created by URI on 12.01.2015.
 */
public interface AdminService extends Remote{
    public boolean restartWeatherServiceIfCanceled() throws RemoteException;
    public boolean startWeatherServiceIfNew() throws RemoteException;
    public boolean stopWeatherServiceIfWaiting() throws RemoteException;
    public boolean stopWeatherServiceIfUpdating() throws RemoteException;
    public String getCurrentCountries() throws RemoteException;
    public String getCurrentPeriod() throws RemoteException;
    public String getConsoleLogs() throws RemoteException;
    public String getRefreshLogs () throws RemoteException;
    public boolean setCurrentCountries(String countryNames) throws RemoteException;
    public boolean setCurrentPeriod(String currentPeriod) throws RemoteException;
    public List<String> checkCityExists(String cityName) throws RemoteException;
    public boolean checkCityExists(String cityName, String countryName) throws RemoteException;
    public boolean checkUpdateServiceIsCanceled() throws RemoteException;
    public boolean checkUpdateServiceIsUpdating() throws RemoteException;
    public boolean checkUpdateServiceIsNew() throws RemoteException;
    public boolean checkUpdateServiceIsWaiting() throws RemoteException;
    public void initializeAdminService(WeatherForecastUpdateService weatherForecastUpdateService) throws RemoteException;
}
