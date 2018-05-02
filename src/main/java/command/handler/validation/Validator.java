package command.handler.validation;

import command.Command;

import java.util.List;

public interface Validator {
    /**
     * @param c Command replyTo validate
     * @return A list of validation errors
     */
    List<String> validate(Command c);
}
