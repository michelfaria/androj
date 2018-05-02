package command.strategy;

import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.TextChannel;

import java.util.function.Consumer;

public class SplitMessageSender implements MessageSender {
    @Override
    public void send(TextChannel channel, String text, Consumer<Message> success, Consumer<Throwable> failure) {
        new MessageBuilder()
                .append(text)
                .buildAll(MessageBuilder.SplitPolicy.ANYWHERE)
                .forEach(m_ -> channel.sendMessage(m_)
                        .queue());
    }
}
