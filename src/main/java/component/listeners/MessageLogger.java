package component.listeners;

import component.MessageLoggerConfig;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnBean(MessageLoggerConfig.class)
public class MessageLogger extends ListenerAdapter implements BotListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageLogger.class);

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        String s = "[" + event.getGuild().getName() + "] [#" + event.getChannel().getName() + "] <"
                + event.getAuthor().getName() + "> (" + event.getAuthor().getId() + ") => "
                + event.getMessage().getContentRaw();
        LOGGER.info(s);
    }

}