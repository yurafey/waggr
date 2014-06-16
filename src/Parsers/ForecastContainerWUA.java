package Parsers;

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
    public HashMap<Integer,String> GetCityNames (){
        return CityNames;
    }
    public ListMultimap<Integer,Integer> GetCountyCitiesMap() {
        return CountryCitiesMap;
    }
    public HashMap<Integer,String> GetCountryIdMap (){
        return CountryNames;
    }

    public ForecastContainerWUA(String CountryNamesToParse) {
        System.out.println("Количество городов в базе: " + CCP.AllCitiesLength());
        CountryNames = CCP.GetCountryIdNamesByCountryNames(CountryNamesToParse);
        CountryCitiesMap = CCP.GetCountryCitiesMapByCountryNames(CountryNamesToParse);
        CityNames = CCP.GetCityIdNames();
        for (Integer CountryId : CountryCitiesMap.keySet() ) {
            System.out.println("Country id: " + CountryId);
            List<Integer> tempCityList = CountryCitiesMap.get(CountryId);
            for (int i = 0; i < tempCityList.size(); i++){

                ForecastParserWUA FP = new ForecastParserWUA("http://xml.weather.ua/1.2/forecast/" + tempCityList.get(i) + "?dayf=5&lang=ru");
                System.out.println("CityId: " + tempCityList.get(i));
                CityWeatherList.put(tempCityList.get(i), FP.GetWeatherList());
            }
//            for (Integer CityId : CityNames.keySet()) {
//                //CityNames.putAll(CityId, CountryCitiesMap.get(CountryId));
//                System.out.println("CityId: " + CityId);
//                ForecastParserWUA FP = new ForecastParserWUA("http://xml.weather.ua/1.2/forecast/" + CityId + "?dayf=5&lang=ru");
//                //for (int x = 0; x<WeatherList.size();x++)   System.out.println(WeatherList.get(x).GetToString());
//                CityWeatherList.put(CityId, FP.GetWeatherList());
//            }

        }
    }
    public ForecastContainerWUA() {
        System.out.println("Количество городов в базе: " + CCP.AllCitiesLength());
        CountryNames = CCP.GetCountryIdNames();
        CountryCitiesMap = CCP.GetCountryCitiesMap();
        CityNames = CCP.GetCityIdNames();
        for (Integer CountryId : CountryCitiesMap.keySet() ) {
            System.out.println("Country id: " + CountryId);
            List<Integer> tempCityList = CountryCitiesMap.get(CountryId);
            for (int i = 0; i < tempCityList.size(); i++){
                ForecastParserWUA FP = new ForecastParserWUA("http://xml.weather.ua/1.2/forecast/" + tempCityList.get(i) + "?dayf=5&lang=ru");
                System.out.println("CityId: " + tempCityList.get(i));
                CityWeatherList.put(tempCityList.get(i), FP.GetWeatherList());
            }
        }
//        for (String CountryId : CountryCitiesMap.keySet()) {
//            System.out.println("Country id: " + CountryId);
//            CityNames = CountryCitiesMap.get(CountryId);
//            for (String CityId : CityNames.keySet()) {
//                System.out.println("CityId: " + CityId);
//                ForecastParserWUA FP = new ForecastParserWUA("http://xml.weather.ua/1.2/forecast/" + CityId + "?dayf=5&lang=ru");
//                //for (int x = 0; x<WeatherList.size();x++)   System.out.println(WeatherList.get(x).GetToString());
//                CityWeatherList.put(CityId, FP.GetWeatherList());
//            }
//
//        }
    }

}
