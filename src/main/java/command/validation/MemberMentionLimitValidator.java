package command.validation;

import command.Command;

import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

public class MemberMentionLimitValidator implements Validator {
    private final Supplier<String> failMessage;
    private final int mentionLimit;

    public MemberMentionLimitValidator(int mentionLimit) {
        this(() -> "You can mention up to " + mentionLimit + " " + (mentionLimit == 1 ? "user" : "users") + ".", mentionLimit);
    }

    public MemberMentionLimitValidator(Supplier<String> failMessage, int mentionLimit) {
        this.failMessage = failMessage;
        this.mentionLimit = mentionLimit;
    }

    @Override
    public List<String> validate(Command c) {
        if (c.getEvent().getMessage().getMentionedMembers().size() > mentionLimit) {
            return Collections.singletonList(failMessage.get());
        }
        return Collections.emptyList();
    }
}
