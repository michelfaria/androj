package component.modules.bomb;

import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;

import java.util.function.Consumer;

interface BombFactory {
    Bomb make(User victim, TextChannel channel, Consumer<Bomb> onExplode,
              Consumer<Bomb> onDefuse);
}
