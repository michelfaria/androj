package component.modules.udict;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty("bot.udict.enabled")
public class UdictConfig {
}
