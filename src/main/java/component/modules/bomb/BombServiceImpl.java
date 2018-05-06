package component.modules.bomb;

import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

@Service
@ConditionalOnBean(BombConfig.class)
public class BombServiceImpl implements BombService {
	private final Map<User, Bomb> userBombMap = new HashMap<>();
	private final BombFactory bombFactory;

	@Autowired
	public BombServiceImpl(BombFactory bombFactory) {
		this.bombFactory = bombFactory;
	}

	@Override
	public Optional<Bomb> bombFor(User victim) {
		return userBombMap.containsKey(victim) ? Optional.of(userBombMap.get(victim)) : Optional.empty();
	}

	@Override
	public Bomb putBombOn(User victim, TextChannel channel, Consumer<Bomb> onExplode, Consumer<Bomb> onDefuse)
			throws VictimAlreadyHasBombException {
		if (hasBomb(victim)) {
			throw new VictimAlreadyHasBombException();
		}
		final Consumer<Bomb> onExplodeWrapped = bomb -> {
			userBombMap.remove(bomb.getVictim());
			onExplode.accept(bomb);
		};
		final Consumer<Bomb> onDefuseWrapped = bomb -> {
			userBombMap.remove(bomb.getVictim());
			onDefuse.accept(bomb);
		};
		final Bomb newBomb = bombFactory.make(victim, channel, onExplodeWrapped, onDefuseWrapped);
		newBomb.startTimer();
		userBombMap.put(victim, newBomb);
		return newBomb;
	}

	@Override
	public boolean hasBomb(User victim) {
		return bombFor(victim).isPresent();
	}
}
