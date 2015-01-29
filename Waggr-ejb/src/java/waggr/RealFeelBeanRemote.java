/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package waggr;

import javax.ejb.Remote;

/**
 *
 * @author URI
 */
@Remote
public interface RealFeelBeanRemote {
    public boolean newRealFeel(String login,String cityName, String countryName, int temperature,int pressure,int humidity,float windSpeed);
}
