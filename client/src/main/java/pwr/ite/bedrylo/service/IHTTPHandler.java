package pwr.ite.bedrylo.service;

public interface IHTTPHandler {
    
    <R> R get(String url, Class<R> responseType);
    
}
