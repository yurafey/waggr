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
public interface WeatherBeanRemote {
    public List<List<String>> getCurrentTable(String cityName, String countryName);
    public List<List<String>> getYandexTable(String cityName, String countryName);
    public List<List<String>> getWuaTable(String cityName, String countryName);
    public List<List<String>> getCurrentTableByLogin(String userLogin);
    public List<List<String>> getWuaTableByLogin(String userLogin);
    public List<List<String>> getYanTableByLogin(String userLogin);
    public void persist(Object object);
}
