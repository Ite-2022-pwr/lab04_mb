package pwr.ite.bedrylo.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties()
public class Param {
    
    private Integer id;
    
    private String name;
    
    private String formula;
    
    private String code;
    
}
