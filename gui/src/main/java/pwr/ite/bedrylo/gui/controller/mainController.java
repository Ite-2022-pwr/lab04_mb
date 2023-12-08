package pwr.ite.bedrylo.gui.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import pwr.ite.bedrylo.client.models.*;
import pwr.ite.bedrylo.client.service.implementations.HTTPHandler;
import pwr.ite.bedrylo.client.service.implementations.StationMethods;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class mainController {

    private final StationMethods stationMethods = StationMethods.getInstance();
    
    private final HTTPHandler httpHandler = HTTPHandler.getInstance();
    
    private Station selectedStation;

    @FXML
    private Button stationLoadButton;
    @FXML
    private TableView<Station> stationTable;
    @FXML
    private TableColumn<Station,String> stationNameColumn;
    @FXML
    private Tab stationAQIndexTab;
    @FXML
    private BarChart stationAQIndexChart;
    @FXML
    private Tab stationReadingsTab;
    
    @FXML
    private void initialize() {
        stationAQIndexTab.setDisable(true);
        stationReadingsTab.setDisable(true);
    }


    @FXML
    public void loadStations(Event event) {
        stationMethods.loadAllStations();
        List<Station> stations = stationMethods.getAllStations();
        stationNameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getStationName()));
        stationTable.setItems(FXCollections.observableList(stations));
    }

    @FXML
    protected void cellSelectedEvent(MouseEvent mouseEvent) {
        if(stationTable.getSelectionModel().getSelectedItem() == null) {
            return;
        }
        selectedStation = stationTable.getSelectionModel().getSelectedItem();
        loadStationSensors();
        stationAQIndexTab.setDisable(false);
        stationReadingsTab.setDisable(false);
    }
    
    private void loadStationSensors() {
        List<Sensor> sensors = stationMethods.getSensors(selectedStation.getId());
        selectedStation.setSensors(sensors);
        for(Sensor sensor : sensors) {
            SensorReading data = stationMethods.getSensorReading(sensor.getId());
            sensor.setSensorReading(data);
        }
        AirQualityIndex index = stationMethods.getStationAirQualityIndex(selectedStation.getId());
        selectedStation.setAirQualityIndex(index);
        loadStationAQIndex();
    }
    
    
    private void loadStationAQIndex() {
        stationAQIndexChart.getData().clear();
        stationAQIndexChart.setTitle("Air quality index for '" + selectedStation.getStationName() + "'");
        createIndexChart(selectedStation.getAirQualityIndex());
    }

    private void createIndexChart(AirQualityIndex airQualityIndex) {
        XYChart.Series<String,Double> series = new XYChart.Series<String, Double>();
        series.setName("Indeks jakości powietrza");
        addDataToSeries("ST", airQualityIndex.getStSourceDataDate(), series, airQualityIndex.getStIndexLevel());
        addDataToSeries("SO2", airQualityIndex.getSo2SourceDataDate(), series, airQualityIndex.getSo2IndexLevel());
        addDataToSeries("NO2", airQualityIndex.getNo2SourceDataDate(), series, airQualityIndex.getNo2IndexLevel());
        addDataToSeries("PM10", airQualityIndex.getPm10SourceDataDate(), series, airQualityIndex.getPm10IndexLevel());
        addDataToSeries("PM25", airQualityIndex.getPm25SourceDataDate(), series, airQualityIndex.getPm25IndexLevel());
        addDataToSeries("O3", airQualityIndex.getO3SourceDataDate(), series, airQualityIndex.getO3IndexLevel());
        stationAQIndexChart.getData().add(series);
    }
    
    private void addDataToSeries(String key, String date, XYChart.Series<String,Double> series, IndexLevel indexLevel) {
        Sensor sensor = selectedStation
                .getSensors()
                .stream()
                .filter(s -> s.getParam()
                        .getParamCode()
                        .equals(key))
                .findFirst()
                .orElse(null);   
        if(sensor != null) {
            SensorReading sensorReading = sensor.getSensorReading();
            if(sensorReading != null) {
                SensorReadingValue sensorReadingValue = Arrays.stream(sensorReading
                        .getValues())
                        .filter(rv -> rv
                                .getDate()
                                .equals(date))
                        .findFirst()
                        .orElse(null); 
                if(sensorReadingValue != null) {
                    series.getData().add(new XYChart.Data<>(key + "\n" + indexLevel.getIndexLevelName(), sensorReadingValue.getValue()));
                }
            }
        }
    };
    
}