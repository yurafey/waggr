package Parsers;

import com.sun.org.apache.xerces.internal.parsers.DOMParser;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
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
public class CountryIdParserWUA {
    private HashMap<Integer, String> CountryIdName = new HashMap<>();
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
                Integer CountryId = Integer.parseInt(e.getAttribute("id"));
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
    public HashMap<Integer, String> GetCountryIdByNames(String s){
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

        HashMap<Integer,String> formattedCountryIdName = new HashMap<>();

        for(Integer id:CountryIdName.keySet()){
            for (int x=0; x< inputCountryList.size();x++){
                if (CountryIdName.get(id).equals(inputCountryList.get(x))){
                    formattedCountryIdName.put(id, CountryIdName.get(id));
                }
            }
        }

        return formattedCountryIdName;
    }

    public HashMap<Integer, String> GetCountryId() {

        return CountryIdName;

    }
}