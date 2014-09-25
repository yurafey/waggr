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

    public void setUserName(String userName) {
        this.userName = userName;
    }
    public void setUserSurname(String userSurname) {
        this.userSurname = userSurname;
    }
    public void setUserCity(String userCity) {
        this.userCity = userCity;
    }
    public void setUserCountry(String userCountry) {
        this.userCountry = userCountry;
    }
    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public String getUserName(){
        return userName;
    }
    public String getUserSurname(){
        return userSurname;
    }
    public String getUserLogin() { return userLogin; }
    public String getUserCity(){
        return userCity;
    }
    public String getUserCountry() {
        return userCountry;
    }
 }
