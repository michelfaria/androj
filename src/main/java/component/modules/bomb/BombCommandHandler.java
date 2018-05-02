package component.modules.bomb;

import command.Command;
import command.handler.AbstractCommandHandler;
import command.handler.CommandHandlingFacade;
import command.handler.RegisteredCommandHandler;
import command.handler.strategy.DecoratedReplier;
import component.BotConfig;
import component.command.CommandHandlingFacadeBuilder;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Component
@ConditionalOnBean(BombConfig.class)
public class BombCommandHandler extends AbstractCommandHandler implements RegisteredCommandHandler {

    private CommandHandlingFacade facade;
    private BombService bombService;

    private BotConfig botConfig;
    private CutWireCommandHandler cutWireCommandHandler;

    public BombCommandHandler(CommandHandlingFacadeBuilder builder, BombService bombService, BotConfig botConfig,
                              CutWireCommandHandler cutWireCommandHandler) {
        this.facade = builder
                .setCmdId("bomb")
                .setReplier(new DecoratedReplier(builder.getTextFormatter(), ":bomb:"))
                .setSyntaxSupplier(() -> "<user>")
                .setValidatedCommandHandler(this::handle_)
                .build();
        this.bombService = bombService;
        this.botConfig = botConfig;
        this.cutWireCommandHandler = cutWireCommandHandler;
    }

    private void handle_(Command c) {
        final List<Member> mentioned = c.getEvent().getMessage().getMentionedMembers();
        if (mentioned.isEmpty()) {
            replyTo(c, "Please mention who you want to bomb.");
        } else if (mentioned.size() > 1) {
            replyTo(c, "You can only bomb one person.");
        } else {
            try {
                User victim = mentioned.get(0).getUser();
                bombUser(c, victim);
            } catch (VictimAlreadyHasBombException e) {
                replyTo(c, "That person already has a bomb!");
            }
        }
    }

    private void bombUser(Command c, User victim) throws VictimAlreadyHasBombException {
        if (victim.equals(c.getEvent().getJDA().getSelfUser())) {
            victim = c.getEvent().getAuthor(); // :)
        }
        TextChannel ch = c.getEvent().getTextChannel();
        final Bomb bomb = bombService.putBombOn(victim, ch, onExplode(ch), onDefuse(ch));
        replyTo(c, "You shove a bomb down "
                + victim.getAsMention()
                + "'s pants. They complain, but can't fight back. The timer is set for "
                + bomb.getDetonationTime() / 1000
                + " seconds! The colors are: "
                + formatWireColors(bomb)
                + ". Use " + cutWireCommand() + " to cut a wire!");
    }

    private Consumer<Bomb> onExplode(TextChannel ch) {
        return (bomb) -> ch.sendMessage(":boom: KABOOM! " + bomb.getVictim().getAsMention() + " blew up.").queue();
    }

    private Consumer<Bomb> onDefuse(TextChannel ch) {
        return (bomb) -> ch.sendMessage(":clap: :clap: :clap: " + bomb.getVictim().getAsMention()
                + ", you have successfully defused the bomb! :clap: :clap: :clap:").queue();
    }

    private String formatWireColors(Bomb bomb) {
        return bomb.getWireColors().stream()
                .map(WireColors::toString)
                .collect(Collectors.joining(", "));
    }

    private String cutWireCommand() {
        return botConfig.getPrefix() + cutWireCommandHandler.getCommandId();
    }

    @Override
    public CommandHandlingFacade getFacade() {
        return facade;
    }
}
