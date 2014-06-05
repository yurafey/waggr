package Parsers;

import java.util.HashMap;
import java.util.List;

/**
 * Created by yuraf_000 on 05.06.2014.
 */
public class ForecastContainerWUA{
    private CountryCityParserWUA CCP = new CountryCityParserWUA();
    private HashMap<String,String> CityNames = new HashMap<>();
    private HashMap<String,List<Weather>> CityWeatherList = new HashMap<>();
    private HashMap<String,HashMap<String,String>> CountryCitiesMap = new HashMap<>();
    private HashMap<String,String> CountryIdMap = new HashMap<>();


    public HashMap<String,List<Weather>> GetCityWeatherList(){
        return CityWeatherList;
    }
    public HashMap<String,String> GetCityNames (){
        return CityNames;
    }
    public HashMap<String,HashMap<String,String>> GetCountyCitiesMap() {
        return CountryCitiesMap;
    }
    public HashMap<String,String> GetCountryIdMap (){
        return CountryIdMap;
    }

    public ForecastContainerWUA(String CountryNames) {
        System.out.println("Количество городов в базе: " + CCP.AllCitiesLength());
        CountryIdMap = CCP.GetCountryIdNamesByCountryNames(CountryNames);
        CountryCitiesMap = CCP.GetCountryCitiesMapByCountryNames(CountryNames);
        for (String CountryId : CountryCitiesMap.keySet() ) {
            System.out.println("Country id: " + CountryId);
            CityNames = CountryCitiesMap.get(CountryId);
            for (String CityId : CityNames.keySet()) {
                System.out.println("CityId: " + CityId);
                ForecastParserWUA FP = new ForecastParserWUA("http://xml.weather.ua/1.2/forecast/" + CityId + "?dayf=5&lang=ru");
                //for (int x = 0; x<WeatherList.size();x++)   System.out.println(WeatherList.get(x).GetToString());
                CityWeatherList.put(CityId, FP.GetWeatherList());
            }

        }
    }
    public ForecastContainerWUA() {
        System.out.println("Количество городов в базе: " + CCP.AllCitiesLength());
        CountryIdMap = CCP.GetCountryIdNames();
        CountryCitiesMap = CCP.GetCountryCitiesMap();
        for (String CountryId : CountryCitiesMap.keySet()) {
            System.out.println("Country id: " + CountryId);
            CityNames = CountryCitiesMap.get(CountryId);
            for (String CityId : CityNames.keySet()) {
                System.out.println("CityId: " + CityId);
                ForecastParserWUA FP = new ForecastParserWUA("http://xml.weather.ua/1.2/forecast/" + CityId + "?dayf=5&lang=ru");
                //for (int x = 0; x<WeatherList.size();x++)   System.out.println(WeatherList.get(x).GetToString());
                CityWeatherList.put(CityId, FP.GetWeatherList());
            }

        }
    }

}
