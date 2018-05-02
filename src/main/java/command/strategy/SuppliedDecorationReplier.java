package command.strategy;

import component.formatter.TextFormatter;
import net.dv8tion.jda.core.entities.Message;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class SuppliedDecorationReplier implements Replier {

    private TextFormatter formatter;
    private Supplier<String> decorationSupplier;
    private Replier replier;
    private MessageSender messageSender;

    public SuppliedDecorationReplier(Supplier<String> decorationSupplier) {
        this(TextFormatter.getDefault(), decorationSupplier);
    }

    public SuppliedDecorationReplier(TextFormatter formatter, Supplier<String> decorationSupplier) {
        this(formatter, decorationSupplier, Replier.getDefault());
    }

    public SuppliedDecorationReplier(TextFormatter formatter, Supplier<String> decorationSupplier, Replier replier) {
        this(formatter, decorationSupplier, replier, MessageSender.getDefault());
    }

    public SuppliedDecorationReplier(TextFormatter formatter, Supplier<String> decorationSupplier, Replier replier, MessageSender messageSender) {
        Objects.requireNonNull(formatter);
        Objects.requireNonNull(decorationSupplier);
        Objects.requireNonNull(replier);
        Objects.requireNonNull(messageSender);
        this.formatter = formatter;
        this.decorationSupplier = decorationSupplier;
        this.replier = replier;
        this.messageSender = messageSender;
    }

    @Override
    public void replyTo(Message m, String text, @Nullable Consumer<Message> success, @Nullable Consumer<Throwable> failure) {
        messageSender.send(m.getTextChannel(), buildReply(m, text), success, failure);
    }

    @Override
    public String buildReply(Message m, String text) {
        return decorationSupplier.get() + formatter.bold(" | ") + replier.buildReply(m, text);
    }
}
