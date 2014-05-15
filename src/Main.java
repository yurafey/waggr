import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import com.sun.org.apache.xerces.internal.parsers.DOMParser;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class Main {

    private static List<String> foundCountryCities;

    public static void main(String[] args) {
        while (true) {
            CitiesParserYandex CParserY = new CitiesParserYandex("http://weather.yandex.ru/static/cities.xml");
            ListMultimap<String, String> CountryCityMap = CParserY.GetCountryCityMap();
            System.out.print("Страна:");
            Scanner sc = new Scanner(System.in);
            String enteredCountryName = sc.next();

            foundCountryCities = CountryCityMap.get(enteredCountryName);
            if (foundCountryCities.size()==0) {
                System.out.println("Такой страны нет в базе :(");
                continue;
            }
            System.out.println("Города: "+CountryCityDivide(foundCountryCities).keySet());
            System.out.print("Введите город: ");
            String enteredCityName = sc.next();

            if (CountryCityDivide(foundCountryCities).get(enteredCityName) == null) {
                System.out.println("Такого города нет в базе страны " + enteredCountryName + " :(");
                continue;
            }
            WeatherParserYandex pars = new WeatherParserYandex();
            List<Weather> printLst = new ArrayList<Weather>();

            System.out.print("Введите 'C', чтобы узнать текущее состояние погоды, 'F' - прогноз погоды: ");
            String typeOfForecast = sc.next();
            switch (typeOfForecast){
                case "C":
                    printLst = pars.GetCurrent("http://export.yandex.ru/weather-ng/forecasts/" + CountryCityDivide(foundCountryCities).get(enteredCityName) + ".xml");
                    break;
                case "F":
                    printLst = pars.GetWeekPredict("http://export.yandex.ru/weather-ng/forecasts/" + CountryCityDivide(foundCountryCities).get(enteredCityName) + ".xml");
                    break;
                default:
                    System.out.println("В данном поле можно ввести только 'C' или 'F' !");
                    continue;
            }

            for (int i = 0; i < printLst.size(); i++) {
                System.out.println(printLst.get(i).GetToString());
            }
        }
    }



    private static HashMap<String,String> CountryCityDivide(List<String> foundCountryCities){
        String s1,s2;
        HashMap <String,String> CityIdMap = new HashMap<>();
        for (int i=0; i<foundCountryCities.size();i++){
            String s = foundCountryCities.get(i);
            s1 = s.substring(0,s.indexOf('_'));
            s2 = s.substring(s.indexOf('_')+1);
            CityIdMap.put(s1,s2);

        }

        return  CityIdMap;
    }

}
class Weather{
    private Date date;
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
    public List<Weather> GetWeekPredict(String url) {
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

    public List<Weather> GetCurrent(String url) {
        List<Weather> resList = new ArrayList<Weather>();
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
                resList.add(weatherTmp);
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
        return resList;
    }
}


class CitiesParserYandex {

    ListMultimap<String, String> CountryCityMap = ArrayListMultimap.create();

    public CitiesParserYandex(String url) {
        ParseXMLCities(url);
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
                Element eElement = (Element) nNode;
                NodeList CitiesLst = eElement.getElementsByTagName("city");
                for (int x = 0; x < CitiesLst.getLength(); x++) {
                    Element CElement = (Element) CitiesLst.item(x);

                    switch (eElement.getAttribute("name").toString()) {
                        case "США":
                            if (CitiesLst.item(x).getTextContent().indexOf(",") == -1)
                                CountryCityMap.put(eElement.getAttribute("name"), CitiesLst.item(x).getTextContent() + "_" + CElement.getAttribute("id"));
                            else
                                CountryCityMap.put(eElement.getAttribute("name"), CitiesLst.item(x).getTextContent().substring(0, CitiesLst.item(x).getTextContent().indexOf(",")) + "_" + CElement.getAttribute("id"));
                            break;

                        default:
                            CountryCityMap.put(eElement.getAttribute("name"), CitiesLst.item(x).getTextContent() + "_" + CElement.getAttribute("id"));

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