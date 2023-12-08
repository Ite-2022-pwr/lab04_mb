package pwr.ite.bedrylo.client.service.interfaces;

public interface IHTTPHandler {
    
    <R> R get(String url, Class<R> responseType);
    
}
