package BusinessLogic;

import DataAccessLayer.DBConnector;

import java.util.List;

/**
 * Created by yuraf_000 on 03.12.2014.
 */
public class UserWorker {
    DBConnector db = new DBConnector();

    public boolean checkUserLogin(String login) {
        if (db.getUser(login)!=null) {
            return true;
        } else {
            return false;
        }
    }

    public User getUser(String login) {
        User newUser = db.getUser(login);
        if (newUser!=null){
            return newUser;
        } else {
            return null;
        }
    }

    public boolean checkAuthorization(String login, String password) {
        String dbPassword = db.getPassword(login);
        if (dbPassword!=null) {
            if (password.equals(dbPassword)) return true;
        }
        return false;
    }

    public boolean updateUser(String login, User newUser) {
        if (checkUserLogin(login)) {
            if (db.updateUser(login,newUser)) {
                return true;
            }
        }
        return false;
    }

    public boolean newUser(String login, String password, String name, String surname, String cityName, String countryName) {
        if (!checkUserLogin(login)) {
            if (db.newUser(login,password,name,surname,cityName,countryName)) {
                return true;
            }
        }
        return false;
    }

    public boolean updateUserPassword(String login, String oldPassword, String newPassword) {
        if(checkAuthorization(login, oldPassword)) {
            if (db.updatePassword(login,newPassword)) {
                return true;
            }
        }
        return false;
    }

    public boolean updateUserCityAndCountry(String login, String cityName, String countryName) {
        if (checkUserLogin(login)) {
            if (db.updateUserLocation(login, cityName, countryName)) {
                return true;
            }
        }
        return false;
    }

    public List<User> getUserList() {
        return db.getUsersList();
    }

    public void close() {
        db.connectionClose();
    }
}
