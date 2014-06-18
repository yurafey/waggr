import DBProcessor.DBConnector;
import Parsers.ForecastContainerWUA;
import Parsers.ForecastContainerYa;

import java.text.ParseException;
import java.util.List;

public class Main {

    private static List<String> foundCountryCities;

    public static void main(String[] args) throws ParseException {
        DBConnector db = new DBConnector();
        String CountryNames = "Австралия,Ирландия,Азербайджан";
        ForecastContainerYa FCY = new ForecastContainerYa(CountryNames);
        ForecastContainerWUA FWUA = new ForecastContainerWUA(CountryNames);

        db.WriteWeatherDataYandex(FCY.GetCityWeatherList(), FCY.GetCityIdMap());
        db.WriteWeatherDataWUA(FWUA.GetCityWeatherList(),FWUA.GetCityIdMap());
        db.DBConnectionClose();

//        ForecastContainerYa FCY = new ForecastContainerYa("Ирландия");
//        try{
//            for (Integer tempCityId:FCY.GetCityWeatherList().keySet()){
//                System.out.println("Погода в городе "+FCY.GetCityIdMap().get(tempCityId)+":");
//                for (int i = 0;i<FCY.GetCityWeatherList().get(tempCityId).size();i++){
//                    System.out.println(FCY.GetCityWeatherList().get(tempCityId).get(i).GetToString());
//                }
//            }
//            //System.out.println(FCY.GetCityWeatherList().toString());
//
//        }catch(NullPointerException e){
//
//        }
//
//        ForecastContainerWUA FCWUA = new ForecastContainerWUA("Ирландия");
//
//        try{
//            for (Integer tempCityId:FCWUA.GetCityWeatherList().keySet()){
//                System.out.println("Погода в городе "+FCWUA.GetCityIdMap().get(tempCityId)+":");
//                for (int i = 0;i<FCWUA.GetCityWeatherList().get(tempCityId).size();i++){
//                    System.out.println(FCWUA.GetCityWeatherList().get(tempCityId).get(i).GetToString());
//                }
//            }
//            System.out.println(FCWUA.GetCityWeatherList().toString());
//
//        }catch(NullPointerException e){
//
//        }

    }


}











