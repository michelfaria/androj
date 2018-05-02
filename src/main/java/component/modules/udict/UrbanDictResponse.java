package component.modules.udict;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UrbanDictResponse {
    private List<String> tags;
    @JsonProperty("result_type")
    private String resultType;
    private List<UrbanDictEntry> list;
    private List<String> sounds;
}
