package component.modules.wiki;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty("bot.wiki.enabled")
public class WikiConfig {
}
