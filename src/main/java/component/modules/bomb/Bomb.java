package component.modules.bomb;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Consumer;

@Getter
@ToString
@EqualsAndHashCode(of = { "victim", "channel" })
public class Bomb {
	private final User victim;
	private final TextChannel channel;
	private final Integer detonationTime;
	private final Consumer<Bomb> onExplode;
	private final Consumer<Bomb> onDefuse;
	private final List<WireColors> wireColors;
	private final WireColors correctWire;
	private final Timer timer = new Timer();

	Bomb(User victim, TextChannel channel, Integer detonationTime, Consumer<Bomb> onExplode, Consumer<Bomb> onDefuse,
			List<WireColors> wireColors, WireColors correctWire) {
		this.victim = victim;
		this.channel = channel;
		this.detonationTime = detonationTime;
		this.onExplode = onExplode;
		this.onDefuse = onDefuse;
		this.wireColors = wireColors;
		this.correctWire = correctWire;
	}

	public void cutWire(WireColors color) {
		this.timer.cancel();
		if (color.equals(correctWire)) {
			defuse();
		} else {
			explode();
		}
	}

	public void defuse() {
		this.onDefuse.accept(this);
	}

	public void explode() {
		this.onExplode.accept(this);
	}

	public void startTimer() {
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				explode();
			}
		}, detonationTime);
	}
}
