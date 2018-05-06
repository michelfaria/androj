package component.listeners;

import component.service.LatestImageService;
import net.dv8tion.jda.core.entities.Icon;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Saves what was the last image per channel.
 */
@Component
public class LatestImagePerChannel extends ListenerAdapter implements BotListener {

	private LatestImageService latestImageService;

	@Autowired
	public LatestImagePerChannel(LatestImageService latestImageService) {
		this.latestImageService = latestImageService;
	}

	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		List<Icon> imageAttachments = getImageAttachments(event.getMessage());
		if (!imageAttachments.isEmpty()) {
			// put last image on the map
			latestImageService.put(event.getChannel(), imageAttachments.get(imageAttachments.size() - 1));
		}
	}

	private List<Icon> getImageAttachments(Message message) {
		List<Icon> imageAttachments = new ArrayList<>();
		List<Message.Attachment> attachments = message.getAttachments();
		for (Message.Attachment attachment : attachments) {
			if (attachment.isImage()) {
				try {
					Icon image = attachment.getAsIcon();
					imageAttachments.add(image);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return imageAttachments;
	}

	@Override
	public int priority() {
		return 1;
	}
}
