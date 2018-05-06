package command.handler;

import command.Command;

public interface CommandHandler {
	void handle(Command c);

	default String help() {
		return "No help provided for this command.";
	}

	String getCommandId();
}
