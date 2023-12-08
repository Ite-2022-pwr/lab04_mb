package pwr.ite.bedrylo.service.interfaces;

import pwr.ite.bedrylo.models.AirQualityIndex;
import pwr.ite.bedrylo.models.Sensor;
import pwr.ite.bedrylo.models.SensorReading;
import pwr.ite.bedrylo.models.Station;

import java.util.List;

public interface IStationsMethods {
    
    List<Station> getAllStations();
    
    List<Sensor> getSensors(Integer stationId);
    
    SensorReading getSensorReading(Integer sensorId);
    
    AirQualityIndex getStationAirQualityIndex(Integer stationId);
    
    Station getStationById(Integer stationId);
    
    void loadAllStations();
    
}
