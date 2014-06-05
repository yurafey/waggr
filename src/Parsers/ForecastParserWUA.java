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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by yuraf_000 on 05.06.2014.
 */
public class ForecastParserWUA{

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

