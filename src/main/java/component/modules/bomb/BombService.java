package component.modules.bomb;

import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;

import java.util.Optional;
import java.util.function.Consumer;

public interface BombService {
	Optional<Bomb> bombFor(User victim);

	Bomb putBombOn(User victim, TextChannel channel, Consumer<Bomb> onExplode, Consumer<Bomb> onDefuse)
			throws VictimAlreadyHasBombException;

	boolean hasBomb(User victim);
}
