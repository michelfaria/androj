package command.handler;

import command.Command;
import component.formatter.TextFormatter;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.requests.RestAction;

public abstract class AbstractCommandHandler implements CommandHandler {

    @Override
    public void handle(Command c) {
        getFacade().handle(c);
    }

    @Override
    public String getCommandId() {
        return getFacade().getCmdId();
    }

    public abstract CommandHandlingFacade getFacade();

    public void replyTo(Command c, String text) {
        getFacade().getReplier().replyTo(c, text);
    }

    public TextFormatter formatter() {
        return getFacade().getTextFormatter();
    }

    public RestAction<Void> sendTyping(TextChannel ch) {
        return getFacade().sendTyping(ch);
    }
}
