package ServiceLayer;

import BusinessLogic.User;
import BusinessLogic.UserWorker;
import DataAccessLayer.DBConnector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by yuraf_000 on 21.12.2014.
 */
public class UsersTableService {
    private final String[] colNames = {"Логин","Пароль", "Имя", "Фамилия", "Город","Страна"};
    private List<List<String>> rows = new ArrayList<>();
    private List<List<String>> oldRows = new ArrayList<>();
    private UsersService userService= new UsersService();

    public UsersTableService() {

        List<User> usersList =  userService.getUsersList();
        for (User user:usersList){
            if (!user.getUserLogin().equals("admin"))
            rows.add(Arrays.asList(user.getUserLogin(), user.getUserPassword(), user.getUserName(), user.getUserSurname(), user.getUserCity(), user.getUserCountry()));
        }
        try {
            oldRows = (List) ObjectClonerService.deepCopy(rows);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public Object getValueAt (int row,int column){
        return rows.get(row).get(column);
    }
    public void setValueAt (String value, int row,int column) {
        List<String> r =  rows.get(row);
        r.set(column, value);
        rows.set(row,r);
    }

    public Boolean rollBack () {
        if (!rows.equals(oldRows)){
            try {
                rows = (List) ObjectClonerService.deepCopy(oldRows);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }
    public Boolean updateDB() {
        Boolean diff = false;
        for (int i = 0; i < oldRows.size(); i++) {
            List<String> oldRow = (List) oldRows.get(i);
            List<String> newRow = (List) rows.get(i);
            if (!oldRow.equals(newRow)){
                User u = new User();
                u.setUserLogin(newRow.get(0));
                u.setUserPassword(newRow.get(1));
                u.setUserName(newRow.get(2));
                u.setUserSurname(newRow.get(3));
                u.setUserCity(newRow.get(4));
                u.setUserCountry(newRow.get(5));
                diff = userService.updateUser(oldRow.get(0),u);
            }
        }
        if (diff) {
            try {
                oldRows = (List) ObjectClonerService.deepCopy(rows);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        }
        else return false;
    }
    public String[] getColNames() {
        return colNames;
    }
    public List<List<String>> getRows() {
        return rows;
    }
    public void onClose() {
        userService.close();
    }
}

