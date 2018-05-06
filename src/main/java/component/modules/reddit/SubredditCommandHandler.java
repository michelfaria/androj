package component.modules.reddit;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.jetbrains.annotations.NotNull;

import command.Command;
import command.handler.AbstractCommandHandler;
import command.handler.CommandHandler;
import command.handler.CommandHandlingFacade;
import command.strategy.DecoratedReplier;
import command.validation.NoArgsValidator;
import component.command.CommandHandlingFacadeBuilder;
import util.PickRandom;

public abstract class SubredditCommandHandler extends AbstractCommandHandler implements CommandHandler {

	private Random random;
	private List<String> subreddits;
	private CommandHandlingFacade facade;
	private RedditClientWrapper reddit;

	public SubredditCommandHandler(String cmdId, RedditClientWrapper reddit, CommandHandlingFacadeBuilder builder,
			Random random, List<String> subreddits) {
		this.random = random;
		this.subreddits = subreddits;
		this.reddit = reddit;
		this.facade = builder.setCmdId(cmdId).setReplier(new DecoratedReplier(":globe_with_meridians:"))
				.setValidators(Arrays.asList(new NoArgsValidator())).setValidatedCommandHandler(this::handle_).build();
	}

	public void handle_(Command c) {
		sendTyping(c.getEvent().getTextChannel());
		replyTo(c, formatRedditResponse(reddit.fetchRandomPost(PickRandom.in(subreddits, random))));
	}

	public String formatRedditResponse(RedditResponse response) {
		return response.getTitle() + " " + response.getLink() + "\n\n" + response.getSelfText();
	}

	@NotNull
	@Override
	public CommandHandlingFacade getFacade() {
		return facade;
	}
}
