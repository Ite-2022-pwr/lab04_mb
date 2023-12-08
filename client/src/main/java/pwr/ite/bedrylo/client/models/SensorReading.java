package pwr.ite.bedrylo.client.models;

import lombok.Data;

@Data
public class SensorReading {
    
    private String key;
    
    private SensorReadingValue[] values;
    
}
