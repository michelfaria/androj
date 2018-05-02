package component;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class BotConfig {
    @Value("${bot.token:}")
    @Setter
    private String token;

    @Value("${bot.prefix}")
    private String prefix;

}
