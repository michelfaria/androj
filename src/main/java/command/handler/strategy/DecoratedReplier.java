package command.handler.strategy;

import component.formatter.TextFormatter;
import lombok.AllArgsConstructor;
import net.dv8tion.jda.core.entities.Message;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

@AllArgsConstructor
public class DecoratedReplier implements Replier {

    private TextFormatter formatter;
    private String decoration;
    private Replier replier;
    private MessageSender messageSender;

    public DecoratedReplier(TextFormatter formatter, String decoration) {
        this(formatter, decoration, Replier.getDefault());
    }

    public DecoratedReplier(TextFormatter formatter, String decoration, Replier replier) {
        this(formatter, decoration, replier, MessageSender.getDefault());
    }

    @Override
    public void replyTo(Message m, String text, @Nullable Consumer<Message> success, @Nullable Consumer<Throwable> failure) {
        messageSender.send(m.getTextChannel(), buildReply(m, text), success, failure);
    }

    @Override
    public String buildReply(Message m, String text) {
        return decoration + formatter.bold(" | ") + replier.buildReply(m, text);
    }
}
