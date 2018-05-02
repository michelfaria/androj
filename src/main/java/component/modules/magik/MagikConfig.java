package component.modules.magik;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty("bot.magik.enabled")
public class MagikConfig {
}
