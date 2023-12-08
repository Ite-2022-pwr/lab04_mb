package pwr.ite.bedrylo.client.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties()
public class Sensor {
    
    private Integer id;
    
    private Integer stationId;
    
    private Param param;
    
    @JsonIgnore
    private SensorReading sensorReading;
    
    
}
