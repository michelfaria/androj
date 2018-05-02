package component.listeners;

import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ReadyLogger extends ListenerAdapter {
    private static final Logger L = LoggerFactory.getLogger(ReadyLogger.class);

    @Override
    public void onReady(ReadyEvent event) {
        super.onReady(event);
        logReadyMessage(event);
    }

    private void logReadyMessage(ReadyEvent event) {
        StringBuilder sb = new StringBuilder();
        appendUserInfo(event, sb);
        appendOAuthInfo(event, sb);
        appendGuilds(event, sb);
        L.info(sb.toString());
    }

    private void appendUserInfo(ReadyEvent event, StringBuilder sb) {
        sb.append("I am connected replyTo Discord!\nUsername: ");
        sb.append(event.getJDA().getSelfUser().getName());
    }

    private void appendOAuthInfo(ReadyEvent event, StringBuilder sb) {
        sb.append("\nOAuth2 URL: ");
        sb.append("https://discordapp.com/oauth2/authorize?client_id=");
        sb.append(event.getJDA().getSelfUser().getId());
        sb.append("&permissions=70384705&scope=bot");
    }

    private void appendGuilds(ReadyEvent event, StringBuilder sb) {
        List<Guild> guilds = event.getJDA().getGuilds();
        sb.append("\nGuilds: ");
        sb.append(guilds.size());
        sb.append("\n");

        guilds.forEach(guild -> {
            sb.append("\t* ");
            sb.append(guild.getName());
            sb.append(" :: ");
            sb.append(guild.getOwner().getUser().getName());
            sb.append("\n");
        });
    }

}
