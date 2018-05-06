package command;

import lombok.Data;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.List;

@Data
public class Command {
	private String id;
	private List<String> args;
	private MessageReceivedEvent event;

	public Command(String id, List<String> args, MessageReceivedEvent event) {
		this.id = id;
		this.args = args;
		this.event = event;
	}
}
