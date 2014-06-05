package Parsers;

import java.util.HashMap;

/**
 * Created by yuraf_000 on 05.06.2014.
 */
public class CountryCityParserWUA {
    private HashMap<String, HashMap<String,String>> CountryCityMap = new HashMap<>();
    private int items = 0;
    private CountryIdParserWUA CountryIDParser = new CountryIdParserWUA("http://xml.weather.ua/1.2/country/");
    private HashMap<String,String> CountryIDMap = new HashMap<>();
    public int AllCitiesLength(){
        return items;
    }

    public CountryCityParserWUA(){
        CountryIDMap = CountryIDParser.GetCountryId();

        for (String s: CountryIDMap.keySet()) {
            CityIdParserWUA CityIdParser = new CityIdParserWUA("http://xml.weather.ua/1.2/city/?country="+s); //создаем парсеры городов
            HashMap<String, String> put = CityIdParser.GetCitiesId();
            items = items+put.size();
            CountryCityMap.put(s, put);//создаем карту <ID СТРАНЫ, MAP <ID города, ИМЯ города>>
        }
    }
    public HashMap<String,String> GetCountryIdNames(){
        return CountryIDMap;
    }

    public HashMap<String, HashMap<String, String>> GetCountryCitiesMap(){
        return CountryCityMap;
    }

    public HashMap<String,String> GetCountryIdNamesByCountryNames(String s){
        HashMap<String,String> CountryIDMapLocal = new HashMap<>();
        CountryIDMapLocal = CountryIDParser.GetCountryIdByNames(s);
        return CountryIDMapLocal;
    }

    public HashMap<String, HashMap<String, String>> GetCountryCitiesMapByCountryNames(String s){
        HashMap<String, HashMap<String,String>> CountryCityMapLocal = new HashMap<>();
        HashMap<String,String> CountryIDMapLocal = new HashMap<>();
        CountryIDMapLocal = CountryIDParser.GetCountryIdByNames(s);
        for (String z:CountryIDMapLocal.keySet()){
            CountryCityMapLocal.put(z,CountryCityMap.get(z));
        }
        return CountryCityMapLocal;
    }


}