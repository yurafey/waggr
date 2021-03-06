package Parsers;

import BusinessLogic.Weather;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;

import java.util.HashMap;
import java.util.List;

/**
 * Created by yuraf_000 on 05.06.2014.
 */
public class ForecastContainerWUA{
    private CountryCityParserWUA CCP = new CountryCityParserWUA();
    private HashMap<Integer,String> CityNames = new HashMap<>();
    private HashMap<Integer,String> CountryNames = new HashMap<>();
    private HashMap<Integer,List<Weather>> CityWeatherList = new HashMap<>();
    private ListMultimap<Integer,Integer> CountryCitiesMap = ArrayListMultimap.create();

    public HashMap<Integer,List<Weather>> GetCityWeatherList(){
        return CityWeatherList;
    }
    public HashMap<Integer,String> GetCityIdMap(){
        return CityNames;
    }
    public ListMultimap<Integer,Integer> GetCountyCitiesMap() {
        return CountryCitiesMap;
    }
    public HashMap<Integer,String> GetCountryIdMap (){
        return CountryNames;
    }

    public ForecastContainerWUA(String CountryNamesToParse) {
        System.out.println("Number of cities in WeatherUA DB: " + CCP.AllCitiesLength());
        CountryNames = CCP.GetCountryIdNamesByCountryNames(CountryNamesToParse);
        CountryCitiesMap = CCP.GetCountryCitiesMapByCountryNames(CountryNamesToParse);
        CityNames = CCP.GetCityIdNames();
        ParseProcessor();
    }
    public ForecastContainerWUA() {
        System.out.println("Number of cities in WeatherUA DB: " + CCP.AllCitiesLength());
        CountryNames = CCP.GetCountryIdNames();
        CountryCitiesMap = CCP.GetCountryCitiesMap();
        CityNames = CCP.GetCityIdNames();
        ParseProcessor();

    }
    private void ParseProcessor (){
        for (Integer CountryId : CountryCitiesMap.keySet() ) {
            System.out.println("Country in progress: " + CountryId);
            List<Integer> tempCityList = CountryCitiesMap.get(CountryId);
            for (int i = 0; i < tempCityList.size(); i++){
                ForecastParserWUA FP = new ForecastParserWUA("http://xml.weather.ua/1.2/forecast/" + tempCityList.get(i) + "?dayf=5&lang=ru");
                CityWeatherList.put(tempCityList.get(i), FP.GetWeatherList());
                System.out.println("City done: " + tempCityList.get(i));
            }
        }
    }

}
