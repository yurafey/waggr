package DataAccessLayer;

import BusinessLogic.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by URI on 14.01.2015.
 */
public class UserGateway {
    private Connection waggrConnection = null;
    public UserGateway(){
        try {
            Class.forName("org.postgresql.Driver");
            waggrConnection = DriverManager.getConnection(
                    "jdbc:postgresql://127.0.0.1:5432/waggrdb", "waggr",
                    "waggr");
        } catch (SQLException e) {
            System.out.println("Connection Failed!");
            e.printStackTrace();
            return;

        } catch (ClassNotFoundException e) {
            return;
        }
    }

    public void connectionClose() {
        try {
            waggrConnection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean newUser(String login, String password, String name, String surname, String cityName, String countryName) {
        try {
            String stm = "INSERT INTO users(login, password, name, surname, city_name, country_name) VALUES(?, ?, ?, ?, ?, ?)";
            PreparedStatement tempPst = waggrConnection.prepareStatement(stm);
            tempPst.setString(1, login);
            tempPst.setString(2, password);
            tempPst.setString(3, name);
            tempPst.setString(4, surname);
            tempPst.setString(5, cityName);
            tempPst.setString(6, countryName);
            int i = tempPst.executeUpdate();
            if (i!=0) {
                tempPst.close();
                return true;
            }
            tempPst.close();
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return false;
    }

    public List<User> getUsersList () {
        try {
            List<User> usersList = new ArrayList<>();
            Statement getUsersList = waggrConnection.createStatement();
            String query = "SELECT * FROM users;";
            ResultSet res = getUsersList.executeQuery(query);
            while (res.next()) {
                String userLogin = res.getString(1);
                String userPassword = res.getString(2);
                String userName = res.getString(3);
                String userSurname = res.getString(4);
                String userCity = res.getString(5);
                String userCountry = res.getString(6);
                User newUser = new User(userLogin,userPassword,userName,userSurname,userCity,userCountry);
                usersList.add(newUser);
            }
            return usersList;

        } catch (SQLException e) {
            System.out.println("getUserList exception");
        }
        return null;
    }

    public User getUser (String login){
        try {
            Statement CheckLogin = waggrConnection.createStatement();
            ResultSet Res = CheckLogin.executeQuery("SELECT * FROM users WHERE login ='" + login + "';");
            if (Res.next()) {
                Res.close();
                Statement GetCity = waggrConnection.createStatement();
                ResultSet UserData = GetCity.executeQuery("SELECT password, name, surname, city_name, country_name  FROM users WHERE login ='" + login +  "';");
                UserData.next();
                return new User(login, UserData.getString(1), UserData.getString(2), UserData.getString(3), UserData.getString(4),UserData.getString(5));
            }
            Res.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean deleteUser(String userLogin) {
        try {
            Statement deleteUser = waggrConnection.createStatement();
            String del = String.format("DELETE FROM users WHERE login = '%s';", userLogin);
            int res = deleteUser.executeUpdate(del);
            if (res!=0) {
                deleteUser.close();
                return true;
            }
            deleteUser.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateUser(String userLogin, User newUser) {
        try {
            String stm = String.format("UPDATE users SET login = '%s', password = '%s',name = '%s', surname = '%s', city_name = '%s', country_name = '%s' WHERE login = '%s';",
                    newUser.getUserLogin(),newUser.getUserPassword(),newUser.getUserName(),newUser.getUserSurname(),newUser.getUserCity(),newUser.getUserCountry(),userLogin);
            Statement updateUser = waggrConnection.createStatement();
            int i = updateUser.executeUpdate(stm);
            if (i != 0) {
                updateUser.close();
                return true;
            }
            updateUser.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("update user exception");
        }
        return false;
    }

    public boolean updateUserLocation(String userLogin, String cityName, String countryName) {
        try {
            Statement updateLocation = waggrConnection.createStatement();
            String stm = String.format("UPDATE users SET city_name = '%s', country_name = '%s' WHERE login = '%s';",cityName,countryName,userLogin);
            int i = updateLocation.executeUpdate(stm);
            if (i!=0){
                updateLocation.close();
                return true;
            }
            updateLocation.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Boolean updatePassword(String login, String newPass) {
        try {
            Statement updatePassword = waggrConnection.createStatement();
            String stm = String.format("UPDATE users SET password = '%s' WHERE login = '%s';",newPass,login);
            int i = updatePassword.executeUpdate(stm);
            if (i==1) {
                updatePassword.close();
                return true;
            }
            updatePassword.close();
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public String getPassword(String login) {
        try {
            Statement getPassword = waggrConnection.createStatement();
            String stm = String.format("SELECT password FROM users WHERE login = '%s';",login);
            ResultSet result = getPassword.executeQuery(stm);
            if (result.next()) {
                String res = result.getString(1);
                getPassword.close();
                return res;
            }
            getPassword.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
