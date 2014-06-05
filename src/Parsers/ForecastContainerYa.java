package Parsers;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by yuraf_000 on 05.06.2014.
 */
public class ForecastContainerYa{
    private CountryCityParserYa CP = new CountryCityParserYa("http://weather.yandex.ru/static/cities.xml");

    private ListMultimap<String,String> CountryCityMap = ArrayListMultimap.create();
    private HashMap<String,List<Weather>> CityWeatherList = new HashMap<>();

    public ForecastContainerYa() {
        CountryCityMap = CP.GetCountryCityMap();
        for (String CountryName : CountryCityMap.keySet()) {
            List<String> CityNames = CountryCityMap.get(CountryName);
            for (int i = 0; i < CityNames.size(); i++) {
                List<Weather> WeatherList = new ArrayList<Weather>();
                Pair<String, String> City = CountryCityDivide(CityNames.get(i));
                ForecastParserYa WP = new ForecastParserYa("http://export.yandex.ru/weather-ng/forecasts/" + City.getKey() + ".xml");
                WeatherList = WP.GetWeatherList();
                CityWeatherList.put(City.getKey(),WeatherList);
                System.out.println(City.getKey()+ "  "+ WeatherList.size());
            }

        }
    }

    public HashMap<String,List<Weather>> GetCityWeatherList(){
        return CityWeatherList;
    }

    private static Pair <String,String> CountryCityDivide(String Cityname){
        String s1,s2;
        s1 = Cityname.substring(0,Cityname.indexOf('_'));
        s2 = Cityname.substring(Cityname.indexOf('_')+1);
        Pair<String,String> p = new Pair<>(s2,s1);
        return  p;
    }
}