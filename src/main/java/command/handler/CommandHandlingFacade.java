package command.handler;

import command.Command;
import command.handler.strategy.Replier;
import command.handler.strategy.ValidatedCommandHandler;
import command.handler.strategy.ValidationFailureHandler;
import command.handler.validation.Validator;
import component.formatter.TextFormatter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.dv8tion.jda.core.entities.TextChannel;
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
    private ValidationFailureHandler validationFailureHandler;
    private List<Validator> validators;
    private Replier replier;
    private Supplier<String> syntaxSupplier;
    private ValidatedCommandHandler validatedCommandHandler;

    public void handle(Command c) {
        if (c.getId().equalsIgnoreCase(cmdId)) {
            final List<String> validationErrors = applyValidations(c);
            if (validationErrors.size() > 0) {
                validationFailureHandler.accept(c, validationErrors);
            } else {
                try {
                    validatedCommandHandler.accept(c);
                } catch (Exception ex) {
                    replier.replyTo(c.getEvent().getMessage(),
                            "An internal error occurred. Please report to the bot owner.");
                    ex.printStackTrace();
                }
            }
        }
    }

    protected List<String> applyValidations(Command c) {
        Objects.requireNonNull(validators);
        return validators.stream()
                .map(v -> v.validate(c))
                .reduce(new ArrayList<>(), (a, b) -> {
                    a.addAll(b);
                    return a;
                });
    }

    protected RestAction<Void> sendTyping(TextChannel ch) {
        return ch.sendTyping();
    }

}
