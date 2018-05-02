package component.modules.bomb;

public class VictimAlreadyHasBombException extends Exception {
    private static final long serialVersionUID = 1L;

    public VictimAlreadyHasBombException() {
    }

    public VictimAlreadyHasBombException(String message) {
        super(message);
    }

    public VictimAlreadyHasBombException(String message, Throwable cause) {
        super(message, cause);
    }

    public VictimAlreadyHasBombException(Throwable cause) {
        super(cause);
    }

    public VictimAlreadyHasBombException(String message, Throwable cause, boolean enableSuppression,
                                         boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
