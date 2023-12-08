package pwr.ite.bedrylo.client.service.implementations;

import pwr.ite.bedrylo.client.models.Sensor;
import pwr.ite.bedrylo.client.models.Station;
import pwr.ite.bedrylo.client.models.AirQualityIndex;
import pwr.ite.bedrylo.client.models.SensorReading;
import pwr.ite.bedrylo.client.service.interfaces.IStationsMethods;

import java.util.List;

public class StationMethods implements IStationsMethods{

    private static StationMethods INSTANCE = null;
    
    private final HTTPHandler httpHandler = HTTPHandler.getInstance();
    
    private List<Station> stations;

    @Override
    public List<Station> getAllStations() {
        return this.stations;
    }

    @Override
    public List<Sensor> getSensors(Integer stationId) {
        List<Sensor> sensors = List.of(httpHandler.get("/station/sensors/" + stationId, Sensor[].class));
        return sensors;
    }

    @Override
    public SensorReading getSensorReading(Integer sensorId) {
        SensorReading sensorReading = httpHandler.get("/data/getData/" + sensorId, SensorReading.class);
        return sensorReading;
    }

    @Override
    public AirQualityIndex getStationAirQualityIndex(Integer stationId) {
        AirQualityIndex airQualityIndex = httpHandler.get("/aqindex/getIndex/" + stationId, AirQualityIndex.class);
        return airQualityIndex;
    }

    @Override
    public Station getStationById(Integer stationId) {
        Station station = stations.stream().filter(st -> st.getId().equals(stationId)).findFirst().orElse(null);
        return station;
    }

    @Override
    public void loadAllStations() {
        this.stations = List.of(httpHandler.get("/station/findAll", Station[].class));
    }
    
    public static StationMethods getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new StationMethods();
        }
        return INSTANCE;
    }
}
