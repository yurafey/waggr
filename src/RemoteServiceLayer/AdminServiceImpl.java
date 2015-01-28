package RemoteServiceLayer;

import BusinessLogic.Admin;
import BusinessLogic.City;
import ServiceLayer.WeatherForecastUpdateService;
import Utils.ConsoleWorker;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

/**
 * Created by yuraf_000 on 25.12.2014.
 */
public class AdminServiceImpl extends UnicastRemoteObject implements AdminService {
    private ConsoleWorker consoleWorker = null;
    private Admin adminUtils = new Admin();
    private City city = new City();
    private WeatherForecastUpdateService weatherForecastUpdateService = null;
    private String consoleLogs = "";
    private String refreshLogs = "";

    public AdminServiceImpl() throws RemoteException {
        //super();
        executeConsoleWorker();
    }

    public void initializeAdminService(WeatherForecastUpdateService weatherForecastUpdateService) throws RemoteException {
        this.weatherForecastUpdateService = weatherForecastUpdateService;
        if (weatherForecastUpdateService!=null) this.weatherForecastUpdateService.setAdminOpened(true);
    }

    public boolean checkUpdateServiceIsCanceled() throws RemoteException {
        return weatherForecastUpdateService.isCanceled();
    }

    public boolean checkUpdateServiceIsUpdating() throws RemoteException {
        return weatherForecastUpdateService.isUpdating();
    }

    public boolean checkUpdateServiceIsNew() throws RemoteException {
        return weatherForecastUpdateService.isNew();
    }

    public boolean checkUpdateServiceIsWaiting() throws RemoteException {
        return weatherForecastUpdateService.isWaiting();
    }


    public boolean restartWeatherServiceIfCanceled() throws RemoteException {
        if (weatherForecastUpdateService.isCanceled()) {
            weatherForecastUpdateService = new WeatherForecastUpdateService();
            weatherForecastUpdateService.setAdminOpened(true);
            weatherForecastUpdateService.execute();
            System.out.println("msg ref");
            return true;
        } else {
            return false;
        }
    }

    public boolean startWeatherServiceIfNew() throws RemoteException {
        if (weatherForecastUpdateService.isNew()) {
            weatherForecastUpdateService.execute();
            System.out.println("msg ref");
            return true;
        } else {
            return false;
        }
    }

    public boolean stopWeatherServiceIfWaiting() throws RemoteException {
        if (weatherForecastUpdateService.isWaiting()) {
            weatherForecastUpdateService.cancel(true);
            return true;
        } else {
            return false;
        }
    }

    public boolean stopWeatherServiceIfUpdating() throws RemoteException {
        if (weatherForecastUpdateService !=null){
            if (weatherForecastUpdateService.isUpdating()) {
                weatherForecastUpdateService.cancel(true);
                return true;
            }
        }
        return false;
    }



//    public WeatherForecastUpdateService getCurrentWeatherForecastService() throws RemoteException {
//        return weatherForecastUpdateService;
//    }


    private void executeConsoleWorker() throws RemoteException {
        consoleWorker = new ConsoleWorker(consoleLogs, refreshLogs);
        if (consoleWorker!=null) {
           consoleWorker.execute();
        }
    }

    public String getCurrentCountries() throws RemoteException {
        return adminUtils.getCurrentCountriesToRefresh();
    }

    public String getCurrentPeriod() throws RemoteException {
        return adminUtils.getCurrentRefreshPeriod();
    }

    @Override
    public String getConsoleLogs() throws RemoteException {
        return consoleWorker.getLogs();
    }

    @Override
    public String getRefreshLogs() throws RemoteException {
        return consoleWorker.getRefreshLog();
    }

    public boolean setCurrentCountries(String countryNames) throws RemoteException {
        return adminUtils.setCurrentCountriesToRefresh(countryNames);
    }

    public boolean setCurrentPeriod(String currentPeriod) throws RemoteException {
        weatherForecastUpdateService.setRefreshTime(Integer.parseInt(currentPeriod));
        return adminUtils.setCurrentRefreshPeriod(currentPeriod);
    }

    public List<String> checkCityExists(String cityName) throws RemoteException {
        return city.checkCityExists(cityName);
    }

    public boolean checkCityExists(String cityName, String countryName) throws RemoteException {
        return city.checkCityExists(cityName, countryName);
    }

}
