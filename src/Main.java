import GUI.AuthorizationForm;
import ServiceLayer.WeatherForecastUpdateService;

import javax.swing.*;
import java.text.ParseException;

public class Main {

    public static void main(String[] args) throws ParseException {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                AuthorizationForm MForm = new AuthorizationForm(new WeatherForecastUpdateService());
                MForm.setVisible(true);
            }
        });

//        DBConnector dbConnector = new DBConnector();
//        String login = "user1";
//        Timestamp timestamp = new Timestamp(new Date().getTime());
//        String cityName = "Cанкт-Петербург";
//        String countryName = "Россия";
//        int temperature = -18;
//        int pressure = 777;
//        int humidity = 89;
//        float windSpeed = new Float("1.5");
//        String windDirection = "nne";
//        dbConnector.newRealFeel(login,timestamp,cityName,countryName,temperature,pressure,humidity,windSpeed,windDirection);
    }
}











