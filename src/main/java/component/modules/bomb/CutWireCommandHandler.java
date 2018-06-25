package component.modules.bomb;

import command.Command;
import command.handler.AbstractCommandHandler;
import command.handler.CommandHandlingFacade;
import command.handler.RegisteredCommandHandler;
import command.strategy.DecoratedReplier;
import command.validation.ArgLengthValidator;
import component.command.CommandHandlingFacadeBuilder;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

@Component
@ConditionalOnBean(BombConfig.class)
public class CutWireCommandHandler extends AbstractCommandHandler implements RegisteredCommandHandler {

	private static final String NOT_VALID_COLOR = "THAT'S NOT A VALID COLOR! HURRY! PICK A VALID COLOR!";
	private static final String NO_BOMB = "You don't have a bomb, lol.";

	private CommandHandlingFacade facade;
	private BombService bombService;

	@Autowired
	public CutWireCommandHandler(CommandHandlingFacadeBuilder builder, BombService bombService) {
		this.facade = builder.setCmdId("cutwire")
				.setAliases(Collections.singletonList("cut"))
				.setReplier(new DecoratedReplier(":scissors:"))
				.setSyntaxSupplier(() -> "<color>").setValidators(Collections.singletonList(new ArgLengthValidator(1)))
				.setValidatedCommandHandler(this::handle_).build();
		this.bombService = bombService;
	}

	private void handle_(Command c) {
		assert c.getArgs().size() == 1;
		final Optional<Bomb> bombOpt = bombService.bombFor(c.getEvent().getAuthor());
		if (!bombOpt.isPresent()) {
			replyTo(c, NO_BOMB);
		}
		bombOpt.ifPresent(bomb -> {
			WireColors wireColor;
			try {
				wireColor = WireColors.valueOf(c.getArgs().get(0).toUpperCase());
			} catch (IllegalArgumentException ex) {
				replyTo(c, NOT_VALID_COLOR);
				return;
			}
			bomb.cutWire(wireColor);
		});
	}

	@NotNull
	@Override
	public CommandHandlingFacade getFacade() {
		return facade;
	}
}
