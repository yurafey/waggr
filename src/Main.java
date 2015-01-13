import APILayer.HttpServer;
import PresentationLayer.AuthorizationForm;
import ServiceLayer.WeatherForecastUpdateService;

import javax.swing.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
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
        HttpServer httpServer = new HttpServer();
        new Thread(httpServer).start();
    }
}











