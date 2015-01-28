package RemoteServiceLayer;

import BusinessLogic.User;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * Created by URI on 12.01.2015.
 */
public interface UsersService extends Remote {
    public boolean updatePassword(String login, String oldPassword, String newPassword) throws RemoteException;
    public Object login(String login, String password) throws RemoteException;
    public boolean setCurrentUserLocation(String cityName, String countryName) throws RemoteException;
    public boolean newUser(String login, String password, String name, String surname, String cityName, String countryName) throws RemoteException;
    public List<User> getUsersList() throws RemoteException;
    public boolean updateUser(String userLogin, User newUser) throws RemoteException;
    public void setCurrentUser(Object user) throws RemoteException;
    public User getMainUser() throws RemoteException;
    public String getCurrentUserLogin() throws RemoteException;
    public String getCurrentUserCity() throws RemoteException;
    public String getCurrentUserCountry() throws RemoteException;
    public String getCurrentUserName() throws RemoteException;
    public String getCurrentUserSurname() throws RemoteException;
    //public void close() throws RemoteException;
}
