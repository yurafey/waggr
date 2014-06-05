package Parsers;

import com.sun.org.apache.xerces.internal.parsers.DOMParser;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by yuraf_000 on 05.06.2014.
 */
public class ForecastParserYa {
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
    public ForecastParserYa(String url){
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
