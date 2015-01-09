import DataAccessLayer.DBConnector;
import GUI.AuthorizationForm;
import ServiceLayer.RealFeelService;
import ServiceLayer.WeatherForecastUpdateService;

import javax.swing.*;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Date;

public class Main {

    public static void main(String[] args) throws ParseException {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                AuthorizationForm MForm = new AuthorizationForm(new WeatherForecastUpdateService());
                MForm.setVisible(true);
            }
        });
        System.out.println(new Timestamp(new java.util.Date().getTime()));

//        DBConnector dbConnector = new DBConnector();
//        String login = "user1";
//        Timestamp timestamp = new Timestamp(new Date().getTime());
//        String cityName = "Санкт-Петербург";
//        String countryName = "Россия";
//        int temperature = -33;
//        int pressure = 777;
//        int humidity = 89;
//        float windSpeed = new Float("1.5");
//        String windDirection = "nne";
//        dbConnector.newRealFeel(login,timestamp,cityName,countryName,temperature,pressure,humidity,windSpeed,windDirection);
//        RealFeelService r = new RealFeelService("user1");
//        System.out.println(r.getRealFeels(1).toString());
    }
}











