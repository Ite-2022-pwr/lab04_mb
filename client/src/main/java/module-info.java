module client {
    requires java.net.http;
    requires com.fasterxml.jackson.databind;
    requires lombok;
    
    exports pwr.ite.bedrylo.models;
    exports pwr.ite.bedrylo.service.interfaces;
    exports pwr.ite.bedrylo.service.implementations;
    opens pwr.ite.bedrylo.models to com.fasterxml.jackson.databind;
}