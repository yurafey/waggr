/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package waggr;

import javax.ejb.Local;

/**
 *
 * @author URI
 */
@Local
public interface WeatherBeanLocal {
    public Weatherwua getCurrentWUA (String cityName, String countryName);
    public Weatheryan getCurrentYan (String cityName, String countryName);
}
