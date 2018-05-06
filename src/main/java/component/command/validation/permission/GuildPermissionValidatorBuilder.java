package component.command.validation.permission;

import component.BotConfig;
import lombok.Getter;
import net.dv8tion.jda.core.Permission;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Scope("singleton")
@Getter
public class GuildPermissionValidatorBuilder {

	private static final Logger LOGGER = LoggerFactory.getLogger(GuildPermissionValidator.class);

	private BotConfig botConfig;
	private List<Permission> requiredPermissions = new ArrayList<>();

	@Autowired
	public GuildPermissionValidatorBuilder(BotConfig botConfig) {
		this.botConfig = botConfig;
	}

	public GuildPermissionValidator build() {
		if (requiredPermissions.isEmpty()) {
			LOGGER.warn(GuildPermissionValidator.class.getName() + " created without any required permissions!");
		}
		return new GuildPermissionValidator(botConfig, requiredPermissions);
	}

	public GuildPermissionValidatorBuilder setRequiredPermissions(List<Permission> requiredPermissions) {
		this.requiredPermissions = requiredPermissions;
		return this;
	}
}
