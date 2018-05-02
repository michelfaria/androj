package command.strategy;

import component.formatter.TextFormatter;
import lombok.AllArgsConstructor;
import net.dv8tion.jda.core.entities.Message;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.function.Consumer;

public class DecoratedReplier implements Replier {

    private TextFormatter formatter;
    private String decoration;
    private Replier replier;
    private MessageSender messageSender;

    public DecoratedReplier(String decoration) {
        this(TextFormatter.getDefault(), decoration);
    }

    public DecoratedReplier(TextFormatter formatter, String decoration) {
        this(formatter, decoration, Replier.getDefault());
    }

    public DecoratedReplier(TextFormatter formatter, String decoration, Replier replier) {
        this(formatter, decoration, replier, MessageSender.getDefault());
    }

    public DecoratedReplier(TextFormatter formatter, String decoration, Replier replier, MessageSender messageSender) {
        Objects.requireNonNull(formatter);
        Objects.requireNonNull(decoration);
        Objects.requireNonNull(replier);
        Objects.requireNonNull(messageSender);
        this.formatter = formatter;
        this.decoration = decoration;
        this.replier = replier;
        this.messageSender = messageSender;
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
