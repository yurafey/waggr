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
import java.util.HashMap;

/**
 * Created by yuraf_000 on 05.06.2014.
 */
public class CityIdParserWUA {
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
