package DataAccessLayer;

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

    public void connectionClose() {
        try {
            waggrConnection.close();
            System.out.println("Connection closed");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return waggrConnection;
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

    public boolean newUser(String login, String password, String name, String surname, String cityName, String countryName) {
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

    public User сheckUser(String login, String password) {
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
    public User getUser (String login){
        try {
            Statement CheckLogin = waggrConnection.createStatement();
            ResultSet Res = CheckLogin.executeQuery("SELECT * FROM users WHERE login ='" + login + "';");
            if (Res.next()) {
                Res.close();
                Statement GetCity = waggrConnection.createStatement();
                ResultSet UserData = GetCity.executeQuery("SELECT name, surname, city_name, country_name  FROM users WHERE login ='" + login +  "';");
                UserData.next();
                return new User(login, UserData.getString(1), UserData.getString(2), UserData.getString(3), UserData.getString(4));
            }
            Res.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
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
            // + ";"
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
    public Weather getCurrentYandex(String cityName, String countryName){
        return getCurrentWeather(weatherTableNameYandex, cityName, countryName);
    }

    private Weather getCurrentWeather(String tableName, String cityName, String countryName){
        try {
            Statement CheckLogin = waggrConnection.createStatement();
            String query = String.format("SELECT timestamp, temperature, pressure, humidity, wind_speed, wind_direction FROM %s WHERE city_name = '%s' AND country_name = '%s' AND is_predict = FALSE;", tableName, cityName, countryName);
            ResultSet res = CheckLogin.executeQuery(query);
            if (res.next()){
                Weather weatherResult = new Weather();
                weatherResult.setDate((Date) res.getTimestamp(1));
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


//    public Weather GetCurrentYan(String cityname){
//
//    }

    public List<List<Weather>> getForecastsByCityAndCountyName(String cityName, String countryName) {
        try {
            ResultSet rs = null;
            String query1 = String.format("SELECT timestamp, temperature, pressure, humidity, wind_speed, wind_direction FROM %s WHERE city_name = '%s' AND country_name = '%s' AND is_predict = 'TRUE' ORDER BY timestamp;",weatherTableNameYandex,cityName,countryName);
            String query2 = String.format("SELECT timestamp, temperature, pressure, humidity, wind_speed, wind_direction FROM %s WHERE city_name = '%s' AND country_name = '%s' AND is_predict = 'TRUE' ORDER BY timestamp;",weatherTableNameWUA,cityName,countryName);
            //String query = "SELECT timestamp, temperature, pressure, humidity, wind_speed, wind_direction, is_predict FROM " + weatherTableNameYandex + " WHERE city_name = '" + cityName + "' AND country_name = '" +countryName+ "';" +
            //              "SELECT timestamp, temperature, pressure, humidity, wind_speed, wind_direction, is_predict FROM " + weatherTableNameWUA + " WHERE city_name = '" + cityName + "' AND country_name = '" +countryName+ "';";
            PreparedStatement pst = waggrConnection.prepareStatement(query1+query2);
            boolean isResult = pst.execute();
            List<List<Weather>> resultWeatherLists = new ArrayList<>();
            do {
                List<Weather> WeatherList = new ArrayList<>();
                rs = pst.getResultSet();
                while (rs.next()) {
//                    String result = String.format("Forecast date %s  temperature %d C*, air pressure %d mm, humidity %d, wind %.1f %s", rs.getTimestamp(1).toString(), rs.getInt(2), rs.getInt(3), rs.getInt(4), rs.getFloat(5), rs.getString(6));
//                    System.out.println(result);
                    Weather weatherResult = new Weather();
                    weatherResult.setDate((Date) rs.getTimestamp(1));
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
    public List<String> CheckCity (String cityName){
        try {
            List<String>countryNameList = new ArrayList<>();
            String query1 = String.format("SELECT country_name FROM %s WHERE city_name = '%s';",weatherTableNameWUA,cityName);
            String query2 = String.format("SELECT country_name FROM %s WHERE city_name = '%s';",weatherTableNameYandex,cityName);
//            String query = "SELECT * FROM "+weatherTableNameWUA+" WHERE city_name ='" + cityName + "';" +
//                    "SELECT * FROM "+weatherTableNameYandex+" WHERE city_name ='" + cityName + "';";
            PreparedStatement pst = waggrConnection.prepareStatement(query1+query2);
            Boolean isResult = pst.execute();
            do {
                ResultSet resSet = pst.getResultSet();
                while(resSet.next()){
                    String countryName = resSet.getString(1);
                    if (!countryNameList.contains(countryName)) countryNameList.add(resSet.getString(1));
                }
                isResult = pst.getMoreResults();
            }while (isResult);
            return countryNameList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public boolean setUserCity(String userLogin, String cityName, String countryName){

        try {
            Statement CheckUser = waggrConnection.createStatement();
            ResultSet Res = CheckUser.executeQuery("SELECT * FROM users WHERE login ='" + userLogin + "';");
            if (!Res.next()) {
                CheckUser.close();
                return false;
            }
            CheckUser.close();
            String stm = String.format("UPDATE users SET city_name = '%s', country_name = '%s' WHERE login = '%s';",cityName,countryName,userLogin);
            Statement pst = waggrConnection.createStatement();
            pst.execute(stm);
            pst.close();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}