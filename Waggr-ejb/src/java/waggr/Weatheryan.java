/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package waggr;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author URI
 */
@Entity
@Table(name = "weatheryan")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Weatheryan.findAll", query = "SELECT w FROM Weatheryan w"),
    @NamedQuery(name = "Weatheryan.findById", query = "SELECT w FROM Weatheryan w WHERE w.id = :id"),
    @NamedQuery(name = "Weatheryan.findByTimestamp", query = "SELECT w FROM Weatheryan w WHERE w.timestamp = :timestamp"),
    @NamedQuery(name = "Weatheryan.findByCityId", query = "SELECT w FROM Weatheryan w WHERE w.cityId = :cityId"),
    @NamedQuery(name = "Weatheryan.findByCityName", query = "SELECT w FROM Weatheryan w WHERE w.cityName = :cityName"),
    @NamedQuery(name = "Weatheryan.findByTemperature", query = "SELECT w FROM Weatheryan w WHERE w.temperature = :temperature"),
    @NamedQuery(name = "Weatheryan.findByPressure", query = "SELECT w FROM Weatheryan w WHERE w.pressure = :pressure"),
    @NamedQuery(name = "Weatheryan.findByHumidity", query = "SELECT w FROM Weatheryan w WHERE w.humidity = :humidity"),
    @NamedQuery(name = "Weatheryan.findByWindSpeed", query = "SELECT w FROM Weatheryan w WHERE w.windSpeed = :windSpeed"),
    @NamedQuery(name = "Weatheryan.findByWindDirection", query = "SELECT w FROM Weatheryan w WHERE w.windDirection = :windDirection"),
    @NamedQuery(name = "Weatheryan.findByCountryName", query = "SELECT w FROM Weatheryan w WHERE w.countryName = :countryName"),
    @NamedQuery(name = "Weatheryan.findByCityAndCountryName", query = "SELECT w FROM Weatheryan w WHERE w.cityName = :cityName AND w.countryName = :countryName"),
    @NamedQuery(name = "Weatheryan.findCurrentWeather", query = "SELECT w FROM Weatheryan w WHERE w.cityName = :cityName AND w.countryName = :countryName AND w.isPredict = false")})
public class Weatheryan implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "timestamp")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;
    @Column(name = "city_id")
    private Integer cityId;
    @Size(max = 2147483647)
    @Column(name = "city_name")
    private String cityName;
    @Column(name = "temperature")
    private Integer temperature;
    @Column(name = "pressure")
    private Integer pressure;
    @Column(name = "humidity")
    private Integer humidity;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "wind_speed")
    private Double windSpeed;
    @Size(max = 2147483647)
    @Column(name = "wind_direction")
    private String windDirection;
    @Size(max = 2147483647)
    @Column(name = "country_name")
    private String countryName;
    @Column(name = "is_predict")
    private Boolean isPredict;

    public Weatheryan() {
    }

    public Weatheryan(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Integer getTemperature() {
        return temperature;
    }

    public void setTemperature(Integer temperature) {
        this.temperature = temperature;
    }

    public Integer getPressure() {
        return pressure;
    }

    public void setPressure(Integer pressure) {
        this.pressure = pressure;
    }

    public Integer getHumidity() {
        return humidity;
    }

    public void setHumidity(Integer humidity) {
        this.humidity = humidity;
    }

    public Double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(Double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(String windDirection) {
        this.windDirection = windDirection;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public Boolean getIsPredict() {
        return isPredict;
    }

    public void setIsPredict(Boolean isPredict) {
        this.isPredict = isPredict;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Weatheryan)) {
            return false;
        }
        Weatheryan other = (Weatheryan) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "waggr.Weatheryan[ id=" + id + " ]";
    }
    
}
