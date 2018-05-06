package command.validation;

import command.Command;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

public class MaxArgsValidator implements Validator {
    private final Supplier<String> failMessage;
    private final int length;

    public MaxArgsValidator(int length) {
        this(() -> "At most " + length + " " + (length == 1 ? "argument is" : "arguments are") + " needed.", length);
    }

    public MaxArgsValidator(Supplier<String> failMessage, int length) {
        this.failMessage = failMessage;
        this.length = length;
    }

    @Override
    public List<String> validate(Command c) {
        Objects.requireNonNull(c);
        return c.getArgs().size() > length
                ? Collections.singletonList(failMessage.get())
                : Collections.emptyList();
    }
}