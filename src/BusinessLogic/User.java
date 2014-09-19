package BusinessLogic;

/**
 * Created by yuraf_000 on 19.06.2014.
 */
public class User {
    private String userName = null;
    private String userSurname = null;
    private String userCity = null;
    private String userCountry = null;
    private String userLogin = null;
    private RealFeel userRealFeel = null;
    public User (String login, String name, String surname, String cityName, String countryName){
        userName = name;
        userSurname = surname;
        userCity = cityName;
        userCountry = countryName;
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
    public String GetUserCountry() {
        return userCountry;
    }
 }
