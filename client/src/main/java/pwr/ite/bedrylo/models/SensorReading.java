package pwr.ite.bedrylo.models;

import lombok.Data;

@Data
public class SensorReading {
    
    private String key;
    
    private SensorReadingValue[] values;
    
}
