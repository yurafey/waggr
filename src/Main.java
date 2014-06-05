import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import com.sun.org.apache.xerces.internal.parsers.DOMParser;
import javafx.util.Pair;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class Main {

    private static List<String> foundCountryCities;

    public static void main(String[] args) throws ParseException {

        ForecastContainerYa FCY = new ForecastContainerYa();
        FCY.GetCityWeatherList();
        System.out.println(FCY.GetCityWeatherList().toString());

//        while (true) {
//            CitiesParserYandex CParserY = new CitiesParserYandex("http://weather.yandex.ru/static/cities.xml");
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
//            WeatherParserYandex pars = new WeatherParserYandex();
//            List<Weather> printLst = new ArrayList<Weather>();
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
//        HashMap<String,List<Weather>> CWL= FCW.GetCityWeatherList();
//        HashMap<String,String> CIN = FCW.GetCountryIdMap();
//        HashMap<String,HashMap<String,String>> CCN = FCW.GetCountyCitiesMap();
//        for(String CountryId : CIN.keySet()){
//            System.out.println("Страна: " + CIN.get(CountryId));
//            HashMap<String,String> tempCitiesNames = CCN.get(CountryId);
//            for(String tempCityId : tempCitiesNames.keySet()){
//                List<Weather> tempWeatherList = CWL.get(tempCityId);
//                System.out.println("Город: "+ tempCitiesNames.get(tempCityId));
//                for(int i = 0; i < tempWeatherList.size(); i++){
//                    System.out.println(tempWeatherList.get(i).GetToString());
//                }
//            }
//        }


    }


}

class Weather{
    private Date date = new Date();
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    private int temperature;
    private int pressure;
    private float wind_speed;
    private String wind_direction;
    private int humidity;
    private Boolean ispredict;

    public void SetHumidity (int humidity){
        this.humidity = humidity;
    }
    public void SetIsPredict (Boolean is){
        this.ispredict = is;
    }
    public void SetDate(Date date){
        this.date = date;
    }
    public void SetTemperature(int temperature){
        this.temperature  = temperature;
    }
    public void SetPressure(int pressure){
        this.pressure  = pressure;
    }
    public void SetWindSpeed(float wind_speed){
        this.wind_speed  = wind_speed;
    }
    public void SetWindDirection(String wind_direction){
        this.wind_direction  = wind_direction;
    }

    public int GetHumidity() {
        return humidity;
    }
    public Boolean GetIsPredict (){
        return ispredict;
    }
    public Date GetDate(){
        return date;
    }
    public int GetTemperature(){
        return temperature;
    }
    public int GetPressure(){
        return pressure;
    }
    public String GetWindDirection() {
        return wind_direction;
    }
    public float GetWindSpeed(){
        return wind_speed;
    }

    public String GetToString(){
        //Временаня структура вывода для удобной отладки
        if (ispredict)
            return String.format("Forecast date %s %s : temperature %d C*, air pressure %d mm, humidity %d, wind %.1f %s",
                dateFormat.format(date).substring(0,dateFormat.format(date).indexOf(" ")),
                dateFormat.format(date).substring(dateFormat.format(date).indexOf(" ")).equals(" 13:00")? "DAY" : "NIGHT",
                temperature,
                pressure,
                humidity,
                wind_speed,
                wind_direction
                );
        else
            return String.format("Current weather: %s : temperature %d C*, air pressure %d mm, humidity %d, wind %.1f %s",
                    dateFormat.format(date),
                    temperature,
                    pressure,
                    humidity,
                    wind_speed,
                    wind_direction
            );
    }

}

class WeatherParserYandex {
    private Weather WC = new Weather();
    private List<Weather> WeatherList = new ArrayList<Weather>();

    private List<Weather> GetWeekPredict(String url) {
        //String url = "http://export.yandex.ru/weather-ng/forecasts/26063.xml";
        List<Weather> resList = new ArrayList<Weather>();
        try {
            URL UrlToParse = new URL(url);
            DOMParser p = new DOMParser();
            p.parse(new InputSource(UrlToParse.openStream()));
            Document doc = p.getDocument();
            NodeList nodeLst = doc.getElementsByTagName("day");
            for (int i = 0; i < nodeLst.getLength(); i++) {   // входим в поиск по nodes "day"
                Node nNode = nodeLst.item(i);
                NodeList node2Lst = ((Element) nNode).getElementsByTagName("day_part");
                for (int n = 0; n<node2Lst.getLength(); n++){
                    String attr = (((Element)node2Lst.item(n)).getAttribute("type"));
                    if ((attr.equals("day_short"))||(attr.equals("night_short"))){
                        Element e = (Element) node2Lst.item(n);
                        Weather weatherTmp = new Weather();
                        SimpleDateFormat parserSDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        switch (attr.toString()) {
                            case "day_short":
                                parserSDF.parse(((Element) nNode).getAttribute("date").toString() + " 13:00:00");
                                break;
                            case "night_short":
                                parserSDF.parse(((Element) nNode).getAttribute("date").toString() + " 01:00:00");
                        }
                        Calendar c = parserSDF.getCalendar();
                        Date d = c.getTime();
                        weatherTmp.SetDate(d);
                        weatherTmp.SetIsPredict(true);
                        NodeList node3Lst = e.getChildNodes();
                        for (int y = 0; y < node3Lst.getLength(); y++) {
                            Node items = node3Lst.item(y);
                            switch (items.getNodeName().toString()) {
                                case "temperature":
                                    weatherTmp.SetTemperature(Integer.parseInt(items.getTextContent()));
                                    break;
                                case "pressure":
                                    weatherTmp.SetPressure(Integer.parseInt(items.getTextContent()));
                                    break;
                                case "wind_speed":
                                    weatherTmp.SetWindSpeed(Float.valueOf(items.getTextContent()));
                                    break;
                                case "wind_direction":
                                    weatherTmp.SetWindDirection(items.getTextContent());
                                    break;
                                case "humidity":
                                    weatherTmp.SetHumidity(Integer.parseInt(items.getTextContent()));
                                    break;
                                default:
                                    break;
                            }
                        }
                        resList.add(weatherTmp);
                        weatherTmp = null;

                    }
                }
            }
            nodeLst = null;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return resList;
    }
    private void GetCurrent(String url) {
         try {
            URL UrlToParse = new URL(url);
            DOMParser p = new DOMParser();
            p.parse(new InputSource(UrlToParse.openStream()));
            Document doc = p.getDocument();
            NodeList nodeLst = doc.getElementsByTagName("fact");
            for (int i = 0; i < nodeLst.getLength(); i++) {
                Node node1 = nodeLst.item(i);
                NodeList node1list = node1.getChildNodes();
                Weather weatherTmp = new Weather();
                for (int z = 0; z < node1list.getLength(); z++) {
                    Node items = node1list.item(z);
                    switch (items.getNodeName().toString()) {
                        case "temperature":
                            weatherTmp.SetTemperature(Integer.parseInt(items.getTextContent()));
                            break;
                        case "pressure":
                            weatherTmp.SetPressure(Integer.parseInt(items.getTextContent()));
                            break;
                        case "wind_speed":
                            weatherTmp.SetWindSpeed(Float.valueOf(items.getTextContent()));
                            break;
                        case "wind_direction":
                            weatherTmp.SetWindDirection(items.getTextContent());
                            break;
                        case "observation_time":
                            SimpleDateFormat parserSDF = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                            parserSDF.parse(items.getTextContent().toString());
                            Calendar c = parserSDF.getCalendar();
                            Date d = c.getTime();
                            weatherTmp.SetDate(d);
                            break;
                        case "humidity":
                            weatherTmp.SetHumidity(Integer.parseInt(items.getTextContent()));
                            break;
                        default:
                            break;
                    }
                }
                weatherTmp.SetIsPredict(false);
                WC = weatherTmp;
                weatherTmp = null;
                node1 = null;
                node1list = null;
            }
            nodeLst = null;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }
    public  WeatherParserYandex(String url){
        GetCurrent(url);
        WeatherList.add(WC);
        Collection<Weather> collection = new ArrayList<Weather>(GetWeekPredict(url));
        WeatherList.addAll(collection);
        collection = null;

    }
    public List<Weather> GetWeatherList(){
        return WeatherList;
    }

}


class CitiesParserYandex {

    private ListMultimap<String, String> CountryCityMap = ArrayListMultimap.create();
    private HashMap<String,String> CountryIdMap = new HashMap<>();
    private int counter = 0;
    public CitiesParserYandex(String url) {
        ParseXMLCities(url);

        System.out.println("Количество городов: " + counter);
    }

    public ListMultimap<String, String> GetCountryCityMap() {
        return CountryCityMap;
    }

    private void ParseXMLCities(String url) {
        try {
            String resCityId = null;
            URL UrlToParse = new URL(url);
            DOMParser p = new DOMParser();
            p.parse(new InputSource(UrlToParse.openStream()));
            Document doc = p.getDocument();
            NodeList nodeLst = doc.getElementsByTagName("country");
            for (int i = 0; i < nodeLst.getLength(); i++) {
                Node nNode = nodeLst.item(i);
                if (nNode.getNodeType()==Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    NodeList CitiesLst = eElement.getElementsByTagName("city");
                    for (int x = 0; x < CitiesLst.getLength(); x++) {
                        Element CElement = (Element) CitiesLst.item(x);

                        switch (eElement.getAttribute("name").toString()) {
                            case "США":
                                if (CitiesLst.item(x).getTextContent().indexOf(",") == -1) {
                                    CountryCityMap.put(eElement.getAttribute("name"), CitiesLst.item(x).getTextContent() + "_" + CElement.getAttribute("id"));
                                    counter++;
                                }
                                else {
                                    CountryCityMap.put(eElement.getAttribute("name"), CitiesLst.item(x).getTextContent().substring(0, CitiesLst.item(x).getTextContent().indexOf(",")) + "_" + CElement.getAttribute("id"));
                                    counter++;
                                }
                                break;

                            default:
                                CountryCityMap.put(eElement.getAttribute("name"), CitiesLst.item(x).getTextContent() + "_" + CElement.getAttribute("id"));
                                counter++;

                        }

                    }
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
    }
}
class ForecastContainerYa{
    private CitiesParserYandex CP = new CitiesParserYandex("http://weather.yandex.ru/static/cities.xml");

    private ListMultimap<String,String> CountryCityMap = ArrayListMultimap.create();
    private HashMap<String,List<Weather>> CityWeatherList = new HashMap<>();

    ForecastContainerYa() {
        CountryCityMap = CP.GetCountryCityMap();
        for (String CountryName : CountryCityMap.keySet()) {
            List<String> CityNames = CountryCityMap.get(CountryName);
            for (int i = 0; i < CityNames.size(); i++) {
                List<Weather> WeatherList = new ArrayList<Weather>();
                Pair<String, String> City = CountryCityDivide(CityNames.get(i));
                WeatherParserYandex WP = new WeatherParserYandex("http://export.yandex.ru/weather-ng/forecasts/" + City.getKey() + ".xml");
                WeatherList = WP.GetWeatherList();
                CityWeatherList.put(City.getKey(),WeatherList);
                System.out.println(City.getKey()+ "  "+ WeatherList.size());
            }

        }
    }

    public HashMap<String,List<Weather>> GetCityWeatherList(){
        return CityWeatherList;
    }

    private static Pair <String,String> CountryCityDivide(String Cityname){
        String s1,s2;
        s1 = Cityname.substring(0,Cityname.indexOf('_'));
        s2 = Cityname.substring(Cityname.indexOf('_')+1);
        Pair<String,String> p = new Pair<>(s2,s1);
        return  p;
    }
}
class ForecastParserWUA{

    private List<Weather> WeatherList = new ArrayList<Weather>();

    private String ConvertWindDirection(int degree){
        if (degree>=0 && degree<=20) return "n";
        if (degree>20 && degree<=35) return "nne";
        if (degree>35 && degree<=55) return "ne";
        if (degree>55 && degree<=70) return "ene";
        if (degree>70 && degree<=110) return "e";
        if (degree>110 && degree<=125) return "ese";
        if (degree>125 && degree<=145) return "se";
        if (degree>145 && degree<=160) return "sse";
        if (degree>160 && degree<=200) return "s";
        if (degree>200 && degree<=215) return "ssw";
        if (degree>215 && degree<=235) return "sw";
        if (degree>235 && degree<=250) return "wsw";
        if (degree>250 && degree<=290) return "w";
        if (degree>290 && degree<=305) return "wnw";
        if (degree>305 && degree<=325) return "nw";
        if (degree>325 && degree<=340) return "nnw";
        if (degree>340 && degree<=360) return "n";
        else return "unknown";
    }
    public List<Weather> GetWeatherList(){
        return WeatherList;
    }
    public ForecastParserWUA(String url) {
        try {
            URL UrlToParse = new URL(url);
            //URL UrlToParse = new URL("http://xml.weather.co.ua/1.2/forecast/62024?dayf=5&lang=ru");
            DOMParser p = new DOMParser();
            p.parse(new InputSource(UrlToParse.openStream()));
            Document doc = p.getDocument();

            //parseCurrent
            Element current = (Element) doc.getElementsByTagName("current").item(0);
            Weather wc = new Weather();
            if (current.getChildNodes().getLength() > 1) { //ecли есть current
                String currentDate = current.getAttribute("last_updated");
                SimpleDateFormat parserSDF = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss", Locale.ENGLISH);
                Date d = parserSDF.parse(currentDate);
                wc.SetDate(d);
                wc.SetIsPredict(false);
                NodeList node0Lst = current.getChildNodes();
                //System.out.println("Current node child nodes " + node0Lst.getLength());
                for (int z = 0; z < node0Lst.getLength(); z++) {
                    if (node0Lst.item(z).getNodeType() == Node.ELEMENT_NODE) {
                        switch (node0Lst.item(z).getNodeName()) {
                            case "t":
                                wc.SetTemperature(Integer.parseInt(node0Lst.item(z).getTextContent()));
                                break;
                            case "p":
                                wc.SetPressure(Integer.parseInt(node0Lst.item(z).getTextContent()));
                                break;
                            case "w":
                                wc.SetWindSpeed(Float.parseFloat(node0Lst.item(z).getTextContent()));
                                break;
                            case "w_rumb":
                                wc.SetWindDirection(this.ConvertWindDirection(Integer.parseInt(node0Lst.item(z).getTextContent())));
                                break;
                            case "h":
                                wc.SetHumidity(Integer.parseInt(node0Lst.item(z).getTextContent()));
                                break;
                            default:
                                continue;
                        }
                    }
                }

                WeatherList.add(wc);
            } else {
                wc.SetIsPredict(true);
            }

            NodeList nodeLst = doc.getElementsByTagName("forecast");
            NodeList node2Lst = ((Element) nodeLst.item(1)).getElementsByTagName("day");
            if (node2Lst.getLength() != 1) {
                for (int i = 0; i < node2Lst.getLength(); i++) {
                    if (node2Lst.item(i).getNodeType() == Node.ELEMENT_NODE) {
                        String date = ((Element) node2Lst.item(i)).getAttribute("date");
                        String time = ((Element) node2Lst.item(i)).getAttribute("hour");
                        SimpleDateFormat parserSDF2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
                        SimpleDateFormat equalParser = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
                        Date d1 = parserSDF2.parse(date + " " + time + ":00:00");

                        if (wc.GetIsPredict() || !(equalParser.format(d1).equals(equalParser.format(wc.GetDate())))) {   //проверяем чтобы не записывать прогноз на текущий день
                            NodeList daynodeLst = node2Lst.item(i).getChildNodes();

                            Weather wp = new Weather();
                            wp.SetDate(d1);
                            for (int z = 0; z < daynodeLst.getLength(); z++) {
                                if (daynodeLst.item(z).getNodeType() == Node.ELEMENT_NODE) {
                                    if (daynodeLst.item(z).getChildNodes().getLength() > 1) {
                                        String min = ((Element) daynodeLst.item(z)).getElementsByTagName("min").item(0).getTextContent();
                                        String max = ((Element) daynodeLst.item(z)).getElementsByTagName("max").item(0).getTextContent();
                                        int mid = (Integer.parseInt(min) + Integer.parseInt(max)) / 2;
                                        Float midf = Float.parseFloat(String.valueOf(mid));

                                        switch (daynodeLst.item(z).getNodeName()) {
                                            case "t":
                                                wp.SetTemperature(mid);
                                                break;
                                            case "p":
                                                wp.SetPressure(mid);
                                                break;
                                            case "wind":
                                                wp.SetWindSpeed(midf);
                                                int rumb = Integer.parseInt(((Element) daynodeLst.item(z)).getElementsByTagName("rumb").item(0).getTextContent());
                                                wp.SetWindDirection(this.ConvertWindDirection(rumb));
                                                break;
                                            case "hmid":
                                                wp.SetHumidity(mid);
                                                break;
                                            default:
                                                continue;
                                        }
                                    }
                                }
                            }
                            wp.SetIsPredict(true);
                            WeatherList.add(wp);
                        }

                    }
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
class ForecastContainerWUA{
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

class CountryCityParserWUA {
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

class CityIdParserWUA {

    private HashMap<String,String> CityIdName = new HashMap<>();
    private String url;

    public CityIdParserWUA(String url){
        this.url = url;
        try {
            URL UrlToParse = new URL(url);
            DOMParser p = new DOMParser();
            p.parse(new InputSource(UrlToParse.openStream()));
            Document doc = p.getDocument();
            NodeList nodeLst = doc.getElementsByTagName("city");

            for (int i = 1; i < nodeLst.getLength(); i++) {
                Element e = (Element) nodeLst.item(i);
                String CityId = e.getAttribute("id");
                String CityName = (((Element) nodeLst.item(i)).getElementsByTagName("name")).item(0).getTextContent();
                //System.out.println(CountryNameId + "   " + CountryName);
                CityIdName.put(CityId, CityName);
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public HashMap<String,String> GetCitiesId(){
        return CityIdName;
    }
}

class CountryIdParserWUA {
    private HashMap<String, String> CountryIdName = new HashMap<>();
    private String url;

    public CountryIdParserWUA(String url) {
        this.url = url;
        try {
            URL UrlToParse = new URL(url);
            DOMParser p = new DOMParser();
            p.parse(new InputSource(UrlToParse.openStream()));
            Document doc = p.getDocument();
            NodeList nodeLst = doc.getElementsByTagName("country");

            for (int i = 1; i < nodeLst.getLength(); i++) {
                Element e = (Element) nodeLst.item(i);
                String CountryId = e.getAttribute("id");
                String CountryName = (((Element) nodeLst.item(i)).getElementsByTagName("name")).item(0).getTextContent();
                //System.out.println(CountryNameId + "   " + CountryName);
                CountryIdName.put(CountryId, CountryName);
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public HashMap<String, String> GetCountryIdByNames(String s){
        List<String> inputCountryList = new ArrayList<>();
        if (s.indexOf(',')!=-1) {
            while (s.indexOf(',') != -1) {
                int i = s.indexOf(',');
                inputCountryList.add(s.substring(0, i));
                s = s.substring(i + 1);
            }
            inputCountryList.add(s);
        }else {
            inputCountryList.add(s);
        }

        HashMap<String,String> formattedCountryIdName = new HashMap<>();

        for(String id:CountryIdName.keySet()){
            for (int x=0; x< inputCountryList.size();x++){
                if (CountryIdName.get(id).equals(inputCountryList.get(x))){
                    formattedCountryIdName.put(id, CountryIdName.get(id));
                }
            }
        }

        return formattedCountryIdName;
    }

    public HashMap<String, String> GetCountryId() {

        return CountryIdName;

    }
}
