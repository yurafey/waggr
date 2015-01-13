package BusinessLogic;

import DataAccessLayer.DBConnector;

import java.util.List;

/**
 * Created by URI on 12.01.2015.
 */
public class Admin {
    private DBConnector dbConnector = new DBConnector();
    public String getCurrentRefreshPeriod() {
        return dbConnector.getCurrentPeriod();
    }
    public String getCurrentCountriesToRefresh() {
        return dbConnector.getCurrentCountries();
    }
    public boolean setCurrentRefreshPeriod(String currentPeriod) {
        return dbConnector.setCurrentPeriod(currentPeriod);
    }
    public boolean setCurrentCountriesToRefresh(String countryNames) {
        return dbConnector.setCurrentCountries(countryNames);
    }
}
