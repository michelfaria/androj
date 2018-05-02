package command.handler.strategy;

import command.Command;

public interface ValidatedCommandHandler {
    void accept(Command c);
}
