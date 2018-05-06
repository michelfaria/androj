package component.command;

import command.Command;
import command.CommandParser;
import component.BotConfig;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class CommandParserImpl implements CommandParser {
	private final BotConfig cfg;

	@Autowired
	public CommandParserImpl(BotConfig cfg) {
		this.cfg = cfg;
	}

	@Override
	public Optional<Command> parse(MessageReceivedEvent event) {
		Objects.requireNonNull(event);
		final String content = event.getMessage().getContentRaw();
		return isCommand(content) ? Optional.of(parse(event, tokenize(content))) : Optional.empty();
	}

	private boolean isCommand(String content) {
		return content.startsWith(cfg.getPrefix());
	}

	private Command parse(MessageReceivedEvent event, List<String> tokenizedMsg) {
		return new Command(extractCmdId(tokenizedMsg), extractCmdArgs(tokenizedMsg), event);
	}

	private List<String> tokenize(String content) {
		return Arrays.asList(content.split(" "));
	}

	private String extractCmdId(List<String> tokens) {
		return tokens.get(0).substring(cfg.getPrefix().length());
	}

	private List<String> extractCmdArgs(List<String> tokenizedMsg) {
		return tokenizedMsg.size() > 1 ? tokenizedMsg.subList(1, tokenizedMsg.size()) : Collections.emptyList();
	}
}
