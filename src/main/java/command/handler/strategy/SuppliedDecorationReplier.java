package command.handler.strategy;

import component.formatter.TextFormatter;
import lombok.AllArgsConstructor;
import net.dv8tion.jda.core.entities.Message;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;
import java.util.function.Supplier;

@AllArgsConstructor
public class SuppliedDecorationReplier implements Replier {

    private TextFormatter formatter;
    private Supplier<String> decoration;
    private Replier replier;
    private MessageSender messageSender;

    public SuppliedDecorationReplier(TextFormatter formatter, Supplier<String> decoration) {
        this(formatter, decoration, Replier.getDefault());
    }

    public SuppliedDecorationReplier(TextFormatter formatter, Supplier<String> decoration, Replier replier) {
        this(formatter, decoration, replier, MessageSender.getDefault());
    }

    @Override
    public void replyTo(Message m, String text, @Nullable Consumer<Message> success, @Nullable Consumer<Throwable> failure) {
        messageSender.send(m.getTextChannel(), buildReply(m, text), success, failure);
    }

    @Override
    public String buildReply(Message m, String text) {
        return decoration.get() + formatter.bold(" | ") + replier.buildReply(m, text);
    }
}
