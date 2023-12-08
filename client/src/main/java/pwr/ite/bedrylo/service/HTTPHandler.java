package pwr.ite.bedrylo.service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import com.fasterxml.jackson.databind.ObjectMapper;


public class HTTPHandler implements IHTTPHandler{
    
    public static HTTPHandler INSTANCE = null;
    
    private static String baseURL = "https://api.gios.gov.pl/pjp-api/rest";
    
    private static final ObjectMapper objectMapper = new ObjectMapper();
    
    @Override
    public <R> R get(String url, Class<R> responseType) {
        try {
            HttpRequest request = HttpRequest
                    .newBuilder()
                    .uri(URI.create(baseURL + url))
                    .GET()
                    .build();
            
            HttpResponse<String> response = HttpClient
                    .newHttpClient()
                    .send(request, HttpResponse.BodyHandlers.ofString());
            
            return objectMapper.readValue(response.body(), responseType);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
    
    public static HTTPHandler getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new HTTPHandler();
        }
        return INSTANCE;
    }    
    
}
