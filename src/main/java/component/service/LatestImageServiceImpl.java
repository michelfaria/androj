package component.service;

import net.dv8tion.jda.core.entities.Icon;
import net.dv8tion.jda.core.entities.MessageChannel;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class LatestImageServiceImpl implements LatestImageService {

    private final Map<MessageChannel, Icon> channelLastImageMap = new HashMap<>();

    @Override
    public Icon put(MessageChannel key, Icon value) {
        return channelLastImageMap.put(key, value);
    }

    @Override
    public Icon get(MessageChannel key) {
        return channelLastImageMap.get(key);
    }
}
