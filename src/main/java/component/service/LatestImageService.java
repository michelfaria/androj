package component.service;

import net.dv8tion.jda.core.entities.Icon;
import net.dv8tion.jda.core.entities.MessageChannel;

public interface LatestImageService {
	Icon put(MessageChannel key, Icon value);

	Icon get(MessageChannel key);
}
