package component.modules.core;

import command.Command;
import command.handler.AbstractCommandHandler;
import command.handler.CommandHandlingFacade;
import command.handler.RegisteredCommandHandler;
import component.command.CommandHandlingFacadeBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class HelpCommandHandler extends AbstractCommandHandler implements RegisteredCommandHandler {

    private CommandHandlingFacade facade;
    private List<RegisteredCommandHandler> registeredCommandHandlers;

    @Autowired
    public HelpCommandHandler(CommandHandlingFacadeBuilder builder,
                              List<RegisteredCommandHandler> registeredCommandHandlers) {
        this.facade = builder.setCmdId("help")
                .setValidatedCommandHandler(this::handle_)
                .build();
        this.registeredCommandHandlers = registeredCommandHandlers;
    }

    public void handle_(Command c) {
        StringBuilder sb = new StringBuilder();
        sb.append(facade.getTextFormatter().bold("Help for Goatbot:"));
        sb.append("\n");
        if (registeredCommandHandlers == null || registeredCommandHandlers.isEmpty()) {
            sb.append("No commands found.");
        } else {
            registeredCommandHandlers.forEach(h -> {
                sb.append(facade.getTextFormatter().bold(h.getCommandId()));
                sb.append(": ");
                sb.append(h.help());
                sb.append("\n");
            });
        }
        replyTo(c, sb.toString());
    }

    @Override
    public CommandHandlingFacade getFacade() {
        return facade;
    }
}
