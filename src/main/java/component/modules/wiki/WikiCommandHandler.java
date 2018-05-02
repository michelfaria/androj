package component.modules.wiki;


import command.Command;
import command.handler.AbstractCommandHandler;
import command.handler.CommandHandlingFacade;
import command.handler.RegisteredCommandHandler;
import command.handler.strategy.SuppliedDecorationReplier;
import command.handler.validation.MinArgsValidator;
import component.command.CommandHandlingFacadeBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Component
@ConditionalOnBean(WikiConfig.class)
public class WikiCommandHandler extends AbstractCommandHandler implements RegisteredCommandHandler {

    private static final List<String> decorators = Arrays.asList(":earth_africa:", ":earth_americas:", ":earth_asia:");

    private Wikipedia wikipedia;
    private Random random;
    private CommandHandlingFacade facade;

    @Autowired
    public WikiCommandHandler(CommandHandlingFacadeBuilder builder, Wikipedia wikipedia) {
        this(builder, wikipedia, new Random());
    }

    public WikiCommandHandler(CommandHandlingFacadeBuilder builder, Wikipedia wikipedia, Random random) {
        this.wikipedia = wikipedia;
        this.random = random;
        this.facade = builder
                .setCmdId("wiki")
                .setValidatedCommandHandler(this::handle_)
                .setReplier(new SuppliedDecorationReplier(
                        builder.getTextFormatter(), () -> decorators.get(random.nextInt(decorators.size()))))
                .setSyntaxSupplier(() -> "<term>")
                .setValidators(Arrays.asList(new MinArgsValidator(1)))
                .build();
    }

    private void handle_(Command c) {
        String term = String.join(" ", c.getArgs());
        try {
            WikipediaResponse wikipediaResponse = wikipedia.lookup(term);
            replyTo(c,
                    wikipediaResponse.getSummary() + "\n\nSee more at: "
                            + wikipediaResponse.getUrl());
        } catch (FileNotFoundException e) {
            replyTo(c, "Could not find a Wikipedia page on that.");
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public CommandHandlingFacade getFacade() {
        return facade;
    }
}
