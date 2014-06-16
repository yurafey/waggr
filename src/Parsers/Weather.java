package Parsers;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by yuraf_000 on 05.06.2014.
 */

public class Weather{
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