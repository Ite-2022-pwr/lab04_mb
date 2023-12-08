package pwr.ite.bedrylo.client.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(value = {"gegrLat", "gegrLon", "addressStreet"})
public class Station {
    
    private Integer id;
    
    private String stationName;
    
    private City city;
    
    @JsonIgnore
    private List<Sensor> sensors;
    
    private AirQualityIndex airQualityIndex;
    
    
    
}
