package DBProcessor;

import BusinessLogic.User;
import BusinessLogic.Weather;
import com.google.common.collect.ListMultimap;

import java.sql.*;
import java.util.*;
import java.util.Date;

/**
 * Created by yuraf_000 on 15.06.2014.
 */
public class DBConnector {
    private Connection waggrConnection = null;
    private String weatherTableNameYandex = "weatheryan";
    private String weatherTableNameWUA = "weatherwua";

    public DBConnector() {
        try {
            Class.forName("org.postgresql.Driver");
            waggrConnection = DriverManager.getConnection(
                    "jdbc:postgresql://127.0.0.1:5432/waggrdb", "waggr",
                    "admin");
            if (waggrConnection != null) System.out.println("Connection opened");
        } catch (SQLException e) {
            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
            return;

        } catch (ClassNotFoundException e) {
            return;
        }

    }

    public void DBConnectionClose() {
        try {
            waggrConnection.close();
            System.out.println("Connection closed");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection GetConnection() {
        return waggrConnection;
    }

    public void WriteWeatherDataYandex(HashMap<Integer, List<Weather>> CityWeatherListYandex, HashMap<Integer, String> CityIdMapYandex , HashMap<Integer,String> CountryIdMapYandex , ListMultimap<Integer,Integer> CountryCityMapYandex) {
        ClearTable(weatherTableNameYandex);
        ClearSequence("seq1");
        WriteWeatherData(weatherTableNameYandex, CityWeatherListYandex, CityIdMapYandex, CountryIdMapYandex, CountryCityMapYandex);

    }

    public void WriteWeatherDataWUA(HashMap<Integer, List<Weather>> CityWeatherListWUA, HashMap<Integer, String> CityIdMapWUA, HashMap<Integer,String> CountryIdMapWUA , ListMultimap<Integer,Integer> CountryCityMapWUA) {
        ClearTable(weatherTableNameWUA);
        ClearSequence("seq2");
        WriteWeatherData(weatherTableNameWUA, CityWeatherListWUA, CityIdMapWUA, CountryIdMapWUA, CountryCityMapWUA);

    }

    public boolean NewUser(String login, String password, String name, String surname, String cityName, String countryName) {
        try {

            Statement CheckUser = waggrConnection.createStatement();
            ResultSet Res = CheckUser.executeQuery("SELECT * FROM users WHERE login ='" + login + "';");
            if (Res.next()) {
                CheckUser.close();
                return false;
            }
            CheckUser.close();
            String stm = "INSERT INTO users(login, password, name, surname, city_name, country_name) VALUES(?, ?, ?, ?, ?, ?)";
            PreparedStatement tempPst = waggrConnection.prepareStatement(stm);
            tempPst.setString(1, login);
            tempPst.setString(2, password);
            tempPst.setString(3, name);
            tempPst.setString(4, surname);
            tempPst.setString(5, cityName);
            tempPst.setString(6, countryName);
            tempPst.executeUpdate();
            tempPst.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public User CheckUser(String login, String password) {
        try {
            Statement CheckLogin = waggrConnection.createStatement();
            ResultSet Res = CheckLogin.executeQuery("SELECT * FROM users WHERE login ='" + login + "'AND password = '" + password + "';");
            if (Res.next()) {
                Res.close();
                Statement GetCity = waggrConnection.createStatement();
                ResultSet UserData = GetCity.executeQuery("SELECT name, surname, city_name, country_name  FROM users WHERE login ='" + login + "'AND password = '" + password + "';");
                UserData.next();
                return new User(login, UserData.getString(1), UserData.getString(2), UserData.getString(3), UserData.getString(4));
            }
            Res.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Boolean ClearSequence(String sequenceName) {
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

    private Boolean ClearTable(String tableName) {
        try {
            Statement StDel = waggrConnection.createStatement();
            Boolean res = StDel.execute("DELETE FROM " + tableName + ";");
            // + ";"
            StDel.close();
            return res;
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return null;
    }

    private void WriteWeatherData (String tableName, HashMap<Integer, List<Weather>> CityWeatherList, HashMap<Integer, String> CityIdMap, HashMap<Integer,String> CountryIdMap , ListMultimap<Integer,Integer> CountryCityMap) {

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
                            Timestamp sqlDate = new Timestamp(tmpWeather.GetDate().getTime());
                            tempPst.setTimestamp(1, sqlDate);
                            tempPst.setInt(2, CityId);
                            tempPst.setString(3, CityIdMap.get(CityId));
                            tempPst.setInt(4, tmpWeather.GetTemperature());
                            tempPst.setInt(5, tmpWeather.GetPressure());
                            tempPst.setInt(6, tmpWeather.GetHumidity());
                            tempPst.setFloat(7, tmpWeather.GetWindSpeed());
                            tempPst.setString(8, tmpWeather.GetWindDirection());
                            tempPst.setString(9, CountryIdMap.get(CountryId));
                            tempPst.setBoolean(10, tmpWeather.GetIsPredict());
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

    public Weather GetCurrentWUA (String cityName,String countryName){
        return GetCurrentWeather(weatherTableNameWUA,cityName,countryName);
    }
    public Weather GetCurrentYandex (String cityName,String countryName){
        return GetCurrentWeather(weatherTableNameYandex, cityName, countryName);
    }

    private Weather GetCurrentWeather(String tableName, String cityName,String countryName){
        try {
            Statement CheckLogin = waggrConnection.createStatement();
            String query = String.format("SELECT timestamp, temperature, pressure, humidity, wind_speed, wind_direction FROM %s WHERE city_name = '%s' AND country_name = '%s' AND is_predict = FALSE;", tableName, cityName, countryName);
            ResultSet res = CheckLogin.executeQuery(query);
            if (res.next()){
                Weather weatherResult = new Weather();
                weatherResult.SetDate((Date) res.getTimestamp(1));
                weatherResult.SetTemperature(res.getInt(2));
                weatherResult.SetPressure(res.getInt(3));
                weatherResult.SetHumidity(res.getInt(4));
                weatherResult.SetWindSpeed(res.getFloat(5));
                weatherResult.SetWindDirection(res.getString(6));
                weatherResult.SetIsPredict(false);
                return weatherResult;
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }


//    public Weather GetCurrentYan(String cityname){
//
//    }

    public List<List<Weather>> GetForecastsByCityAndCountyName(String cityName,String countryName) {
        try {
            ResultSet rs = null;
            String query = "SELECT timestamp, temperature, pressure, humidity, wind_speed, wind_direction, is_predict FROM " + weatherTableNameYandex + " WHERE city_name = '" + cityName + "' AND country_name = '" +countryName+ "';" +
                           "SELECT timestamp, temperature, pressure, humidity, wind_speed, wind_direction, is_predict FROM " + weatherTableNameWUA + " WHERE city_name = '" + cityName + "' AND country_name = '" +countryName+ "';";
            PreparedStatement pst = waggrConnection.prepareStatement(query);
            boolean isResult = pst.execute();
            List<List<Weather>> resultWeatherLists = new ArrayList<>();
            do {
                List<Weather> WeatherList = new ArrayList<>();
                rs = pst.getResultSet();
                while (rs.next()) {
//                    String result = String.format("Forecast date %s  temperature %d C*, air pressure %d mm, humidity %d, wind %.1f %s", rs.getTimestamp(1).toString(), rs.getInt(2), rs.getInt(3), rs.getInt(4), rs.getFloat(5), rs.getString(6));
//                    System.out.println(result);
                    Weather weatherResult = new Weather();
                    weatherResult.SetDate((Date) rs.getTimestamp(1));
                    weatherResult.SetTemperature(rs.getInt(2));
                    weatherResult.SetPressure(rs.getInt(3));
                    weatherResult.SetHumidity(rs.getInt(4));
                    weatherResult.SetWindSpeed(rs.getFloat(5));
                    weatherResult.SetWindDirection(rs.getString(6));
                    weatherResult.SetIsPredict(rs.getBoolean(7));
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
    public Boolean CheckCity (String cityName, String countryName){
        try {
            String query1 = String.format("SELECT * FROM %s WHERE city_name = '%s' AND country_name = '%s';",weatherTableNameWUA,cityName,countryName);
            String query2 = String.format("SELECT * FROM %s WHERE city_name = '%s' AND country_name = '%s';",weatherTableNameYandex,cityName,countryName);
//            String query = "SELECT * FROM "+weatherTableNameWUA+" WHERE city_name ='" + cityName + "';" +
//                    "SELECT * FROM "+weatherTableNameYandex+" WHERE city_name ='" + cityName + "';";
            PreparedStatement pst = waggrConnection.prepareStatement(query1+query2);
            Boolean isResult = pst.execute();
            do {
                if (pst.getResultSet().next()) return true;
                else {
                    pst.getMoreResults();
                    return pst.getResultSet().next();
                }

            }while (isResult);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}