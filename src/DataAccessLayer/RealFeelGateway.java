package DataAccessLayer;

import BusinessLogic.RealFeel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by URI on 14.01.2015.
 */
public class RealFeelGateway {
    private Connection waggrConnection = null;
    public RealFeelGateway() {
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
            System.out.println(newRealFeel.toString());
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
//            String stm = String.format("SELECT login, timestamp, city_name, country_name, temperature, pressure, " +
//                    "humidity, wind_speed, wind_direction FROM realfeel WHERE city_name = '%s' AND country_name = '%s' ORDER BY timestamp LIMIT %s;",cityName,countryName,numOfRes);
            String stm = String.format("SELECT login, timestamp, temperature, pressure, " +
                    "humidity, wind_speed, wind_direction FROM realfeel WHERE city_name = '%s' AND country_name = '%s' ORDER BY timestamp DESC LIMIT %s;",cityName,countryName,numOfRes);
            //System.out.println(stm);
            ResultSet res = statement.executeQuery(stm);
            List<RealFeel> result = new ArrayList<>();
            while (res.next()){
                //System.out.println("!!!");
                RealFeel tempRealFeel = new RealFeel();
                tempRealFeel.setUserLogin(res.getString(1));
                tempRealFeel.setDate(res.getTimestamp(2));
                tempRealFeel.setCityName(cityName);
                tempRealFeel.setCountryName(countryName);
                tempRealFeel.setTemperature(res.getInt(3));
                tempRealFeel.setPressure(res.getInt(4));
                tempRealFeel.setHumidity(res.getInt(5));
                tempRealFeel.setWindSpeed(res.getFloat(6));
                tempRealFeel.setWindDirection(res.getString(7));
                result.add(tempRealFeel);
            }
            if (result.size()!=0) {
                statement.close();
                System.out.println("ress111");
                System.out.println(result);
                return result;
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
