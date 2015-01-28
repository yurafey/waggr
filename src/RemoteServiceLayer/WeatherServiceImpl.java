package RemoteServiceLayer;

import BusinessLogic.Weather;
import BusinessLogic.WeatherWorker;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by URI on 24.01.2015.
 */
public class WeatherServiceImpl extends UnicastRemoteObject implements WeatherService {

    public WeatherServiceImpl() throws RemoteException {
    }

    public Weather getCurrentWeatherYandex (String cityName, String countryName) throws RemoteException {
        WeatherWorker weatherWorker = new WeatherWorker(cityName,countryName);
        return weatherWorker.getCurrentWeatherYandex();
    }

    public Weather getCurrentWeatherWUA (String cityName, String countryName) throws RemoteException {
        WeatherWorker weatherWorker = new WeatherWorker(cityName,countryName);
        return weatherWorker.getCurrentWeatherWUA();
    }

    public List<List<Weather>> getForecastList (String cityName, String countryName) throws RemoteException {
        WeatherWorker weatherWorker = new WeatherWorker(cityName,countryName);
        return weatherWorker.getForecastsByCityAndCountyName();
    }

}
