package component.modules.reddit;

import command.handler.RegisteredCommandHandler;
import component.command.CommandHandlingFacadeBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Random;

@Component
public class CringeCommandHandler extends SubredditCommandHandler implements RegisteredCommandHandler {

	@Autowired
	public CringeCommandHandler(RedditClientWrapper reddit, CommandHandlingFacadeBuilder builder) {
		this(reddit, builder, new Random());
	}

	public CringeCommandHandler(RedditClientWrapper reddit, CommandHandlingFacadeBuilder builder, Random random) {
		super("cringe", reddit, builder, random, Arrays.asList("cringepics", "CringeAnarchy"));
	}

	@Override
	public String help() {
		return "Get some cringe.";
	}
}
