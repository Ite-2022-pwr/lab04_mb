package pwr.ite.bedrylo.gui.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import pwr.ite.bedrylo.client.models.*;
import pwr.ite.bedrylo.client.service.implementations.HTTPHandler;
import pwr.ite.bedrylo.client.service.implementations.StationMethods;

import java.util.Arrays;
import java.util.List;

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
    private TabPane pointPlotTab;

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
        createSensorReadingsDataTabs(selectedStation);
    }
    
    
    private void loadStationAQIndex() {
        stationAQIndexChart.getData().clear();
        stationAQIndexChart.setTitle("Indeks jakości powietrza dla: " + selectedStation.getStationName());
        createIndexChart(selectedStation.getAirQualityIndex());
    }

    private void createIndexChart(AirQualityIndex airQualityIndex) {
        XYChart.Series<String,Double> series = new XYChart.Series<String, Double>();
        stationAQIndexChart.getData().add(series);
        stationAQIndexChart.setLegendVisible(false);
        series.setName("Indeks jakości powietrza");
        addDataToSeries("ST", airQualityIndex.getStSourceDataDate(), series, airQualityIndex.getStIndexLevel());
        addDataToSeries("SO2", airQualityIndex.getSo2SourceDataDate(), series, airQualityIndex.getSo2IndexLevel());
        addDataToSeries("NO2", airQualityIndex.getNo2SourceDataDate(), series, airQualityIndex.getNo2IndexLevel());
        addDataToSeries("PM10", airQualityIndex.getPm10SourceDataDate(), series, airQualityIndex.getPm10IndexLevel());
        addDataToSeries("PM25", airQualityIndex.getPm25SourceDataDate(), series, airQualityIndex.getPm25IndexLevel());
        addDataToSeries("O3", airQualityIndex.getO3SourceDataDate(), series, airQualityIndex.getO3IndexLevel());
        System.out.println(series.getData().size());
        if (series.getData().isEmpty()) {
            stationAQIndexChart.setTitle("Brak danych dla wybranej stacji");
        }
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
                    String barColor = determineBarColor(indexLevel.getId());
                    XYChart.Data<String, Double> data = new XYChart.Data<>(key + "\n" + indexLevel.getIndexLevelName(), sensorReadingValue.getValue());
                    series.getData().add(data);
                    if (data.getNode() != null) {
                            data.getNode().setStyle("-fx-bar-fill: " + barColor);
                    } else {
                        System.out.println("null");
                    }
                    
                    
                }
            }
        }
    };
    
    private String determineBarColor(int indexLevel){
        return switch (indexLevel) {
            case -1 -> "#000000";
            case 0 -> "#2cba00";
            case 1 -> "#a3ff00";
            case 2 -> "#fff400";
            case 3 -> "#ffa700";
            case 4 -> "#ff0000";
            case 5 -> "#000000";
            default -> throw new IllegalStateException("niespodziewana wartość: " + indexLevel);
        };
    }

    private void createSensorReadingsDataTabs(Station station) {
        pointPlotTab.getTabs().clear();
        for(Sensor sensor : station.getSensors()) {
            SensorReading sensorData = sensor.getSensorReading();
            String paramCode = sensor.getParam().getParamCode();

            CategoryAxis xAxis = new CategoryAxis();
            NumberAxis yAxis = new NumberAxis();
            LineChart<String, Number> chart = new LineChart<>(xAxis, yAxis);
            chart.setTitle("Dane w czasie dla: " + paramCode);
            chart.setLegendVisible(false);
            LineChart.Series<String,Number> series = new LineChart.Series<>();
            series.setName(paramCode + " wartość");
            for(SensorReadingValue value : sensorData.getValues()) {
                if(value.getValue() != null) {
                    series.getData().add(new LineChart.Data<>(value.getDate(), value.getValue()));
                } else {
                    series.getData().add(new LineChart.Data<>(value.getDate(), 0.0));
                }
            }
            chart.getData().add(series);
            series.getNode().setStyle("-fx-stroke: #000000");
            for(LineChart.Data<String, Number> data : series.getData()) {
                data.getNode().setScaleX(0.5);
                data.getNode().setScaleY(0.5);
                data.getNode().setStyle("-fx-background-color: #000000");
            }
            Tab tab = new Tab(paramCode, chart);
            pointPlotTab.getTabs().add(tab);
        }
    }
    
}