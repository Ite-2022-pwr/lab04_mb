package pwr.ite.bedrylo.service.interfaces;

public interface IHTTPHandler {
    
    <R> R get(String url, Class<R> responseType);
    
}
