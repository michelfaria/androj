package component.modules.moderation;

import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import org.jetbrains.annotations.Nullable;

public interface MessageCleaner {
    default void clean(TextChannel ch, int amount) {
        clean(ch, amount, null);
    }

    void clean(TextChannel ch, int amount, @Nullable User author);
}
