package component.modules.magik;

import command.Command;
import command.handler.AbstractCommandHandler;
import command.handler.CommandHandlingFacade;
import command.handler.RegisteredCommandHandler;
import component.command.CommandHandlingFacadeBuilder;
import component.service.LatestImageService;
import net.dv8tion.jda.core.entities.Icon;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MagikCommandHandler extends AbstractCommandHandler implements RegisteredCommandHandler {

    private CommandHandlingFacade facade;
    private LatestImageService latestImageService;

    @Autowired
    public MagikCommandHandler(CommandHandlingFacadeBuilder builder, LatestImageService latestImageService) {
        this.facade = builder.setCmdId("magik")
                .setValidatedCommandHandler(this::handle_)
                .build();
        this.latestImageService = latestImageService;
    }

    private void handle_(Command c) {
        Icon icon = latestImageService.get(c.getEvent().getChannel());
        if (icon == null) {
            replyTo(c, "none");
        } else {
            replyTo(c, icon.getEncoding());
        }
    }

    @NotNull
    @Override
    public CommandHandlingFacade getFacade() {
        return facade;
    }
}
