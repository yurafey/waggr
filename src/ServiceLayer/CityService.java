package ServiceLayer;

import DataAccessLayer.DBConnector;

import java.util.List;

/**
 * Created by yuraf_000 on 25.12.2014.
 */
public class CityService {
    DBConnector dbConnector = new DBConnector();
    public List<String> checkCityExists(String cityName) {
        List<String> checkList = dbConnector.checkCity(cityName);
        System.out.println(checkList==null?"null":"notnull");
        System.out.println(checkList.toString().isEmpty());
        System.out.println(checkList);
        if (checkList.size()!=0&&checkList!=null) {
            return checkList;
        } else {
            return null;
        }
    }
    public boolean checkCityExists(String cityName, String countryName) {
        return dbConnector.checkCity(cityName,countryName);
    }
}
