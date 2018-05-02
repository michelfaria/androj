package component;

import lombok.Getter;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class Client {
    private static final Logger L = LoggerFactory.getLogger(Client.class);

    private final BotConfig cfg;
    private final List<ListenerAdapter> eventListeners;
    private final Environment env;

    @Getter
    private JDA jda;

    @Autowired
    public Client(BotConfig cfg, List<ListenerAdapter> eventListeners, Environment env) {
        this.cfg = cfg;
        this.eventListeners = eventListeners;
        this.env = env;
    }

    @PostConstruct
    public void init() {
        promptIfNoToken();
        try {
            this.jda = new JDABuilder(AccountType.BOT)
                    .setToken(cfg.getToken())
                    .addEventListener(eventListeners.toArray())
                    .buildBlocking();
        } catch (Exception ex) {
            L.error("Could not log in replyTo Discord! Error: " + ex.getMessage());
        }
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
