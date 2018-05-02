package component.listeners;

import command.CommandParser;
import command.handler.RegisteredCommandHandler;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class CommandListener extends ListenerAdapter implements BotListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommandListener.class);

    private final CommandParser commandParser;
    private final List<RegisteredCommandHandler> registeredCommandHandlers;

    @Autowired
    public CommandListener(CommandParser commandParser, List<RegisteredCommandHandler> registeredCommandHandlers) {
        this.commandParser = commandParser;
        this.registeredCommandHandlers = registeredCommandHandlers;
    }

    @PostConstruct
    public void init() {
        StringBuilder sb = new StringBuilder();
        sb.append("Registered command handlers:\n");
        registeredCommandHandlers.forEach(r -> {
            sb.append("\t-> ");
            sb.append(r.getCommandId());
            sb.append("\n");
        });
        LOGGER.info(sb.toString());
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        commandParser.parse(event)
                .ifPresent(c -> registeredCommandHandlers.forEach(h -> h.handle(c)));
    }
}
