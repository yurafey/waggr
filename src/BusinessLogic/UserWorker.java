package BusinessLogic;

import DataAccessLayer.UserGateway;
import java.util.List;

/**
 * Created by yuraf_000 on 03.12.2014.
 */
public class UserWorker {
    UserGateway userGateway = new UserGateway();

    public boolean checkUserLogin(String login) {
        if (userGateway.getUser(login)!=null) {
            return true;
        } else {
            return false;
        }
    }

    public User getUser(String login) {
        User newUser = userGateway.getUser(login);
        if (newUser!=null){
            return newUser;
        } else {
            return null;
        }
    }

    public boolean checkAuthorization(String login, String password) {
        String dbPassword = userGateway.getPassword(login);
        if (dbPassword!=null) {
            if (password.equals(dbPassword)) return true;
        }
        return false;
    }

    public boolean updateUser(String login, User newUser) {
        if (checkUserLogin(login)) {
            if (userGateway.updateUser(login,newUser)) {
                return true;
            }
        }
        return false;
    }

    public boolean newUser(String login, String password, String name, String surname, String cityName, String countryName) {
        if (!checkUserLogin(login)) {
            if (userGateway.newUser(login,password,name,surname,cityName,countryName)) {
                return true;
            }
        }
        return false;
    }

    public boolean updateUserPassword(String login, String oldPassword, String newPassword) {
        if(checkAuthorization(login, oldPassword)) {
            if (userGateway.updatePassword(login,newPassword)) {
                return true;
            }
        }
        return false;
    }

    public boolean updateUserCityAndCountry(String login, String cityName, String countryName) {
        if (checkUserLogin(login)) {
            if (userGateway.updateUserLocation(login, cityName, countryName)) {
                return true;
            }
        }
        return false;
    }

    public boolean deleteUser(String userLogin) {
        if (checkUserLogin(userLogin)) {
            return userGateway.deleteUser(userLogin);
        }
        return false;
    }

    public List<User> getUserList() {
        return userGateway.getUsersList();
    }

    public void close() {
        userGateway.connectionClose();
    }
}
