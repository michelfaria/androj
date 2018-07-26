package command.handler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import command.Command;
import command.strategy.Replier;
import command.strategy.ValidatedCommandHandler;
import command.strategy.ValidationFailureHandler;
import command.validation.Validator;
import component.formatter.TextFormatter;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.exceptions.InsufficientPermissionException;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

public class CommandHandlingFacadeNoValidatorsTest {

	private CommandHandlingFacade obj;
	@Mock
	private TextFormatter textFormatter;
	private String cmdId = "testCommand";
	private List<String> aliases = new ArrayList<>();
	{
		aliases.add("anotherCommand");
		aliases.add("sampleAlias");
	}
	@Mock
	private ValidationFailureHandler validationFailureHandler;
	private List<Validator> validators = Collections.emptyList();
	@Mock
	private Replier replier;
	private Supplier<String> syntaxSupplier = () -> "This is my syntax!";
	@Mock
	private ValidatedCommandHandler validatedCommandHandler;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		obj = new CommandHandlingFacade(textFormatter, cmdId, aliases, validationFailureHandler, validators, replier,
				syntaxSupplier, validatedCommandHandler);
	}

	private Command unmatchingCommand = new Command("unknown", Collections.emptyList(),
			mock(MessageReceivedEvent.class));

	@Test
	public void handle_UnmatchingCommand_DoesNotCallValidationFailureHandler() {
		obj.handle(unmatchingCommand);
		verify(validationFailureHandler, never()).accept(any(), any());
	}

	@Test
	public void handle_UnmatchingCommand_DoesNotCallValidatedCommandHandler() {
		obj.handle(unmatchingCommand);
		verify(validatedCommandHandler, never()).accept(any());
	}

	@Test
	public void handle_UnmatchingCommand_DoesNotCallReplier() {
		obj.handle(unmatchingCommand);
		verifyZeroInteractions(replier);
	}

	private Command matchingCommand = new Command("testCommand", Collections.emptyList(),
			mock(MessageReceivedEvent.class));

	@Test
	public void handle_MatchingCommand_CallsValidatedCommandHandler() {
		obj.handle(matchingCommand);
		verify(validatedCommandHandler, times(1)).accept(matchingCommand);
	}

	@Test
	public void handle_MatchingCommand_DoesNotCallValidationFailureHandler() {
		obj.handle(matchingCommand);
		verify(validationFailureHandler, never()).accept(any(), any());
	}
}
