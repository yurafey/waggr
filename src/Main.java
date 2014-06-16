import Parsers.ForecastContainerYa;

import java.text.ParseException;
import java.util.List;

public class Main {

    private static List<String> foundCountryCities;

    public static void main(String[] args) throws ParseException {

        ForecastContainerYa FCY = new ForecastContainerYa();
        try{
            for (Integer tempCityId:FCY.GetCityWeatherList().keySet()){
                System.out.println("Погода в городе "+FCY.GetCityIdMap().get(tempCityId)+":");
                for (int i = 0;i<FCY.GetCityWeatherList().get(tempCityId).size();i++){
                    System.out.println(FCY.GetCityWeatherList().get(tempCityId).get(i).GetToString());
                }
            }
            System.out.println(FCY.GetCityWeatherList().toString());

        }catch(NullPointerException e){

        }

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











