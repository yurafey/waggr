package Parsers;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
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
import java.util.HashMap;

/**
 * Created by yuraf_000 on 05.06.2014.
 */
public class CountryCityParserYa {
    private ListMultimap<Integer, Integer> CountryCityMap = ArrayListMultimap.create();
    private HashMap<Integer,String> CountryIdMap = new HashMap<>();
    private HashMap<Integer,String> CityIdMap = new HashMap<>();
    private int counter = 0;
    public CountryCityParserYa(String url) {
        ParseXMLCities(url);

        System.out.println("Количество городов: " + counter);
    }

    public ListMultimap<Integer, Integer> GetCountryCityMap() {
        return CountryCityMap;
    }
    public HashMap<Integer,String> GetCityIdMap() { return CityIdMap; }
    public HashMap<Integer,String> GetCountryIdMap (){
        return CountryIdMap;
    }
    private void ParseXMLCities(String url) {
        try {
            String resCityId = null;
            URL UrlToParse = new URL(url);
            DOMParser p = new DOMParser();
            p.parse(new InputSource(UrlToParse.openStream()));
            Document doc = p.getDocument();
            NodeList nodeLst = doc.getElementsByTagName("country");
            int CountryId = 0;
            for (int i = 0; i < nodeLst.getLength(); i++) {
                Node nNode = nodeLst.item(i);
                if (nNode.getNodeType()==Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    CountryIdMap.put(CountryId,eElement.getAttribute("name").toString());//создаем карту стран
                    NodeList CitiesLst = eElement.getElementsByTagName("city");
                    for (int x = 0; x < CitiesLst.getLength(); x++) {
                        Element CElement = (Element) CitiesLst.item(x);
                        //CountryIdMap.put()
                        int tempCityId = Integer.parseInt(CElement.getAttribute("id"));
                        switch (eElement.getAttribute("name").toString()) {
                            case "США":
                                if (CitiesLst.item(x).getTextContent().indexOf(",") == -1) {
                                    CountryCityMap.put(CountryId, tempCityId);
                                    CityIdMap.put(tempCityId,CitiesLst.item(x).getTextContent());
                                    counter++;
                                }
                                else {
                                    CountryCityMap.put(CountryId, tempCityId );
                                    CityIdMap.put(tempCityId,CitiesLst.item(x).getTextContent().substring(0, CitiesLst.item(x).getTextContent().indexOf(",")));
                                    counter++;
                                }
                                break;

                            default:
                                CountryCityMap.put(CountryId, tempCityId);
                                CityIdMap.put(tempCityId,CitiesLst.item(x).getTextContent());
                                counter++;

                        }

                    }
                    CountryId++;
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
