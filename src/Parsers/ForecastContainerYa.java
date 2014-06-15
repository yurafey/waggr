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
    private HashMap<Integer,String> CountryIdMap = new HashMap<>();
    private ListMultimap<Integer,Pair<String,String>> CountryCityMap = ArrayListMultimap.create();
    private HashMap<String,List<Weather>> CityWeatherList = new HashMap<>();

    public ForecastContainerYa() {
        CountryCityMap = CP.GetCountryCityMap();
        CountryIdMap = CP.GetCountryIdMap();
        for (Integer CountryId : CountryCityMap.keySet()) {
            List<Pair<String,String>> CityNames = CountryCityMap.get(CountryId);
            for (int i = 0; i < CityNames.size(); i++) {
                List<Weather> WeatherList = new ArrayList<Weather>();
                ForecastParserYa WP = new ForecastParserYa("http://export.yandex.ru/weather-ng/forecasts/" + CityNames.get(i).getKey() + ".xml");
                WeatherList = WP.GetWeatherList();
                CityWeatherList.put(CityNames.get(i).getKey(),WeatherList);
                System.out.println(CityNames.get(i).getKey()+ "  "+ WeatherList.size());
            }

        }
    }

    public HashMap<String,List<Weather>> GetCityWeatherList(){
        return CityWeatherList;
    }
    public HashMap<Integer,String> GetCountryIdMap (){
        return CountryIdMap;
    }
    public ListMultimap<Integer,Pair<String,String>> GetCountryCityMap (){
        return CountryCityMap;
    }
}