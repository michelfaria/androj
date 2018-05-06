package component.modules.bomb;

import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;

@Component
@ConditionalOnBean(BombConfig.class)
public class RandomBombFactory implements BombFactory {
	private Random random;
	private BombConfig bombConfig;

	@Autowired
	public RandomBombFactory(BombConfig bombConfig) {
		this(new Random(), bombConfig);
	}

	public RandomBombFactory(Random random, BombConfig bombConfig) {
		this.random = random;
		this.bombConfig = bombConfig;
	}

	/**
	 * Returns a bomb without timer.
	 */
	@Override
	public Bomb make(User victim, TextChannel channel, Consumer<Bomb> onExplode, Consumer<Bomb> onDefuse) {
		List<WireColors> wireColors = randomWireColors(randomColorAmount());
		return new Bomb(victim, channel, randomDetonationTime(), onExplode, onDefuse, wireColors,
				pickCorrectWire(wireColors));
	}

	private List<WireColors> randomWireColors(int amountColors) {
		List<WireColors> wireColors = new ArrayList<>();
		List<WireColors> aux = new ArrayList<>(Arrays.asList(WireColors.values()));
		while (wireColors.size() < amountColors) {
			if (aux.isEmpty()) {
				break;
			}
			int index = random.nextInt(aux.size());
			WireColors chosen = aux.get(index);
			wireColors.add(chosen);
			aux.remove(chosen);
		}
		return wireColors;
	}

	private int randomColorAmount() {
		return random.nextInt(bombConfig.getMaxWires() - bombConfig.getMinWires()) + bombConfig.getMinWires();
	}

	private int randomDetonationTime() {
		return random.nextInt(bombConfig.getMaxTimeMs() - bombConfig.getMinWires()) + bombConfig.getMinTimeMs();
	}

	private WireColors pickCorrectWire(List<WireColors> wireColors) {
		return wireColors.get(random.nextInt(wireColors.size()));
	}
}
