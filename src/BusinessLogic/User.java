package BusinessLogic;

/**
 * Created by yuraf_000 on 19.06.2014.
 */
public class User {
    private String userLogin = null;
    private String userCity = null;
    private RealFeel userRealFeel = null;
    public User (String login, String city){
        userLogin = login;
        userCity = city;
    }
    public String GetUserLogin(){
        return userLogin;
    }
    public String GetUserCity(){
        return userCity;
    }
 }
