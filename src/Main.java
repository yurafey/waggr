import java.text.ParseException;
import java.util.*;

import Parsers.*;

public class Main {

    private static List<String> foundCountryCities;

    public static void main(String[] args) throws ParseException {

        ForecastContainerYa FCY = new ForecastContainerYa();
        FCY.GetCityWeatherList();
        System.out.println(FCY.GetCityWeatherList().toString());

//        while (true) {
//            CountryCityParserYa CParserY = new CountryCityParserYa("http://weather.yandex.ru/static/cities.xml");
//            ListMultimap<String, String> CountryCityMap = CParserY.GetCountryCityMap();
//            System.out.print("Страна:");
//            Scanner sc = new Scanner(System.in);
//            String enteredCountryName = sc.next();
//
//            foundCountryCities = CountryCityMap.get(enteredCountryName);
//            if (foundCountryCities.size()==0) {
//                System.out.println("Такой страны нет в базе :(");
//                continue;
//            }
//            System.out.println("Города: "+CountryCityDivide(foundCountryCities).keySet());
//            System.out.print("Введите город: ");
//            String enteredCityName = sc.next();
//
//            if (CountryCityDivide(foundCountryCities).get(enteredCityName) == null) {
//                System.out.println("Такого города нет в базе страны " + enteredCountryName + " :(");
//                continue;
//            }
//            ForecastParserYa pars = new ForecastParserYa();
//            List<Parsers.Weather> printLst = new ArrayList<Parsers.Weather>();
//
//            System.out.print("Введите 'C', чтобы узнать текущее состояние погоды, 'F' - прогноз погоды: ");
//            String typeOfForecast = sc.next();
//            switch (typeOfForecast){
//                case "C":
//                    printLst = pars.GetCurrent("http://export.yandex.ru/weather-ng/forecasts/" + CountryCityDivide(foundCountryCities).get(enteredCityName) + ".xml");
//                    break;
//                case "F":
//                    printLst = pars.GetWeekPredict("http://export.yandex.ru/weather-ng/forecasts/" + CountryCityDivide(foundCountryCities).get(enteredCityName) + ".xml");
//                    break;
//                default:
//                    System.out.println("В данном поле можно ввести только 'C' или 'F' !");
//                    continue;
//            }
//
//            for (int i = 0; i < printLst.size(); i++) {
//                System.out.println(printLst.get(i).GetToString());
//            }
//        }




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











