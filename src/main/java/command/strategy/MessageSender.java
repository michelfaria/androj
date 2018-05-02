package command.strategy;

import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.TextChannel;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public interface MessageSender {
    static MessageSender getDefault() {
        return new SplitMessageSender();
    }

    default void send(TextChannel c, String text) {
        send(c, text, null, null);
    }

    void send(TextChannel channel, String text, @Nullable Consumer<Message> success, @Nullable Consumer<Throwable> failure);
}
