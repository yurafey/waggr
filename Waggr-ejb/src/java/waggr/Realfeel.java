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
@Table(name = "realfeel")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Realfeel.findAll", query = "SELECT r FROM Realfeel r"),
    @NamedQuery(name = "Realfeel.findById", query = "SELECT r FROM Realfeel r WHERE r.id = :id"),
    @NamedQuery(name = "Realfeel.findByLogin", query = "SELECT r FROM Realfeel r WHERE r.login = :login"),
    @NamedQuery(name = "Realfeel.findByTemperature", query = "SELECT r FROM Realfeel r WHERE r.temperature = :temperature"),
    @NamedQuery(name = "Realfeel.findByPressure", query = "SELECT r FROM Realfeel r WHERE r.pressure = :pressure"),
    @NamedQuery(name = "Realfeel.findByHumidity", query = "SELECT r FROM Realfeel r WHERE r.humidity = :humidity"),
    @NamedQuery(name = "Realfeel.findByWindSpeed", query = "SELECT r FROM Realfeel r WHERE r.windSpeed = :windSpeed"),
    @NamedQuery(name = "Realfeel.findByWindDirection", query = "SELECT r FROM Realfeel r WHERE r.windDirection = :windDirection"),
    @NamedQuery(name = "Realfeel.findByCityName", query = "SELECT r FROM Realfeel r WHERE r.cityName = :cityName"),
    @NamedQuery(name = "Realfeel.findByCountryName", query = "SELECT r FROM Realfeel r WHERE r.countryName = :countryName"),
    @NamedQuery(name = "Realfeel.findByCityNameAndCountryName", query = "SELECT r FROM Realfeel r WHERE r.cityName = :cityName AND r.countryName = :countryName ORDER BY r.timestamp DESC"),
    @NamedQuery(name = "Realfeel.findByTimestamp", query = "SELECT r FROM Realfeel r WHERE r.timestamp = :timestamp")})
public class Realfeel implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 2147483647)
    @Column(name = "login")
    private String login;
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
    @Column(name = "city_name")
    private String cityName;
    @Size(max = 2147483647)
    @Column(name = "country_name")
    private String countryName;
    @Column(name = "timestamp")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;

    public Realfeel() {
    }

    public Realfeel(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
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

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
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
        if (!(object instanceof Realfeel)) {
            return false;
        }
        Realfeel other = (Realfeel) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "waggr.Realfeel[ id=" + id + " ]";
    }
    
}
