import DBProcessor.DBConnector;
import Parsers.ForecastContainerWUA;
import Parsers.ForecastContainerYa;

import java.text.ParseException;
import java.util.List;

public class Main {

    private static List<String> foundCountryCities;

    public static void main(String[] args) throws ParseException {
        //AuthorizationForm MForm = new AuthorizationForm();
        //DBConnector db = new DBConnector();


        DBConnector db = new DBConnector();
        String CountryNames = "Азербайджан,Австралия";
        ForecastContainerYa FCY = new ForecastContainerYa(CountryNames);
        ForecastContainerWUA FWUA = new ForecastContainerWUA(CountryNames);

        db.WriteWeatherDataYandex(FCY.GetCityWeatherList(), FCY.GetCityIdMap(),FCY.GetCountryIdMap(),FCY.GetCountryCityMap());
        db.WriteWeatherDataWUA(FWUA.GetCityWeatherList(),FWUA.GetCityIdMap(),FWUA.GetCountryIdMap(),FWUA.GetCountyCitiesMap());
        db.DBConnectionClose();
        //List<List<Weather>> res = new ArrayList<>();
        //res = db.GetForecastsByCityName("Баку");
        //System.out.println(res.get(0).get(0).GetDate().toString());
        //System.out.println(res.get(1).get(0).GetDate().toString());


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











