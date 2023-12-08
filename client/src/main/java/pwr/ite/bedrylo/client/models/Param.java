package pwr.ite.bedrylo.client.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties()
public class Param {

    private String paramName;

    private String paramFormula;

    private String paramCode;

    private String idParam;
}
