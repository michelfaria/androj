package component.modules.reddit;

import command.handler.RegisteredCommandHandler;
import component.command.CommandHandlingFacadeBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Random;

@Component
@ConditionalOnBean(RedditConfig.class)
public class SmartCommandHandler extends SubredditCommandHandler implements RegisteredCommandHandler {

	@Autowired
	public SmartCommandHandler(RedditClientWrapper reddit, CommandHandlingFacadeBuilder builder) {
		this(reddit, builder, new Random());
	}

	public SmartCommandHandler(RedditClientWrapper reddit, CommandHandlingFacadeBuilder builder, Random random) {
		super("smart", reddit, builder, random, Arrays.asList("iamverysmart"));
	}

	@Override
	public String help() {
		return "You need an IQ of over 300 to use this command.";
	}
}
