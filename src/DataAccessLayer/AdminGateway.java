package DataAccessLayer;

import java.sql.*;

/**
 * Created by URI on 14.01.2015.
 */
public class AdminGateway {
    private Connection waggrConnection = null;
    public AdminGateway() {
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

    public String getCurrentCountries() {
        try {
            Statement CheckLogin = waggrConnection.createStatement();
            ResultSet Res = CheckLogin.executeQuery("SELECT country_name FROM users WHERE login ='admin';");
            if (Res.next()) return Res.getString(1);
            Res.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public String getCurrentPeriod() {
        try {
            Statement CheckLogin = waggrConnection.createStatement();
            ResultSet Res = CheckLogin.executeQuery("SELECT city_name FROM users WHERE login ='admin';");
            if (Res.next()) return Res.getString(1);
            Res.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean setCurrentCountries(String countriesList) {
        try {
            String stm = "UPDATE users SET country_name = ? WHERE login = 'admin'";
            PreparedStatement pst  = waggrConnection.prepareStatement(stm);
            pst.setString(1,countriesList);
            int i = pst.executeUpdate();
            if (i!=0) {
                pst.close();
                return true;
            }
            pst.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public boolean setCurrentPeriod(String period) {
        try {
            String stm = "UPDATE users SET city_name = ? WHERE login = 'admin'";
            PreparedStatement pst = waggrConnection.prepareStatement(stm);
            pst.setString(1,period);
            int i = pst.executeUpdate();
            if (i!=0) {
                pst.close();
                return true;
            }
            pst.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
