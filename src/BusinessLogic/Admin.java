package BusinessLogic;

import DataAccessLayer.AdminGateway;


/**
 * Created by URI on 12.01.2015.
 */
public class Admin {
    private AdminGateway adminGateway = new AdminGateway();
    public String getCurrentRefreshPeriod() {
        return adminGateway.getCurrentPeriod();
    }
    public String getCurrentCountriesToRefresh() {
        return adminGateway.getCurrentCountries();
    }
    public boolean setCurrentRefreshPeriod(String currentPeriod) {
        return adminGateway.setCurrentPeriod(currentPeriod);
    }
    public boolean setCurrentCountriesToRefresh(String countryNames) {
        return adminGateway.setCurrentCountries(countryNames);
    }
}
