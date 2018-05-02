package component.modules.magik;

import command.Command;
import command.handler.AbstractCommandHandler;
import command.handler.CommandHandlingFacade;
import command.handler.RegisteredCommandHandler;
import component.command.CommandHandlingFacadeBuilder;
import component.io.TempFiles;
import component.service.LatestImageService;
import net.dv8tion.jda.core.entities.Icon;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

@Component
public class MagikCommandHandler extends AbstractCommandHandler implements RegisteredCommandHandler {

    private CommandHandlingFacade facade;
    private TempFiles tempFiles;
    private LatestImageService latestImageService;

    @Autowired
    public MagikCommandHandler(CommandHandlingFacadeBuilder builder, TempFiles tempFiles, LatestImageService latestImageService) {
        this.facade = builder.setCmdId("magik")
                .setValidatedCommandHandler(this::handle_)
                .build();
        this.tempFiles = tempFiles;
        this.latestImageService = latestImageService;
    }

    private void handle_(Command c) {
        Icon icon = latestImageService.get(c.getEvent().getChannel());
        if (icon == null) {
            replyTo(c, "none");
            return;
        }
        File tempFile;
        try {
            // remove the base64 image thing
            String raw = icon.getEncoding()
                    .split(",")[1];
            tempFile = tempFiles.createDecodeBase64(raw);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        replyTo(c, "c");
    }

    @NotNull
    @Override
    public CommandHandlingFacade getFacade() {
        return facade;
    }
}
