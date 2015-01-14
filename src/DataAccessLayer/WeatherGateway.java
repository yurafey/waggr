package DataAccessLayer;

import BusinessLogic.Weather;
import com.google.common.collect.ListMultimap;

import java.sql.*;
import java.sql.Date;
import java.util.*;

/**
 * Created by URI on 14.01.2015.
 */
public class WeatherGateway {
    private Connection waggrConnection = null;
    private String weatherTableNameYandex = "weatheryan";
    private String weatherTableNameWUA = "weatherwua";
    public WeatherGateway() {
        try {
            Class.forName("org.postgresql.Driver");
            waggrConnection = DriverManager.getConnection(
                    "jdbc:postgresql://127.0.0.1:5432/waggrdb", "waggr",
                    "waggr");
        } catch (SQLException e) {
            System.out.println("Connection Failed!");
            e.printStackTrace();
            return;

        } catch (ClassNotFoundException e) {
            return;
        }
    }
    public void connectionClose() {
        try {
            waggrConnection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void writeWeatherDataYandex(HashMap<Integer, List<Weather>> CityWeatherListYandex, HashMap<Integer, String> CityIdMapYandex, HashMap<Integer, String> CountryIdMapYandex, ListMultimap<Integer, Integer> CountryCityMapYandex) {
        clearTable(weatherTableNameYandex);
        clearSequence("seq1");
        writeWeatherData(weatherTableNameYandex, CityWeatherListYandex, CityIdMapYandex, CountryIdMapYandex, CountryCityMapYandex);

    }

    public void writeWeatherDataWUA(HashMap<Integer, List<Weather>> CityWeatherListWUA, HashMap<Integer, String> CityIdMapWUA, HashMap<Integer, String> CountryIdMapWUA, ListMultimap<Integer, Integer> CountryCityMapWUA) {
        clearTable(weatherTableNameWUA);
        clearSequence("seq2");
        writeWeatherData(weatherTableNameWUA, CityWeatherListWUA, CityIdMapWUA, CountryIdMapWUA, CountryCityMapWUA);

    }
    private Boolean clearSequence(String sequenceName) {
        try {
            Statement StClear = waggrConnection.createStatement();
            Boolean res = StClear.execute("SELECT setval('" + sequenceName + "', 0);");
            StClear.close();
            return res;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Boolean clearTable(String tableName) {
        try {
            Statement StDel = waggrConnection.createStatement();
            Boolean res = StDel.execute("DELETE FROM " + tableName + ";");
            StDel.close();
            return res;
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return null;
    }
    private void writeWeatherData(String tableName, HashMap<Integer, List<Weather>> CityWeatherList, HashMap<Integer, String> CityIdMap, HashMap<Integer, String> CountryIdMap, ListMultimap<Integer, Integer> CountryCityMap) {
        try {
            String stm = "INSERT INTO " + tableName + "(timestamp, city_id, city_name, temperature, pressure, humidity, wind_speed, wind_direction, country_name, is_predict) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement tempPst = waggrConnection.prepareStatement(stm);

            for (Integer CountryId: CountryCityMap.keySet()){
                List<Integer> CityIds = CountryCityMap.get(CountryId);
                for (int y = 0; y < CityIds.size(); y++) {
                    Integer CityId = CityIds.get(y);
                    try {
                        List<Weather> tmpWeatherList = CityWeatherList.get(CityId);
                        for (int i = 0; i < tmpWeatherList.size(); i++) {
                            Weather tmpWeather = tmpWeatherList.get(i);
                            Timestamp sqlDate = new Timestamp(tmpWeather.getDate().getTime());
                            tempPst.setTimestamp(1, sqlDate);
                            tempPst.setInt(2, CityId);
                            tempPst.setString(3, CityIdMap.get(CityId));
                            tempPst.setInt(4, tmpWeather.getTemperature());
                            tempPst.setInt(5, tmpWeather.getPressure());
                            tempPst.setInt(6, tmpWeather.getHumidity());
                            tempPst.setFloat(7, tmpWeather.getWindSpeed());
                            tempPst.setString(8, tmpWeather.getWindDirection());
                            tempPst.setString(9, CountryIdMap.get(CountryId));
                            tempPst.setBoolean(10, tmpWeather.getIsPredict());
                            tempPst.execute();
                        }
                    } catch (NullPointerException e) {
                        System.out.println("DBCONNECTOR ERROR: City "+CityId+" forecast not found");

                    }
                }
            }
            tempPst.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Weather getCurrentWUA(String cityName, String countryName){
        return getCurrentWeather(weatherTableNameWUA, cityName, countryName);
    }

    public Weather getCurrentWUA(int cityId){
        return getCurrentWeather(weatherTableNameWUA, cityId);
    }

    public Weather getCurrentYandex(String cityName, String countryName){
        return getCurrentWeather(weatherTableNameYandex, cityName, countryName);
    }

    public Weather getCurrentYandex(int cityId){
        return getCurrentWeather(weatherTableNameYandex, cityId);
    }

    private Weather getCurrentWeather(String tableName, String cityName, String countryName){
        try {
            Statement getWeather = waggrConnection.createStatement();
            String query = String.format("SELECT timestamp, temperature, pressure, humidity, wind_speed, wind_direction FROM %s WHERE city_name = '%s' AND country_name = '%s' AND is_predict = FALSE;", tableName, cityName, countryName);
            ResultSet res = getWeather.executeQuery(query);
            if (res.next()){
                Weather weatherResult = new Weather();
                weatherResult.setDate((java.util.Date) res.getTimestamp(1));
                weatherResult.setTemperature(res.getInt(2));
                weatherResult.setPressure(res.getInt(3));
                weatherResult.setHumidity(res.getInt(4));
                weatherResult.setWindSpeed(res.getFloat(5));
                weatherResult.setWindDirection(res.getString(6));
                weatherResult.setIsPredict(false);
                return weatherResult;
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    private Weather getCurrentWeather(String tableName, int cityId){
        try {
            Statement getWeather = waggrConnection.createStatement();
            String query = String.format("SELECT timestamp, temperature, pressure, humidity, wind_speed, wind_direction FROM %s WHERE city_id = '%s' AND is_predict = FALSE;", tableName, cityId);
            ResultSet res = getWeather.executeQuery(query);
            if (res.next()){
                Weather weatherResult = new Weather();
                weatherResult.setDate((java.util.Date) res.getTimestamp(1));
                weatherResult.setTemperature(res.getInt(2));
                weatherResult.setPressure(res.getInt(3));
                weatherResult.setHumidity(res.getInt(4));
                weatherResult.setWindSpeed(res.getFloat(5));
                weatherResult.setWindDirection(res.getString(6));
                weatherResult.setIsPredict(false);
                return weatherResult;
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
    public List<List<Weather>> getForecastsByCityAndCountyName(String cityName, String countryName) {
        try {
            ResultSet rs = null;
            String query1 = String.format("SELECT timestamp, temperature, pressure, humidity, wind_speed, wind_direction FROM %s WHERE city_name = '%s' AND country_name = '%s' AND is_predict = 'TRUE' ORDER BY timestamp;",weatherTableNameYandex,cityName,countryName);
            String query2 = String.format("SELECT timestamp, temperature, pressure, humidity, wind_speed, wind_direction FROM %s WHERE city_name = '%s' AND country_name = '%s' AND is_predict = 'TRUE' ORDER BY timestamp;",weatherTableNameWUA,cityName,countryName);
            PreparedStatement pst = waggrConnection.prepareStatement(query1+query2);
            boolean isResult = pst.execute();
            List<List<Weather>> resultWeatherLists = new ArrayList<>();
            do {
                List<Weather> WeatherList = new ArrayList<>();
                rs = pst.getResultSet();
                while (rs.next()) {
                    Weather weatherResult = new Weather();
                    weatherResult.setDate((java.util.Date) rs.getTimestamp(1));
                    weatherResult.setTemperature(rs.getInt(2));
                    weatherResult.setPressure(rs.getInt(3));
                    weatherResult.setHumidity(rs.getInt(4));
                    weatherResult.setWindSpeed(rs.getFloat(5));
                    weatherResult.setWindDirection(rs.getString(6));
                    weatherResult.setIsPredict(true);
                    WeatherList.add(weatherResult);
                }
                resultWeatherLists.add(WeatherList);
                isResult = pst.getMoreResults();
            } while (isResult);

            return (resultWeatherLists.size()==0)?null:resultWeatherLists;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
