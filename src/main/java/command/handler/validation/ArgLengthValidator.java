package command.handler.validation;

import command.Command;
import lombok.Getter;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

@Getter
public class ArgLengthValidator implements Validator {
    private final Supplier<String> failMessage;
    private final int length;

    public ArgLengthValidator(int length) {
        this(() -> "Incorrect amount of arguments.", length);
    }

    public ArgLengthValidator(Supplier<String> failMessage, int length) {
        this.failMessage = failMessage;
        this.length = length;
    }

    @Override
    public List<String> validate(Command c) {
        Objects.requireNonNull(c);
        return c.getArgs().size() != length
                ? Collections.singletonList(failMessage.get())
                : Collections.emptyList();
    }
}
