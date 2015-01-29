/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package waggr;

import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author URI
 */
@Remote
public interface WaggrSessionRemote {
    public String getPasswordByUserName(String userName);
    public void addNewUser (String userName, String userSurname, String userLogin, String userPassword, String userCity, String userCountry);
    public boolean doAuthorization (String userLogin, String userPassword);
    public String getUserCurrentCityName (String userLogin);
    public String getUserCurrentCountryName (String userLogin);
    public boolean changeUserCityAndCountry(String userLogin,String cityName,String countryName);
    public boolean checkCityAndCountryExists(String cityName, String countryName);
    public boolean checkUserExists(String userLogin);
}
