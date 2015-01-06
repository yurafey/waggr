package BusinessLogic;

/**
 * Created by yuraf_000 on 26.12.2014.
 */
public class RealFeel extends Weather {
    private String countryName = null;
    private String cityName = null;
    private String userLogin = null;

    public String getCountryName() {
        return countryName;
    }

    public String getCityName() {
        return cityName;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

}
