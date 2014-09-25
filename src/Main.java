import DataAccessLayer.DBConnector;
import Parsers.ForecastContainerWUA;
import Parsers.ForecastContainerYa;

import java.text.ParseException;
import java.util.List;

public class Main {

    private static List<String> foundCountryCities;

    public static void main(String[] args) throws ParseException {
        //AuthorizationForm MForm = new AuthorizationForm();

        DBConnector db = new DBConnector();
        String CountryNames = "Австралия";
        ForecastContainerYa FCY = new ForecastContainerYa(CountryNames);
        ForecastContainerWUA FCWUA = new ForecastContainerWUA(CountryNames);

        db.writeWeatherDataYandex(FCY.GetCityWeatherList(), FCY.GetCityIdMap(),FCY.GetCountryIdMap(),FCY.GetCountryCityMap());
        db.writeWeatherDataWUA(FCWUA.GetCityWeatherList(), FCWUA.GetCityIdMap(), FCWUA.GetCountryIdMap(), FCWUA.GetCountyCitiesMap());
        db.connectionClose();
//
//        List<List<Weather>> res = new ArrayList<>();
//        res = db.getForecastsByCityAndCountyName("Сумгаит","Азербайджан");
        //System.out.println(res.get(0).get(0).getDate().toString());
        //System.out.println(res.get(1).get(0).getDate().toString());
//        db.connectionClose();
        //List<List<Weather>> res = new ArrayList<>();
        //res = db.getForecastsByCityAndCountyName("Баку");
        //System.out.println(res.get(0).get(0).getDate().toString());
        //System.out.println(res.get(1).get(0).getDate().toString());


//        ForecastContainerYa FCY = new ForecastContainerYa("Ирландия");
//        try{
//            for (Integer tempCityId:FCY.GetCityWeatherList().keySet()){
//                System.out.println("Погода в городе "+FCY.GetCityIdMap().get(tempCityId)+":");
//                for (int i = 0;i<FCY.GetCityWeatherList().get(tempCityId).size();i++){
//                    System.out.println(FCY.GetCityWeatherList().get(tempCityId).get(i).getToString());
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
//                    System.out.println(FCWUA.GetCityWeatherList().get(tempCityId).get(i).getToString());
//                }
//            }
//            System.out.println(FCWUA.GetCityWeatherList().toString());
//
//        }catch(NullPointerException e){
//
//        }

    }


}











