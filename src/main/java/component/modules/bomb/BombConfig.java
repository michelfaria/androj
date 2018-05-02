package component.modules.bomb;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@Getter
@ConditionalOnProperty("bot.bomb.enabled")
public class BombConfig {
    private int maxTimeMs;
    private int minTimeMs;
    private int minWires;
    private int maxWires;

    @Value("${bot.bomb.maxTimeMs:200000}")
    public void setMaxTimeMs(int maxTimeMs) {
        if (maxTimeMs < 0) {
            throw new IllegalArgumentException("maxTimeMs cannot be less than 0");
        }
        this.maxTimeMs = maxTimeMs;
    }

    @Value("${bot.bomb.minTimeMs:60000}")
    public void setMinTimeMs(int minTimeMs) {
        if (minTimeMs < 0) {
            throw new IllegalArgumentException("minTimeMs cannot be less than 0");
        }
        this.minTimeMs = minTimeMs;
    }

    @Value("${bot.bomb.minWires:2}")
    public void setMinWires(int minWires) {
        if (minWires < 1) {
            throw new IllegalArgumentException("minWires cannot be less than 1");
        }
        this.minWires = minWires;
    }

    @Value("${bot.bomb.maxWires:6}")
    public void setMaxWires(int maxWires) {
        if (maxWires < 1) {
            throw new IllegalArgumentException("maxWires cannot be less than 1");
        }
        this.maxWires = maxWires;
    }
}
