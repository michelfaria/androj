package command.handler.strategy;

import command.Command;

import java.util.List;

public interface ValidationFailureHandler {
    void accept(Command c, List<String> validationErrors);
}
