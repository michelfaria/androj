package component.modules.wiki;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WikipediaResponse {
    private String summary;
    private String url;
}
