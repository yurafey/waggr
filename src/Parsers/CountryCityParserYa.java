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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by yuraf_000 on 05.06.2014.
 */
public class CountryCityParserYa {
    private ListMultimap<Integer, Integer> CountryCityMap = ArrayListMultimap.create();
    private HashMap<Integer,String> CountryIdMap = new HashMap<>();
    private HashMap<Integer,String> CityIdMap = new HashMap<>();
    private int cityCounter = 0;
    public CountryCityParserYa(String url) {
        System.out.println("Retrieving lists countries and cities in Yandex DB...");
        try {
            String resCityId = null;
            URL UrlToParse = new URL(url);
            DOMParser p = new DOMParser();
            System.out.println(url.toString());
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
                                    cityCounter++;
                                }
                                else {
                                    CountryCityMap.put(CountryId, tempCityId );
                                    CityIdMap.put(tempCityId,CitiesLst.item(x).getTextContent().substring(0, CitiesLst.item(x).getTextContent().indexOf(",")));
                                    cityCounter++;
                                }
                                break;

                            default:
                                CountryCityMap.put(CountryId, tempCityId);
                                CityIdMap.put(tempCityId,CitiesLst.item(x).getTextContent());
                                cityCounter++;

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
    public Integer AllCitiesLength() { return cityCounter; }
    public ListMultimap<Integer, Integer> GetCountryCityMapByNames(String Names) {
        ListMultimap<Integer, Integer> CountryCityMapLocal = ArrayListMultimap.create();
        List<String> inputCountryList = new ArrayList<>();
        if (Names.indexOf(',')!=-1) {
            while (Names.indexOf(',') != -1) {
                int i = Names.indexOf(',');
                inputCountryList.add(Names.substring(0, i));
                Names = Names.substring(i + 1);
            }
            inputCountryList.add(Names);
        }else {
            inputCountryList.add(Names);
        }

        for(Integer id:CountryIdMap.keySet()){
            for (int x=0; x< inputCountryList.size();x++){
                if (CountryIdMap.get(id).equals(inputCountryList.get(x))){
                    CountryCityMapLocal.putAll(id,CountryCityMap.get(id));
                }
            }
        }

        return CountryCityMapLocal;
    }
    public ListMultimap<Integer, Integer> GetCountryCityMap() {
        return CountryCityMap;
    }
    public HashMap<Integer,String> GetCityIdMap() { return CityIdMap; }
    public HashMap<Integer,String> GetCountryIdMap (){
        return CountryIdMap;
    }

}
