package Parsers;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;

import java.util.HashMap;

/**
 * Created by yuraf_000 on 05.06.2014.
 */
public class CountryCityParserWUA {
    //private HashMap<String, HashMap<String,String>> CountryCityMap = new HashMap<>();
    private ListMultimap<Integer, Integer> CountryCityMap = ArrayListMultimap.create();
    private HashMap<Integer,String> CityIDMap = new HashMap<>();
    private int items = 0;
    private CountryIdParserWUA CountryIDParser = new CountryIdParserWUA("http://xml.weather.ua/1.2/country/");
    private HashMap<Integer,String> CountryIDMap = new HashMap<>();


    public CountryCityParserWUA(){
        CountryIDMap = CountryIDParser.GetCountryId();

        for (Integer s: CountryIDMap.keySet()) {
            CityIdParserWUA CityIdParser = new CityIdParserWUA("http://xml.weather.ua/1.2/city/?country="+s); //создаем парсеры городов
            HashMap<Integer, String> put = CityIdParser.GetCitiesId();
            CityIDMap.putAll(put);
            items = items+put.size();
            CountryCityMap.putAll(s, put.keySet());//создаем карту <ID СТРАНЫ, MAP <ID города, ИМЯ города>>
        }
    }
    public int AllCitiesLength(){
        return items;
    }

    public HashMap<Integer,String> GetCityIdNames(){
        return CityIDMap;
    }
    public HashMap<Integer,String> GetCountryIdNames(){
        return CountryIDMap;
    }

    public ListMultimap<Integer,Integer> GetCountryCitiesMap(){
        return CountryCityMap;
    }

    public HashMap<Integer,String> GetCountryIdNamesByCountryNames(String s){
        HashMap<Integer,String> CountryIDMapLocal = new HashMap<>();
        CountryIDMapLocal = CountryIDParser.GetCountryIdByNames(s);
        return CountryIDMapLocal;
    }

    public ListMultimap<Integer, Integer> GetCountryCitiesMapByCountryNames(String s){
        ListMultimap<Integer, Integer> CountryCityMapLocal = ArrayListMultimap.create();
        HashMap<Integer,String> CountryIDMapLocal = new HashMap<>();
        CountryIDMapLocal = CountryIDParser.GetCountryIdByNames(s);
        for (Integer z:CountryIDMapLocal.keySet()){
            CountryCityMapLocal.putAll(z, CountryCityMap.get(z));
        }
        return CountryCityMapLocal;
    }


}