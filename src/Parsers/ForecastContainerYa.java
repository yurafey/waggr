package Parsers;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by yuraf_000 on 05.06.2014.
 */
public class ForecastContainerYa{
    private CountryCityParserYa CP = new CountryCityParserYa("http://weather.yandex.ru/static/cities.xml");
    private HashMap<Integer,String> CountryIdMap = new HashMap<>();
    private HashMap<Integer,String> CityIdMap = new HashMap<>();
    private ListMultimap<Integer,Integer> CountryCityMap = ArrayListMultimap.create();
    private HashMap<Integer,List<Weather>> CityWeatherList = new HashMap<>();

    public ForecastContainerYa() {
        CountryCityMap = CP.GetCountryCityMap();
        CountryIdMap = CP.GetCountryIdMap();
        CityIdMap = CP.GetCityIdMap();
        for (Integer CountryId : CountryCityMap.keySet()) {
            List <Integer> CityIds = CountryCityMap.get(CountryId);
            for (int i = 0; i < CityIds.size(); i++) {
                List<Weather> WeatherList = new ArrayList<Weather>();
                ForecastParserYa WP = new ForecastParserYa("http://export.yandex.ru/weather-ng/forecasts/" + CityIds.get(i) + ".xml");
                WeatherList = WP.GetWeatherList();
                if (WeatherList != null){
                    CityWeatherList.put(CityIds.get(i),WeatherList);
                    System.out.println(CityIds.get(i)+ "  "+ WeatherList.size());
                }
            }

        }
    }
    public ForecastContainerYa(String CountryNames) {
        CountryCityMap = CP.GetCountryCityMapByNames(CountryNames);
        CountryIdMap = CP.GetCountryIdMap();
        CityIdMap = CP.GetCityIdMap();
        for (Integer CountryId : CountryCityMap.keySet()) {
            List <Integer> CityIds = CountryCityMap.get(CountryId);
            for (int i = 0; i < CityIds.size(); i++) {
                List<Weather> WeatherList = new ArrayList<Weather>();
                ForecastParserYa WP = new ForecastParserYa("http://export.yandex.ru/weather-ng/forecasts/" + CityIds.get(i) + ".xml");
                WeatherList = WP.GetWeatherList();
                if (WeatherList != null){
                    CityWeatherList.put(CityIds.get(i),WeatherList);
                    System.out.println(CityIds.get(i)+ "  "+ WeatherList.size());
                }
            }

        }
    }

    public HashMap<Integer,List<Weather>> GetCityWeatherList(){
        return CityWeatherList;
    }
    public HashMap<Integer,String> GetCityIdMap () { return CityIdMap; }
    public HashMap<Integer,String> GetCountryIdMap (){
        return CountryIdMap;
    }
    public ListMultimap<Integer,Integer> GetCountryCityMap (){
        return CountryCityMap;
    }
}