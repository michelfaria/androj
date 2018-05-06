package component.modules.moderation;

import command.Command;
import command.handler.AbstractCommandHandler;
import command.handler.CommandHandlingFacade;
import command.handler.RegisteredCommandHandler;
import command.validation.IntArgValidator;
import command.validation.MaxArgsValidator;
import component.command.validation.permission.GuildPermissionValidator;
import component.command.validation.permission.GuildPermissionValidatorBuilder;
import component.command.CommandHandlingFacadeBuilder;
import net.dv8tion.jda.core.Permission;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;

@Component
public class SelfCleaningCommandHandler extends AbstractCommandHandler implements RegisteredCommandHandler {

    private CommandHandlingFacade facade;
    private MessageCleaner messageCleaner;

    @Autowired
    public SelfCleaningCommandHandler(CommandHandlingFacadeBuilder builder, MessageCleaner messageCleaner,
                                      GuildPermissionValidatorBuilder permissionValidatorBuilder) {
        GuildPermissionValidator permissionValidator = permissionValidatorBuilder
                .setRequiredPermissions(Collections.singletonList(Permission.MESSAGE_MANAGE))
                .build();
        this.facade = builder.setCmdId("clean")
                .setValidators(Arrays.asList(
                        permissionValidator,
                        new MaxArgsValidator(1),
                        new IntArgValidator(0, IntArgValidator.Enforce.POSITIVE, () -> "Please specify the amount of my messages that you want to clean!")))
                .setSyntaxSupplier(() -> "[amount]")
                .setValidatedCommandHandler(this::handle_)
                .build();
        this.messageCleaner = messageCleaner;
    }

    private void handle_(Command c) {
        int amount = 10;
        if (c.getArgs().size() > 0) {
            amount = Integer.parseInt(c.getArgs().get(0));
        }
        messageCleaner.clean(c.getEvent().getTextChannel(), amount, c.getEvent().getJDA().getSelfUser());
    }

    @NotNull
    @Override
    public CommandHandlingFacade getFacade() {
        return facade;
    }

    @Override
    public String help() {
        return "Cleans my messages!";
    }
}
