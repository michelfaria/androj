package command.validation;

public class NoArgsValidator extends ArgLengthValidator {
    public NoArgsValidator() {
        super(() -> "This command takes no arguments.", 0);
    }
}
