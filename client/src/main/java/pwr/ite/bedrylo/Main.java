package pwr.ite.bedrylo;

import com.fasterxml.jackson.databind.JsonNode;
import pwr.ite.bedrylo.service.HTTPHandler;


public class Main {
    
    public static void main(String[] args) {
        HTTPHandler httpHandler = HTTPHandler.getInstance();
        System.out.println(httpHandler.get("/station/findAll", JsonNode.class));
    }

}