package component.modules.moderation;

import command.Command;
import command.handler.AbstractCommandHandler;
import command.handler.CommandHandlingFacade;
import command.handler.RegisteredCommandHandler;
import command.strategy.ValidationFailureHandler;
import command.validation.IntArgValidator;
import command.validation.MaxArgsValidator;
import command.validation.MemberMentionLimitValidator;
import command.validation.Validator;
import component.command.CommandHandlingFacadeBuilder;
import component.command.validation.permission.GuildPermissionValidator;
import component.command.validation.permission.GuildPermissionValidatorBuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
public class PurgeCommandHandler extends AbstractCommandHandler implements RegisteredCommandHandler {

    private CommandHandlingFacade facade;
    private MessageCleaner messageCleaner;

    @Autowired
    public PurgeCommandHandler(CommandHandlingFacadeBuilder builder, MessageCleaner messageCleaner,
                               GuildPermissionValidatorBuilder permissionValidatorBuilder) {
        GuildPermissionValidator permissionValidator = permissionValidatorBuilder
                .setRequiredPermissions(Collections.singletonList(Permission.MESSAGE_MANAGE))
                .build();
        this.facade = builder.setCmdId("purge")
                .setValidators(Arrays.asList(
                        permissionValidator,
                        new MaxArgsValidator(2),
                        new MemberMentionLimitValidator(1),
                        new CustomValidator()))
                .setSyntaxSupplier(() -> "[user] [amount]")
                .setValidatedCommandHandler(this::handle_)
                .build();
        this.messageCleaner = messageCleaner;
    }

    private void handle_(Command c) {
        int amountArgsPos = getAmountArgsPos(c);
        int amount = 10;
        if (c.getArgs().size() > amountArgsPos) {
            amount = Integer.parseInt(c.getArgs().get(amountArgsPos));
        }
        List<Member> mentionedMembers = c.getEvent().getMessage().getMentionedMembers();
        User user = null;
        if (amountArgsPos == 1) {
            assert mentionedMembers.size() > 0;
            user = mentionedMembers.get(0).getUser();
        }
        messageCleaner.clean(c.getEvent().getTextChannel(), amount, user);

    }

    @NotNull
    @Override
    public CommandHandlingFacade getFacade() {
        return facade;
    }

    @Override
    public String help() {
        return "Purge a user's messages.";
    }

    private int getAmountArgsPos(Command c) {
        List<Member> mentionedMembers = c.getEvent().getMessage().getMentionedMembers();
        if (mentionedMembers.size() > 0) {
            return 1;
        }
        return 0;
    }

    private class CustomValidator implements Validator {
        @Override
        public List<String> validate(Command c) {
            return new IntArgValidator(getAmountArgsPos(c))
                    .validate(c);
        }
    }
}