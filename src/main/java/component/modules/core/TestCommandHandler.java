package component.modules.core;

import command.Command;
import command.handler.AbstractCommandHandler;
import command.handler.CommandHandlingFacade;
import command.handler.RegisteredCommandHandler;
import component.command.CommandHandlingFacadeBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TestCommandHandler extends AbstractCommandHandler implements RegisteredCommandHandler {

    private CommandHandlingFacade facade;

    @Autowired
    public TestCommandHandler(CommandHandlingFacadeBuilder builder) {
        this.facade = builder.setCmdId("test")
                .setValidatedCommandHandler(this::handle_)
                .build();
    }

    public void handle_(Command c) {
        replyTo(c, "Hello world!");
    }

    @Override
    public CommandHandlingFacade getFacade() {
        return this.facade;
    }
}
