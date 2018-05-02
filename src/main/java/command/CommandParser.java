package command;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.Optional;

public interface CommandParser {
    Optional<Command> parse(MessageReceivedEvent string);
}
