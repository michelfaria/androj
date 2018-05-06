package component.command.validation.permission;

import command.Command;
import command.validation.Validator;
import component.BotConfig;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.User;

import java.util.Collections;
import java.util.List;

public class GuildPermissionValidator implements Validator {

    private BotConfig botConfig;
    private List<Permission> requiredPermissions;

    GuildPermissionValidator(BotConfig botConfig, List<Permission> requiredPermissions) {
        this.botConfig = botConfig;
        this.requiredPermissions = requiredPermissions;
    }

    @Override
    public List<String> validate(Command c) {
        Member member = c.getEvent().getMember();
        if (member == null) {
            return Collections.singletonList("This command can only be used in a guild!");
        }
        if (isAuthorized(member)) {
            return Collections.emptyList();
        }
        return Collections.singletonList("You do not have permission to use this command!");
    }

    private boolean isAuthorized(Member u) {
        return isAdmin(u.getUser()) || hasAllPermissions(u);
    }

    private boolean isAdmin(User u) {
        return botConfig.getAdmins().contains(u.getId());
    }

    private boolean hasAllPermissions(Member m) {
        return m.hasPermission(requiredPermissions);
    }
}
