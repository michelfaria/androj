package command.handler.strategy;

import command.Command;
import net.dv8tion.jda.core.entities.Message;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public interface Replier {

    static Replier getDefault() {
        return new SimpleReplier();
    }

    String buildReply(Message m, String text);

    default void replyTo(Command c, String text) {
        replyTo(c, text, null, null);
    }

    default void replyTo(Command c, String text, @Nullable Consumer<Message> success, @Nullable Consumer<Throwable> failure) {
        replyTo(c.getEvent().getMessage(), text, success, failure);
    }

    void replyTo(Message m, String text, @Nullable Consumer<Message> success, @Nullable Consumer<Throwable> failure);

    default void replyTo(Message m, String text) {
        replyTo(m, text, null, null);
    }
}
