package component.modules.reddit;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@Getter
@ConditionalOnProperty("bot.reddit.enabled")
public class RedditConfig {

	@Value("${bot.reddit.username}")
	private String username;

	@Value("${bot.reddit.password}")
	private String password;

	@Value("${bot.reddit.clientId}")
	private String clientId;

	@Value("${bot.reddit.clientSecret}")
	private String clientSecret;
}
