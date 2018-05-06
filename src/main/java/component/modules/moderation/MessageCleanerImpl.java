package component.modules.moderation;

import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.requests.restaction.pagination.MessagePaginationAction;
import net.dv8tion.jda.core.requests.restaction.pagination.PaginationAction;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Component;

@Component
public class MessageCleanerImpl implements MessageCleaner {

    @Override
    public void clean(TextChannel ch, int amount, @Nullable User author) {
        if (amount < 0) {
            throw new IllegalArgumentException("amount must be positive");
        }
        PaginationAction<Message, MessagePaginationAction>.PaginationIterator it = ch.getIterableHistory().iterator();
        for (int i = 0; i < amount; i++) {
            if (!it.hasNext()) {
                break;
            }
            Message m = it.next();
            if (author == null || m.getAuthor().equals(author)) {
                m.delete().queue();
            }
        }
    }
}
