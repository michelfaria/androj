package component.modules.reddit;

import net.dean.jraw.RedditClient;
import net.dean.jraw.http.OkHttpNetworkAdapter;
import net.dean.jraw.http.UserAgent;
import net.dean.jraw.oauth.Credentials;
import net.dean.jraw.oauth.OAuthHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@ConditionalOnBean(RedditConfig.class)
public class RedditClientFactoryBean implements FactoryBean<RedditClient> {
	private static final Logger L = LoggerFactory.getLogger(RedditClientFactoryBean.class);

	private RedditConfig cfg;

	@Autowired
	public RedditClientFactoryBean(RedditConfig cfg) {
		this.cfg = cfg;
	}

	@Override
	public RedditClient getObject() {
		RedditClient redditClient = redditClient(credentials(), userAgent());
		redditClient.setLogHttp(false);
		L.info("Logged in Reddit: " + redditClient.me().about());
		return redditClient;
	}

	private RedditClient redditClient(Credentials credentials, UserAgent userAgent) {
		return OAuthHelper.automatic(new OkHttpNetworkAdapter(userAgent), credentials);
	}

	private Credentials credentials() {
		return Credentials.script(Objects.requireNonNull(cfg.getUsername()), Objects.requireNonNull(cfg.getPassword()),
				Objects.requireNonNull(cfg.getClientId()), Objects.requireNonNull(cfg.getClientSecret()));
	}

	private UserAgent userAgent() {
		return new UserAgent("bot", "my.cool.bot", "1.0.0", Objects.requireNonNull(cfg.getUsername()));
	}

	@Override
	public Class<?> getObjectType() {
		return RedditClient.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}
}
