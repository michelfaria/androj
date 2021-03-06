package command.handler;

import command.Command;
import command.strategy.Replier;
import command.strategy.ValidatedCommandHandler;
import command.strategy.ValidationFailureHandler;
import command.validation.Validator;
import component.formatter.TextFormatter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.exceptions.InsufficientPermissionException;
import net.dv8tion.jda.core.requests.RestAction;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

@AllArgsConstructor
@Getter
public class CommandHandlingFacade {

	private TextFormatter textFormatter;
	private String cmdId;
	private List<String> aliases;
	private ValidationFailureHandler validationFailureHandler;
	private List<Validator> validators;
	private Replier replier;
	private Supplier<String> syntaxSupplier;
	private ValidatedCommandHandler validatedCommandHandler;

	public void handle(Command c) {
		if (commandMatchesIdOrAlias(c)) {
			final List<String> validationErrors = applyValidations(c);
			if (validationErrors.size() > 0) {
				validationFailureHandler.accept(c, validationErrors);
			} else {
				try {
					validatedCommandHandler.accept(c);
				} catch (InsufficientPermissionException ex) {
					replier.replyTo(c, "I don't have permission to do that :( I need permission to: "
							+ ex.getPermission().getName() + ".");
				} catch (Exception ex) {
					replier.replyTo(c.getEvent().getMessage(),
							"An internal error occurred. Please report to the bot owner.");
					ex.printStackTrace();
				}
			}
		}
	}

	private boolean commandMatchesIdOrAlias(Command c) {
		return c.getId().equalsIgnoreCase(cmdId)
				|| aliases.stream().anyMatch(alias -> c.getId().equalsIgnoreCase(alias));
	}

	protected List<String> applyValidations(Command c) {
		Objects.requireNonNull(validators);
		return validators.stream().map(v -> v.validate(c)).reduce(new ArrayList<>(), (a, b) -> {
			a.addAll(b);
			return a;
		});
	}

	protected RestAction<Void> sendTyping(TextChannel ch) {
		return ch.sendTyping();
	}

}
