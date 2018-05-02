package component.modules.core;

import command.Command;
import command.handler.AbstractCommandHandler;
import command.handler.CommandHandlingFacade;
import command.handler.RegisteredCommandHandler;
import command.handler.strategy.DecoratedReplier;
import component.command.CommandHandlingFacadeBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class EightBallCommandHandler extends AbstractCommandHandler implements RegisteredCommandHandler {

    private static final String[] fortunes = new String[]{
            "It is certain",
            "It is decidedly so",
            "Without a doubt",
            "Yes definitely",
            "You may rely on it",
            "As I see it, yes",
            "Most likely",
            "Outlook good",
            "Yes",
            "Signs point to yes",
            "Reply hazy try again",
            "Ask again later",
            "Better not tell you now",
            "Cannot predict now",
            "Concentrate and ask again",
            "Don't count on it",
            "My reply is no",
            "My sources say no",
            "Outlook not so good",
            "Very doubtful"
    };
    private final Random random;
    private CommandHandlingFacade facade;

    @Autowired
    public EightBallCommandHandler(CommandHandlingFacadeBuilder builder) {
        this(builder, new Random());
    }

    public EightBallCommandHandler(CommandHandlingFacadeBuilder builder, Random random) {
        this.random = random;
        this.facade = builder.setCmdId("8ball")
                .setReplier(new DecoratedReplier(builder.getTextFormatter(), ":8ball:"))
                .setValidatedCommandHandler(this::handle_)
                .build();
    }

    @Override
    public String help() {
        return "I can answer any of your questions...";
    }

    @Override
    public CommandHandlingFacade getFacade() {
        return facade;
    }

    public void handle_(Command c) {
        replyTo(c, fortunes[random.nextInt(fortunes.length)]);
    }
}
