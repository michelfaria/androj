package command.handler.validation;

import command.Command;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

public class MinArgsValidator implements Validator {
    private final Supplier<String> failMessage;
    private final int length;

    public MinArgsValidator(int length) {
        this(() -> "At least " + length + " " + (length == 1 ? "argument is" : "arguments are") + " needed.", length);
    }

    public MinArgsValidator(Supplier<String> failMessage, int length) {
        this.failMessage = failMessage;
        this.length = length;
    }

    @Override
    public List<String> validate(Command c) {
        Objects.requireNonNull(c);
        return c.getArgs().size() < length
                ? Collections.singletonList(failMessage.get())
                : Collections.emptyList();
    }
}
