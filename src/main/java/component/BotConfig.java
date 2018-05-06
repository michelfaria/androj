package component;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@Getter
public class BotConfig {
	@Value("${bot.token:}")
	@Setter
	private String token;

	@Value("${bot.prefix}")
	private String prefix;

	private List<String> admins;

	@Autowired
	public BotConfig(@Value("${bot.admins}") String admins) {
		this.admins = Arrays.asList(admins.split(","));
	}
}
