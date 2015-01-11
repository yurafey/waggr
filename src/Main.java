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
//        System.out.println(new Timestamp(new java.util.Date().getTime()));
//
//
//        int port = 9999;
//        ServerSocket serverSocket = null;
//        try {
//            serverSocket = new ServerSocket(port);
//            Socket clientSocket = serverSocket.accept();
//            InputStream in = null;
//            OutputStream out = null;
//            in = clientSocket.getInputStream();
//            out = clientSocket.getOutputStream();
//            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
//            StringBuilder builder = new StringBuilder();
//            String ln = null;
//            while (true) {
//                ln = reader.readLine();
//                if (ln == null || ln.isEmpty()) {
//                    break;
//                }
//                builder.append(ln + System.getProperty("line.separator"));
//            }
//            System.out.println(builder.toString());
//            String header = builder.toString();
//            int from = header.indexOf("/")+1;
//            int to = header.indexOf("/", from);
//            String weatherProvider = header.substring(from,to);
//            System.out.println("Start:"+weatherProvider+" end");
//
//            if (!(weatherProvider.equals("WUA")||weatherProvider.equals("Yandex"))) {
//                System.out.println("no!1");
//                return;
//            }
//            int cityIdEndIndex = header.indexOf(" ",to);
//            if (cityIdEndIndex!=to+1) {
//                String cityId =  header.substring(to+1,cityIdEndIndex);
//                System.out.println("start:"+cityId+" end");
//                return;
//            }
//            System.out.println("no!2");
//            return;
//
//        } catch (IOException e1) {
//            e1.printStackTrace();
//        }


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











