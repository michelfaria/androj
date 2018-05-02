package command.strategy;

import net.dv8tion.jda.core.entities.Message;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class SimpleReplier implements Replier {

    private MessageSender messageSender;

    public SimpleReplier() {
        this.messageSender = MessageSender.getDefault();
    }

    public SimpleReplier(MessageSender messageSender) {
        this.messageSender = messageSender;
    }

    @Override
    public void replyTo(Message m, String text, @Nullable Consumer<Message> success, @Nullable Consumer<Throwable> failure) {
        messageSender.send(m.getTextChannel(), buildReply(m, text), success, failure);
    }

    @Override
    public String buildReply(Message m, String text) {
        return m.getAuthor().getAsMention() + ": " + text;
    }
}
