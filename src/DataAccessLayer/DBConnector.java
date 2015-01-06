package DataAccessLayer;

import BusinessLogic.RealFeel;
import BusinessLogic.User;
import BusinessLogic.Weather;
import com.google.common.collect.ListMultimap;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

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


    public boolean newRealFeel(String login, Timestamp timestamp, String city_name, String country_name, int temperature, int pressure, int humidity, float wind_speed, String wind_direction) {
        try {
            String stm = "INSERT INTO realfeel(login, timestamp, city_name, temperature, pressure, humidity , wind_speed, wind_direction, country_name) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement newRealFeel = waggrConnection.prepareStatement(stm);
            newRealFeel.setString(1,login);
            newRealFeel.setTimestamp(2,timestamp);
            newRealFeel.setString(3,city_name);
            newRealFeel.setInt(4,temperature);
            newRealFeel.setInt(5,pressure);
            newRealFeel.setInt(6,humidity);
            newRealFeel.setFloat(7,wind_speed);
            newRealFeel.setString(8,wind_direction);
            newRealFeel.setString(9,country_name);
            int i = newRealFeel.executeUpdate();
            if (i!=0) {
                newRealFeel.close();
                return true;
            }
            newRealFeel.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<RealFeel> getRealFeel(String cityName, String countryName, int numOfRes) {
        try {
            Statement statement = waggrConnection.createStatement();
            String stm = String.format("SELECT login, timestamp, city_name, country_name, temperature, pressure, " +
                    "humidity, wind_speed, wind_direction FROM realfeel WHERE city_name = '%s' AND country_name = '%s' ORDER BY timestamp LIMIT %s;",cityName,countryName,numOfRes);
            System.out.println(stm);
            ResultSet res = statement.executeQuery(stm);
            List<RealFeel> result = new ArrayList<>();

            while (res.next()){
                System.out.println("!!!");
                RealFeel tempRealFeel = new RealFeel();
                tempRealFeel.setUserLogin(res.getString(1));
                tempRealFeel.setDate(res.getDate(2));
                tempRealFeel.setCityName(res.getString(3));
                tempRealFeel.setCountryName(res.getString(4));
                tempRealFeel.setTemperature(res.getInt(5));
                tempRealFeel.setPressure(res.getInt(6));
                tempRealFeel.setHumidity(res.getInt(7));
                tempRealFeel.setWindSpeed(res.getFloat(8));
                tempRealFeel.setWindDirection(res.getString(9));
                result.add(tempRealFeel);
            }
            if (result.size()!=0) {
                statement.close();
                System.out.println(result);
                return result;
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean newUser(String login, String password, String name, String surname, String cityName, String countryName) {
        try {
            String stm = "INSERT INTO users(login, password, name, surname, city_name, country_name) VALUES(?, ?, ?, ?, ?, ?)";
            PreparedStatement tempPst = waggrConnection.prepareStatement(stm);
            tempPst.setString(1, login);
            tempPst.setString(2, password);
            tempPst.setString(3, name);
            tempPst.setString(4, surname);
            tempPst.setString(5, cityName);
            tempPst.setString(6, countryName);
            int i = tempPst.executeUpdate();
            if (i!=0) {
                tempPst.close();
                return true;
            }
            tempPst.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<User> getUsersList () {
        try {
            List<User> usersList = new ArrayList<>();
            Statement getUsersList = waggrConnection.createStatement();
            String query = "SELECT * FROM users;";
            ResultSet res = getUsersList.executeQuery(query);
            while (res.next()) {
                String userLogin = res.getString(1);
                String userPassword = res.getString(2);
                String userName = res.getString(3);
                String userSurname = res.getString(4);
                String userCity = res.getString(5);
                String userCountry = res.getString(6);
                User newUser = new User(userLogin,userPassword,userName,userSurname,userCity,userCountry);
                usersList.add(newUser);
            }
            return usersList;

        } catch (SQLException e) {
            System.out.println("getUserList exception");
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


    public String getCurrentCountries() {
        try {
            Statement CheckLogin = waggrConnection.createStatement();
            ResultSet Res = CheckLogin.executeQuery("SELECT country_name FROM users WHERE login ='admin';");
            if (Res.next()) return Res.getString(1);
            Res.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public String getCurrentPeriod() {
        try {
            Statement CheckLogin = waggrConnection.createStatement();
            ResultSet Res = CheckLogin.executeQuery("SELECT city_name FROM users WHERE login ='admin';");
            if (Res.next()) return Res.getString(1);
            Res.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean setCurrentCountries(String countriesList) {
        try {
            String stm = "UPDATE users SET country_name = ? WHERE login = 'admin'";
            PreparedStatement pst  = waggrConnection.prepareStatement(stm);
            pst.setString(1,countriesList);
            int i = pst.executeUpdate();
            if (i!=0) {
                pst.close();
                return true;
            }
            pst.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public boolean setCurrentPeriod(String period) {
        try {
            String stm = "UPDATE users SET city_name = ? WHERE login = 'admin'";
            PreparedStatement pst = waggrConnection.prepareStatement(stm);
            pst.setString(1,period);
            int i = pst.executeUpdate();
            if (i!=0) {
                pst.close();
                return true;
            }
            pst.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
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
    public Weather getCurrentYandex(String cityName, String countryName){
        return getCurrentWeather(weatherTableNameYandex, cityName, countryName);
    }

    private Weather getCurrentWeather(String tableName, String cityName, String countryName){
        try {
            Statement getWeather = waggrConnection.createStatement();
            String query = String.format("SELECT timestamp, temperature, pressure, humidity, wind_speed, wind_direction FROM %s WHERE city_name = '%s' AND country_name = '%s' AND is_predict = FALSE;", tableName, cityName, countryName);
            ResultSet res = getWeather.executeQuery(query);
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

    public Boolean checkCity(String cityName, String countryName) {
        try {
            String query1 = String.format("SELECT * FROM %s WHERE city_name = '%s' AND country_name = '%s';", weatherTableNameWUA, cityName, countryName);
            String query2 = String.format("SELECT * FROM %s WHERE city_name = '%s' AND country_name = '%s';", weatherTableNameYandex, cityName, countryName);
            PreparedStatement pst = waggrConnection.prepareStatement(query1 + query2);
            pst.execute();
            if (pst.getResultSet().next()) return true;
            else {
                pst.getMoreResults();
                return pst.getResultSet().next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public List<String> checkCity(String cityName){
        try {
            List<String>countryNameList = new ArrayList<>();
            String query1 = String.format("SELECT country_name FROM %s WHERE city_name = '%s';",weatherTableNameWUA,cityName);
            String query2 = String.format("SELECT country_name FROM %s WHERE city_name = '%s';",weatherTableNameYandex,cityName);
            PreparedStatement pst = waggrConnection.prepareStatement(query1+query2);
            boolean isResult = pst.execute();
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

    public boolean updateUser(String userLogin, User newUser) {
        try {
            String stm = String.format("UPDATE users SET login = '%s', password = '%s',name = '%s', surname = '%s', city_name = '%s', country_name = '%s' WHERE login = '%s';",
                    newUser.getUserLogin(),newUser.getUserPassword(),newUser.getUserName(),newUser.getUserSurname(),newUser.getUserCity(),newUser.getUserCountry(),userLogin);
            Statement updateUser = waggrConnection.createStatement();
            int i = updateUser.executeUpdate(stm);
            if (i != 0) {
                updateUser.close();
                return true;
            }
            updateUser.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("update user exception");
        }
        return false;
    }

    public boolean updateUserLocation(String userLogin, String cityName, String countryName) {
        try {
            Statement updateLocation = waggrConnection.createStatement();
            String stm = String.format("UPDATE users SET city_name = '%s', country_name = '%s' WHERE login = '%s';",cityName,countryName,userLogin);
            int i = updateLocation.executeUpdate(stm);
            if (i!=0){
                updateLocation.close();
                return true;
            }
            updateLocation.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Boolean updatePassword(String login, String newPass) {
        try {
            Statement updatePassword = waggrConnection.createStatement();
            String stm = String.format("UPDATE users SET password = '%s' WHERE login = '%s';",newPass,login);
            int i = updatePassword.executeUpdate(stm);
            if (i==1) {
                updatePassword.close();
                return true;
            }
            updatePassword.close();
        }catch (SQLException e) {
           e.printStackTrace();
        }
        return false;
    }

    public String getPassword(String login) {
        try {
            Statement getPassword = waggrConnection.createStatement();
            String stm = String.format("SELECT password FROM users WHERE login = '%s';",login);
            ResultSet result = getPassword.executeQuery(stm);
            if (result.next()) {
                String res = result.getString(1);
                getPassword.close();
                return res;
            }
            getPassword.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


}