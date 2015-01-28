package BusinessLogic;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by yuraf_000 on 05.06.2014.
 */

public class Weather implements Serializable{
    private Date date = new Date();
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    private int temperature;
    private int pressure;
    private float wind_speed;
    private String wind_direction;
    private int humidity;
    private Boolean isPredict = true;

    public void setHumidity(int humidity){
        this.humidity = humidity;
    }
    public void setIsPredict(Boolean is){
        this.isPredict = is;
    }
    public void setDate(Date date){
        this.date = date;
    }
    public void setTemperature(int temperature){
        this.temperature  = temperature;
    }
    public void setPressure(int pressure){
        this.pressure  = pressure;
    }
    public void setWindSpeed(float wind_speed){
        this.wind_speed  = wind_speed;
    }
    public void setWindDirection(String wind_direction){
        this.wind_direction  = wind_direction;
    }

    public int getHumidity() {
        return humidity;
    }
    public Boolean getIsPredict(){
        return isPredict;
    }
    public Date getDate(){
        return date;
    }
    public int getTemperature(){
        return temperature;
    }
    public int getPressure(){
        return pressure;
    }
    public String getWindDirection() {
        return wind_direction;
    }
    public float getWindSpeed(){
        return wind_speed;
    }

    public String getToString(){
        if (isPredict)
            return String.format("Forecast date %s %s : temperature %d C*, air pressure %d mm, humidity %d, wind %.1f %s",
                    dateFormat.format(date).substring(0,dateFormat.format(date).indexOf(" ")),
                    dateFormat.format(date).substring(dateFormat.format(date).indexOf(" ")),
                    //dateFormat.format(date).substring(dateFormat.format(date).indexOf(" ")).equals(" 13:00")? "DAY" : "NIGHT",
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