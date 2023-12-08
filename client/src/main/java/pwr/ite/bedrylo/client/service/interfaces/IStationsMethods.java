package pwr.ite.bedrylo.client.service.interfaces;

import pwr.ite.bedrylo.client.models.Sensor;
import pwr.ite.bedrylo.client.models.Station;
import pwr.ite.bedrylo.client.models.AirQualityIndex;
import pwr.ite.bedrylo.client.models.SensorReading;

import java.util.List;

public interface IStationsMethods {
    
    List<Station> getAllStations();
    
    List<Sensor> getSensors(Integer stationId);
    
    SensorReading getSensorReading(Integer sensorId);
    
    AirQualityIndex getStationAirQualityIndex(Integer stationId);
    
    Station getStationById(Integer stationId);
    
    void loadAllStations();
    
}
