package component.modules.udict;

import command.Command;
import command.handler.AbstractCommandHandler;
import command.handler.CommandHandlingFacade;
import command.handler.RegisteredCommandHandler;
import command.strategy.DecoratedReplier;
import command.validation.MinArgsValidator;
import component.command.CommandHandlingFacadeBuilder;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Component
@ConditionalOnBean(UdictConfig.class)
public class UrbanDictCommandHandler extends AbstractCommandHandler implements RegisteredCommandHandler {

	private CommandHandlingFacade facade;
	private UrbanDict urbanDict;

	@Autowired
	public UrbanDictCommandHandler(CommandHandlingFacadeBuilder builder, UrbanDict urbanDict) {
		this.facade = builder.setCmdId("udict").setValidatedCommandHandler(this::handle_)
				.setValidators(Arrays.asList(new MinArgsValidator(1))).setReplier(new DecoratedReplier(":book:"))
				.setSyntaxSupplier(() -> "<term>").build();
		this.urbanDict = urbanDict;
	}

	@Override
	public String help() {
		return "Define something!";
	}

	@NotNull
	@Override
	public CommandHandlingFacade getFacade() {
		return facade;
	}

	public void handle_(Command c) {
		sendTyping(c.getEvent().getTextChannel());
		final String term = String.join(" ", c.getArgs());
		final List<UrbanDictEntry> entries = urbanDict.define(term).getList();

		if (entries == null || entries.isEmpty()) {
			replyTo(c, "No entries found for \"" + term + "\".");
		} else {
			final UrbanDictEntry first = entries.get(0);
			Objects.requireNonNull(first.getPermalink());
			replyTo(c, entryToString(term, first));
		}
	}

	private String entryToString(String term, UrbanDictEntry entry) {
		return facade.getTextFormatter().bold("Definition for " + term + ": ") + "\n\n" + entry.getDefinition() + "\n\n"
				+ formatter().code(entry.getPermalink());
	}
}
