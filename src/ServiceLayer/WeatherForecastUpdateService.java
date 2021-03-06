package ServiceLayer;

import DataAccessLayer.AdminGateway;
import DataAccessLayer.WeatherGateway;
import Parsers.ForecastContainerWUA;
import Parsers.ForecastContainerYa;

import javax.swing.*;

/**
 * Created by yuraf_000 on 26.11.2014.
 */
public class WeatherForecastUpdateService extends SwingWorker {
    private WeatherGateway weatherGateway = null;
    private AdminGateway adminGateway = null;
    private String CountryNames = null;
    private Integer refreshTime;
    public enum stateOfService {
        WAITING, CANCELED, NEW, UPDATING
    };
    private boolean adminOpened = false;
    private stateOfService currentState = stateOfService.NEW;

    public void setRefreshTime (Integer refreshTime) {
        this.refreshTime = refreshTime;
    }

    public void setAdminOpened(boolean value) {
        adminOpened = value;
    }

    public stateOfService getCurrentState() {
        return currentState;
    }

    @Override
    protected Object doInBackground() throws Exception {
        while (true) {
            weatherGateway = new WeatherGateway();
            adminGateway = new AdminGateway();
            currentState = stateOfService.UPDATING;
            CountryNames = adminGateway.getCurrentCountries();
            refreshTime = Integer.parseInt(adminGateway.getCurrentPeriod());
            ForecastContainerYa FCY = new ForecastContainerYa(CountryNames);
            if (isCancelled()) {
                currentState = stateOfService.CANCELED;
                return null;
            }
            ForecastContainerWUA FCWUA = new ForecastContainerWUA(CountryNames);
            if (isCancelled()) {
                currentState = stateOfService.CANCELED;
                return null;
            }
            weatherGateway.writeWeatherDataYandex(FCY.GetCityWeatherList(), FCY.GetCityIdMap(), FCY.GetCountryIdMap(), FCY.GetCountryCityMap());
            weatherGateway.writeWeatherDataWUA(FCWUA.GetCityWeatherList(), FCWUA.GetCityIdMap(), FCWUA.GetCountryIdMap(), FCWUA.GetCountyCitiesMap());
            weatherGateway.connectionClose();
            if (isCancelled()) {
                currentState = stateOfService.CANCELED;
                return null;
            }
            if (!refreshTime.equals(0)) {
                int i = 0;
                int z;
                while (i < refreshTime*60) {
                    if (currentState!=stateOfService.WAITING) currentState = stateOfService.WAITING;
                    z = refreshTime*60 - i;
                    if (adminOpened) {
                        System.out.println("msg"+ ((z/60)%60)+":"+((String.valueOf(z%60).length()>1?z%60:("0"+z%60))));
                    }
                    i++;
                    Thread.sleep(1000);
                    if (i==refreshTime*60) {
                        if(adminOpened) {
                            System.out.println("msg ref");
                        }
                    }
                }
            } else break;
            weatherGateway.connectionClose();
        } return null;
    }

    public boolean isCanceled() {
        if (getCurrentState()==stateOfService.CANCELED) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isNew() {
        if (getCurrentState()==stateOfService.NEW) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isWaiting() {
        if (getCurrentState()==stateOfService.WAITING) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isUpdating() {
        if (getCurrentState()==stateOfService.UPDATING) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected void done() {
        if (isWaiting()) currentState = stateOfService.CANCELED;
    }
}
