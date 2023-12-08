module pwr.ite.bedrylo.client {
    requires java.net.http;
    requires com.fasterxml.jackson.databind;
    requires lombok;
    
    exports pwr.ite.bedrylo.client.models;
    exports pwr.ite.bedrylo.client.service.interfaces;
    exports pwr.ite.bedrylo.client.service.implementations;
    opens pwr.ite.bedrylo.client.models to com.fasterxml.jackson.databind;
}