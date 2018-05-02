package component;

import component.listeners.BotListener;
import lombok.Getter;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class Client {
    private static final Logger LOGGER = LoggerFactory.getLogger(Client.class);

    private final BotConfig cfg;
    private final List<BotListener> eventListeners;
    private final Environment env;

    @Getter
    private JDA jda;

    @Autowired
    public Client(BotConfig cfg, List<BotListener> eventListeners, Environment env) {
        this.cfg = cfg;
        this.eventListeners = eventListeners;
        this.env = env;
    }

    @PostConstruct
    public void init() {
        orderEventListeners();
        LOGGER.info("Event listeners: " + eventListeners.stream().map(Object::toString).collect(Collectors.joining("\n")));
        promptIfNoToken();
        try {
            this.jda = new JDABuilder(AccountType.BOT)
                    .setToken(cfg.getToken())
                    .addEventListener(eventListeners.toArray())
                    .buildBlocking();
        } catch (Exception ex) {
            LOGGER.error("Could not log in replyTo Discord! Error: " + ex.getMessage());
        }
    }

    private void orderEventListeners() {
        eventListeners.sort((a, b) -> -Integer.compare(a.priority(), b.priority()));
    }

    public void promptIfNoToken() {
        if (env.getProperty("bot.token") == null) {
            System.out.println("You are seeing this prompt because you did not set "
                    + "a bot.token in your properties file.\nEnter bot token: ");
            cfg.setToken(String.valueOf(
                    System.console().readPassword()));
        }
    }
}
