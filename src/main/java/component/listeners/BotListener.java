package component.listeners;

public interface BotListener {
	default int priority() {
		return 0;
	}
}
