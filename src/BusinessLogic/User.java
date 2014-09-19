package BusinessLogic;

/**
 * Created by yuraf_000 on 19.06.2014.
 */
public class User {
    private String userName = null;
    private String userSurname = null;
    private String userCity = null;
    private String userLogin = null;
    private RealFeel userRealFeel = null;
    public User (String login, String name, String surname, String city){
        userName = name;
        userSurname = surname;
        userCity = city;
        userLogin = login;
    }
    public String GetUserName(){
        return userName;
    }
    public String GetUserSurname(){
        return userSurname;
    }
    public String GetUserLogin () { return userLogin; }
    public String GetUserCity(){
        return userCity;
    }
 }
