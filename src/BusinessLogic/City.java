package BusinessLogic;

        import DataAccessLayer.DBConnector;

        import java.util.List;

/**
 * Created by URI on 12.01.2015.
 */
public class City {
    private DBConnector dbConnector = new DBConnector();
    public List<String> checkCityExists(String cityName) {
        List<String> checkList = dbConnector.checkCity(cityName);
        if (checkList.size() != 0 && checkList != null) {
            return checkList;
        } else {
            return null;
        }
    }

    public boolean checkCityExists(String cityName, String countryName) {
        return dbConnector.checkCity(cityName, countryName);
    }
}
