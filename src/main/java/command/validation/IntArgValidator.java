package command.validation;

import command.Command;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

public class IntArgValidator implements Validator {

    public enum Enforce {
        POSITIVE,
        NEGATIVE,
        NONE
    }

    private Supplier<String> failMessage;
    private Enforce enforce;
    private int position;

    public IntArgValidator(int position, Enforce enforce, Supplier<String> failMessage) {
        if (position < 0) {
            throw new IllegalArgumentException("position must be greater or equal to 0");
        }
        this.enforce = enforce;
        this.failMessage = failMessage;
        this.position = position;
    }

    public IntArgValidator(int position, Enforce enforce) {
        this(position, enforce, () -> "Argument at position " + position + " must be an integer.");
    }

    public IntArgValidator(int position) {
        this(position, Enforce.NONE);
    }

    @Override
    public List<String> validate(Command c) {
        if (c.getArgs().size() > position) {
            try {
                int x = Integer.parseInt(c.getArgs().get(position));
                switch (enforce) {
                    case POSITIVE:
                        if (x < 0) {
                            return Arrays.asList("The number specified must be positive.");
                        }
                        break;
                    case NEGATIVE:
                        if (x > 0) {
                            return Arrays.asList("The number specified must be negative.");
                        }
                        break;
                    case NONE:
                        break;
                }
            } catch (NumberFormatException ex) {
                return Collections.singletonList(failMessage.get());
            }
        }
        return Collections.emptyList();
    }
}
