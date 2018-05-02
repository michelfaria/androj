package component.modules.udict;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UrbanDictEntry {
    @JsonProperty("defid")
    private Long defId;
    private String word;
    private String author;
    private String permalink;
    private String definition;
    private String example;
    @JsonProperty("thumbs_up")
    private Integer thumbsUp;
    @JsonProperty("thumbs_down")
    private Integer thumbsDown;
    @JsonProperty("current_vote")
    private String currentVote;
}
