package component;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@Getter
public class BotConfig {
    @Value("${bot.token:}")
    @Setter
    private String token;

    @Value("${bot.prefix}")
    private String prefix;

    private List<String> admins;

    @Autowired
    public BotConfig(@Value("${bot.admins}") String admins) {
        this.admins = Arrays.asList(admins.split(","));
    }
}
