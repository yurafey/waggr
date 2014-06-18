package DBProcessor;

import Parsers.Weather;

import java.sql.*;
import java.util.HashMap;
import java.util.List;

/**
 * Created by yuraf_000 on 15.06.2014.
 */
public class DBConnector {
    private Connection waggrConnection = null;
    private String WeatherTableNameYandex = "weatheryan";
    private String WeatherTableNameWUA = "weatherwua";

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

    public void WriteWeatherDataYandex(HashMap<Integer, List<Weather>> CityWeatherListYandex, HashMap<Integer,String> CityIdMapYandex){
        ClearTable(WeatherTableNameYandex);
        ClearSequence("auto_wid_inc");
        WriteToOneTableFromCWLAndIdMap(WeatherTableNameYandex,CityWeatherListYandex,CityIdMapYandex);
    }
    public void WriteWeatherDataWUA (HashMap<Integer, List<Weather>> CityWeatherListWUA, HashMap<Integer,String> CityIdMapWUA){
        ClearTable(WeatherTableNameWUA);
        ClearSequence("auto_wid_inc2");
        WriteToOneTableFromCWLAndIdMap(WeatherTableNameWUA, CityWeatherListWUA,CityIdMapWUA);
    }

    private void ClearSequence (String sequenceName){
        try {
            Statement StClear = waggrConnection.createStatement();
            StClear.execute("SELECT SETVAL('" + sequenceName + "', 1, false);");
            StClear.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void ClearTable (String tableName){
        try {
            Statement StDel = waggrConnection.createStatement();
            StDel.execute("DELETE FROM "+tableName+";");
            StDel.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void WriteToOneTableFromCWLAndIdMap(String tableName ,HashMap<Integer, List<Weather>> CityWeatherList, HashMap<Integer,String> CityIdMap) {

        try {

            String stm = "INSERT INTO "+tableName+"(timestamp, town_id, town_name, temperature, pressure, humidity, wind_speed, wind_direction) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement tempPst = waggrConnection.prepareStatement(stm);
            for(Integer CityId: CityWeatherList.keySet()){
                List<Weather> tmpWeatherList = CityWeatherList.get(CityId);
                for (int i = 0; i < tmpWeatherList.size(); i++){
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
                    tempPst.executeUpdate();
                }

            }
            tempPst.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
