package pwr.ite.bedrylo.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import pwr.ite.bedrylo.client.models.*;
import pwr.ite.bedrylo.client.service.interfaces.*;
import pwr.ite.bedrylo.client.service.implementations.*;
import pwr.ite.bedrylo.gui.misc.DaysLeft;


import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class GIOSApp extends Application {
    
    private final HTTPHandler httpHandler = HTTPHandler.getInstance();
    private final StationMethods stationMethods = StationMethods.getInstance();
    
    
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(GIOSApp.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("GIOŚ pogodynkaPRO+ v2023 evaluation version: "+ DaysLeft.getDaysLeft() +" days left");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}