import Parsers.ForecastContainerYa;

import java.text.ParseException;
import java.util.List;

public class Main {

    private static List<String> foundCountryCities;

    public static void main(String[] args) throws ParseException {

        ForecastContainerYa FCY = new ForecastContainerYa();
        FCY.GetCityWeatherList();
        
        System.out.println(FCY.GetCityWeatherList().toString());

        //CountryCityParserWUA c = new CountryCityParserWUA();
       // c.GetCitiesMap();

//        ForecastContainerWUA FCW = new ForecastContainerWUA("Ирландия,Австралия");
//        HashMap<String,List<Parsers.Weather>> CWL= FCW.GetCityWeatherList();
//        HashMap<String,String> CIN = FCW.GetCountryIdMap();
//        HashMap<String,HashMap<String,String>> CCN = FCW.GetCountyCitiesMap();
//        for(String CountryId : CIN.keySet()){
//            System.out.println("Страна: " + CIN.get(CountryId));
//            HashMap<String,String> tempCitiesNames = CCN.get(CountryId);
//            for(String tempCityId : tempCitiesNames.keySet()){
//                List<Parsers.Weather> tempWeatherList = CWL.get(tempCityId);
//                System.out.println("Город: "+ tempCitiesNames.get(tempCityId));
//                for(int i = 0; i < tempWeatherList.size(); i++){
//                    System.out.println(tempWeatherList.get(i).GetToString());
//                }
//            }
//        }


    }


}











