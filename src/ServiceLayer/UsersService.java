package ServiceLayer;

import BusinessLogic.User;
import BusinessLogic.UserWorker;

import java.util.List;

/**
 * Created by yuraf_000 on 25.12.2014.
 */
public class UsersService {
    private UserWorker userWorker = new UserWorker();
    private User mainUser = null;

    public boolean newUser(String login, String password, String name, String surname, String cityName, String countryName) {
        return userWorker.newUser(login,password,name,surname,cityName,countryName);
    }


    public boolean updatePassword(String login, String oldPassword, String newPassword) {
       return userWorker.updateUserPassword(login,oldPassword,newPassword);
    }

    public Object login(String login, String password) {
        if (userWorker.checkAuthorization(login,password)) {
            return userWorker.getUser(login);
        }
        return null;
    }

    public boolean setCurrentUserLocation(String cityName, String countryName) {
        if (userWorker.updateUserCityAndCountry(mainUser.getUserLogin(),cityName,countryName)) {
            reloadMainUser();
            return true;
        } else {
            return false;
        }
    }

    public List<User> getUsersList() {
        return userWorker.getUserList();
    }

    public boolean updateUser(String userLogin, User newUser) {
        return userWorker.updateUser(userLogin,newUser);
    }

    private void reloadMainUser() {
        mainUser = userWorker.getUser(mainUser.getUserLogin());
    }

    public void setCurrentUser(Object user) {
        mainUser = (User) user;
    }

    public User getMainUser() {
        return mainUser;
    }

    public String getCurrentUserLogin() { return mainUser.getUserLogin(); }

    public String getCurrentUserCity() {
        return mainUser.getUserCity();
    }

    public String getCurrentUserCountry() {
        return mainUser.getUserCountry();
    }

    public String getCurrentUserName() {
        return mainUser.getUserName();
    }

    public String getCurrentUserSurname() {
        return mainUser.getUserSurname();
    }

    public void close() {
        userWorker.close();
    }
}
