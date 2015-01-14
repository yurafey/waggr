package ServiceLayer;

import DataAccessLayer.CityGateway;
import java.util.List;

/**
 * Created by yuraf_000 on 25.12.2014.
 */
public class CityService {
    CityGateway cityGateway = new CityGateway();
    public List<String> checkCityExists(String cityName) {
        List<String> checkList = cityGateway.checkCity(cityName);
        if (checkList.size()!=0&&checkList!=null) {
            return checkList;
        } else {
            return null;
        }
    }
    public boolean checkCityExists(String cityName, String countryName) {
        return cityGateway.checkCity(cityName,countryName);
    }
}
