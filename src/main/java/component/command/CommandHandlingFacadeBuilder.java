package component.command;

import command.handler.CommandHandlingFacade;
import command.strategy.Replier;
import command.strategy.SimpleValidationFailureHandler;
import command.strategy.ValidatedCommandHandler;
import command.strategy.ValidationFailureHandler;
import command.validation.Validator;
import component.formatter.TextFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

@Component
@Scope("prototype")
public class CommandHandlingFacadeBuilder {

	private TextFormatter textFormatter;
	private String cmdId;
	private ValidationFailureHandler validationFailureHandler;
	private List<Validator> validators = Collections.emptyList();
	private Replier replier;
	private Supplier<String> syntaxSupplier;
	private ValidatedCommandHandler validatedCommandHandler;

	@Autowired
	public CommandHandlingFacadeBuilder(TextFormatter textFormatter) {
		this.textFormatter = textFormatter;
	}

	public CommandHandlingFacade build() {
		Objects.requireNonNull(textFormatter);
		Objects.requireNonNull(cmdId);

		if (replier == null) {
			replier = Replier.getDefault();
		}
		if (syntaxSupplier == null) {
			syntaxSupplier = () -> "No syntax provided for this command.";
		}
		if (validationFailureHandler == null) {
			validationFailureHandler = new SimpleValidationFailureHandler(textFormatter, replier, syntaxSupplier);
		}
		if (validators == null) {
			validators = Collections.emptyList();
		}
		Objects.requireNonNull(validatedCommandHandler);

		return new CommandHandlingFacade(textFormatter, cmdId, validationFailureHandler, validators, replier,
				syntaxSupplier, validatedCommandHandler);
	}

	public TextFormatter getTextFormatter() {
		return textFormatter;
	}

	public CommandHandlingFacadeBuilder setTextFormatter(TextFormatter textFormatter) {
		this.textFormatter = textFormatter;
		return this;
	}

	public String getCmdId() {
		return cmdId;
	}

	public CommandHandlingFacadeBuilder setCmdId(String cmdId) {
		this.cmdId = cmdId;
		return this;
	}

	public ValidationFailureHandler getValidationFailureHandler() {
		return validationFailureHandler;
	}

	public CommandHandlingFacadeBuilder setValidationFailureHandler(ValidationFailureHandler validationFailureHandler) {
		this.validationFailureHandler = validationFailureHandler;
		return this;
	}

	public List<Validator> getValidators() {
		return validators;
	}

	public CommandHandlingFacadeBuilder setValidators(List<Validator> validators) {
		this.validators = validators;
		return this;
	}

	public Replier getReplier() {
		return replier;
	}

	public CommandHandlingFacadeBuilder setReplier(Replier replier) {
		this.replier = replier;
		return this;
	}

	public Supplier<String> getSyntaxSupplier() {
		return syntaxSupplier;
	}

	public CommandHandlingFacadeBuilder setSyntaxSupplier(Supplier<String> syntaxSupplier) {
		this.syntaxSupplier = syntaxSupplier;
		return this;
	}

	public ValidatedCommandHandler getValidatedCommandHandler() {
		return validatedCommandHandler;
	}

	public CommandHandlingFacadeBuilder setValidatedCommandHandler(ValidatedCommandHandler validatedCommandHandler) {
		this.validatedCommandHandler = validatedCommandHandler;
		return this;
	}
}
