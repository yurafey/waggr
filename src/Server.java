import APILayer.HttpServer;
import RemoteServiceLayer.*;
import ServiceLayer.WeatherCurrentTableService;
import ServiceLayer.WeatherForecastTableService;
import ServiceLayer.WeatherForecastUpdateService;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.text.ParseException;

public class Server {

    public static void main(String[] args) throws ParseException, RemoteException, MalformedURLException {

        //?????
        //System.setSecurityManager(new RMISecurityManager());

        AdminServiceImpl adminServiceImpl = new AdminServiceImpl();
        adminServiceImpl.initializeAdminService(new WeatherForecastUpdateService());
        Naming.rebind("//127.0.0.1/AdminService", adminServiceImpl);

        RealFeelServiceImpl realFeelServiceImpl = new RealFeelServiceImpl();
        Naming.rebind("//127.0.0.1/RealFeelService", realFeelServiceImpl);

        UsersServiceImpl usersServiceImpl = new UsersServiceImpl();
        Naming.rebind("//127.0.0.1/UsersService", usersServiceImpl);

        WeatherServiceImpl weatherServiceImpl = new WeatherServiceImpl();
        Naming.rebind("//127.0.0.1/WeatherService",weatherServiceImpl);

//        WeatherCurrentTableService weatherCurrentTableServiceImpl = new WeatherCurrentTableService();
//        Naming.rebind("//127.0.0.1/WeatherCurrentTableService", weatherCurrentTableServiceImpl);
//
//        WeatherForecastTableService weatherForecastTableService = new WeatherForecastTableService();
//        Naming.rebind("//127.0.0.1/WeatherForecastTableService", weatherForecastTableService);

        System.out.println("Ready to serve");

        HttpServer httpServer = new HttpServer();
        new Thread(httpServer).start();

    }
}











