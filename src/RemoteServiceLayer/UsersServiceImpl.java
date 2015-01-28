package RemoteServiceLayer;

import BusinessLogic.User;
import BusinessLogic.UserWorker;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

/**
* Created by yuraf_000 on 25.12.2014.
*/
public class UsersServiceImpl extends UnicastRemoteObject implements UsersService {
    private UserWorker userWorker = new UserWorker();
    private User mainUser = null;

    public UsersServiceImpl() throws RemoteException {
    }

    public boolean newUser(String login, String password, String name, String surname, String cityName, String countryName) throws RemoteException {
        return userWorker.newUser(login,password,name,surname,cityName,countryName);
    }


    public boolean updatePassword(String login, String oldPassword, String newPassword) throws RemoteException {
       return userWorker.updateUserPassword(login,oldPassword,newPassword);
    }

    public Object login(String login, String password) throws RemoteException {
        if (userWorker.checkAuthorization(login,password)) {
            return userWorker.getUser(login);
        }
        return null;
    }

    public boolean setCurrentUserLocation(String cityName, String countryName) throws RemoteException {
        if (userWorker.updateUserCityAndCountry(mainUser.getUserLogin(),cityName,countryName)) {
            reloadMainUser();
            return true;
        } else {
            return false;
        }
    }

    public List<User> getUsersList() throws RemoteException {
        return userWorker.getUserList();
    }

    public boolean updateUser(String userLogin, User newUser) throws RemoteException {
        return userWorker.updateUser(userLogin,newUser);
    }

    private void reloadMainUser() throws RemoteException  {
        mainUser = userWorker.getUser(mainUser.getUserLogin());
    }

    public void setCurrentUser(Object user) throws RemoteException  {
        mainUser = (User) user;
    }

    public User getMainUser() throws RemoteException  {
        return mainUser;
    }

    public String getCurrentUserLogin() throws RemoteException { return mainUser.getUserLogin(); }

    public String getCurrentUserCity() throws RemoteException  {
        return mainUser.getUserCity();
    }

    public String getCurrentUserCountry() throws RemoteException  {
        return mainUser.getUserCountry();
    }

    public String getCurrentUserName() throws RemoteException  {
        return mainUser.getUserName();
    }

    public String getCurrentUserSurname() throws RemoteException  {
        return mainUser.getUserSurname();
    }

//    public void close() throws RemoteException  {
//        userWorker.close();
//    }
}
