package command.handler;

import command.Command;
import component.formatter.TextFormatter;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.requests.RestAction;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public abstract class AbstractCommandHandler implements CommandHandler {

    @Override
    public void handle(Command c) {
        getFacade().handle(c);
    }

    @Override
    public String getCommandId() {
        return getFacade().getCmdId();
    }

    public abstract @NotNull CommandHandlingFacade getFacade();

    public void replyTo(Command c, String text) {
        getFacade().getReplier().replyTo(c, text);
    }

    public void replyTo(Command c, String text, @Nullable Consumer<Message> success, @Nullable Consumer<Throwable> failure) {
        getFacade().getReplier().replyTo(c, text, success, failure);
    }

    public TextFormatter formatter() {
        return getFacade().getTextFormatter();
    }

    public RestAction<Void> sendTyping(TextChannel ch) {
        return getFacade().sendTyping(ch);
    }
}
