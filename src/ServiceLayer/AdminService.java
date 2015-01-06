package ServiceLayer;

import DataAccessLayer.DBConnector;
import Utils.ConsoleWorker;

import javax.swing.*;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;

/**
 * Created by yuraf_000 on 25.12.2014.
 */
public class AdminService {
    private ConsoleWorker consoleWorker = null;
    private final PipedInputStream outPipe = new PipedInputStream();
    private DBConnector dbConnector = new DBConnector();
    private WeatherForecastUpdateService weatherForecastUpdateService = null;

    public AdminService(WeatherForecastUpdateService weatherForecastUpdateService) {
        this.weatherForecastUpdateService = weatherForecastUpdateService;
        this.weatherForecastUpdateService.setAdminOpened(true);
    }

    public boolean restartWeatherServiceIfCanceled() {
        if (weatherForecastUpdateService.isCanceled()) {
            weatherForecastUpdateService = new WeatherForecastUpdateService();
            weatherForecastUpdateService.setAdminOpened(true);
            weatherForecastUpdateService.execute();
            return true;
        } else {
            return false;
        }
    }

    public boolean startWeatherServiceIfNew() {
        if (weatherForecastUpdateService.isNew()) {
            weatherForecastUpdateService.execute();
            return true;
        } else {
            return false;
        }
    }

    public boolean stopWeatherServiceIfWaiting() {
        if (weatherForecastUpdateService.isWaiting()) {
            weatherForecastUpdateService.cancel(true);
            return true;
        } else {
            return false;
        }
    }

    public boolean stopWeatherServiceIfUpdating(final Object button, final Object label) {
        if (weatherForecastUpdateService !=null){
            if (weatherForecastUpdateService.isUpdating()) {
                weatherForecastUpdateService.cancel(true);
                SwingWorker<Void, String> buttonWorker = new SwingWorker<Void, String>() {
                    protected Void doInBackground() throws Exception {
                        while (true) {
                            Thread.sleep(1L);
                            if (weatherForecastUpdateService.isCanceled()) {
                                ((JButton)button).setEnabled(true);
                                ((JButton)button).setText("Включить");
                                ((JLabel)label).setText("Сервис отключен.");
                                return null;
                            }
                        }
                    }
                };
                buttonWorker.execute();
                return true;
            }
        }
        return false;
    }

    public WeatherForecastUpdateService getCurrentWeatherForecastService() {
        return weatherForecastUpdateService;
    }


    public void executeConsoleWorker(Object outLabel,Object outTextArea){
        try {
            System.setOut(new PrintStream(new PipedOutputStream(outPipe), true));
        } catch (IOException e) {
            e.printStackTrace();
        }
        consoleWorker = new ConsoleWorker(outLabel,outTextArea,outPipe);
        if (consoleWorker!=null) {
           consoleWorker.execute();
        }
    }

    public String getCurrentCountries() {
        return dbConnector.getCurrentCountries();
    }

    public String getCurrentPeriod() {
        return dbConnector.getCurrentPeriod();
    }

    public boolean setCurrentCountries(String countryNames) {
        return dbConnector.setCurrentCountries(countryNames);
    }

    public boolean setCurrentPeriod(String currentPeriod) {
        weatherForecastUpdateService.setRefreshTime(Integer.parseInt(currentPeriod));
        return dbConnector.setCurrentPeriod(currentPeriod);
    }


}
