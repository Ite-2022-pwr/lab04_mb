module pwr.ite.bedrylo.gui {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;


    opens pwr.ite.bedrylo.gui to javafx.fxml;
    exports pwr.ite.bedrylo.gui;
}