package DataAccessLayer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by URI on 14.01.2015.
 */
public class CityGateway {
    private Connection waggrConnection = null;
    private String weatherTableNameYandex = "weatheryan";
    private String weatherTableNameWUA = "weatherwua";

    public CityGateway() {
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
    public String getCityNameWUA (int cityId) {
        try {
            Statement getName = waggrConnection.createStatement();
            String query = String.format("SELECT city_name FROM %s WHERE city_id = '%s';", weatherTableNameWUA, cityId);
            ResultSet res = getName.executeQuery(query);
            if (res.next()){
                return  res.getString(1);
            }
            return null;
        } catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public String getCityNameYandex (int cityId) {
        try {
            Statement getName = waggrConnection.createStatement();
            String query = String.format("SELECT city_name FROM %s WHERE city_id = '%s';", weatherTableNameYandex, cityId);
            ResultSet res = getName.executeQuery(query);
            if (res.next()){
                return  res.getString(1);
            }
            return null;
        } catch (SQLException e){
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
}
