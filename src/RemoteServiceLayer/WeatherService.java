package RemoteServiceLayer;

import BusinessLogic.Weather;
import BusinessLogic.WeatherWorker;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

/**
 * Created by URI on 24.01.2015.
 */
public interface WeatherService extends Remote {
    public Weather getCurrentWeatherYandex (String cityName, String countryName) throws RemoteException;
    public Weather getCurrentWeatherWUA (String cityName, String countryName) throws RemoteException;
    public List<List<Weather>> getForecastList (String cityName, String countryName) throws RemoteException;
}
