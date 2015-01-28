import PresentationLayer.AuthorizationForm;
import ServiceLayer.WeatherForecastUpdateService;

import javax.swing.*;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 * Created by URI on 12.01.2015.
 */
public class Client {
    public static void main(String[] args){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                AuthorizationForm AForm = null;
                try {
                    AForm = new AuthorizationForm(null,null,null,null,null);
                } catch (RemoteException e) {
                    e.printStackTrace();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (NotBoundException e) {
                    e.printStackTrace();
                }
                AForm.setVisible(true);
            }
        });
    }
}
