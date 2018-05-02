package command.strategy;

import command.Command;
import component.formatter.TextFormatter;

import java.util.List;
import java.util.function.Supplier;

public class SimpleValidationFailureHandler implements ValidationFailureHandler {
    private final TextFormatter formatter;
    private final Replier replier;
    private final Supplier<String> syntaxSupplier;

    public SimpleValidationFailureHandler(TextFormatter formatter, Replier replier, Supplier<String> syntaxSupplier) {
        this.formatter = formatter;
        this.replier = replier;
        this.syntaxSupplier = syntaxSupplier;
    }

    @Override
    public void accept(Command c, List<String> validationErrors) {
        StringBuilder sb = new StringBuilder();
        sb.append(formatter.bold("Error: Please check your syntax: "))
                .append(formatter.code(syntaxSupplier.get()))
                .append("\n");
        validationErrors.forEach(error -> {
            sb.append(formatter.bold("==>"))
                    .append(" ")
                    .append(error)
                    .append("\n");
        });
        replier.replyTo(c.getEvent().getMessage(), sb.toString());
    }
}
