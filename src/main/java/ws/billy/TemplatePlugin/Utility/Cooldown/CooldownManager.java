package ws.billy.TemplatePlugin.Utility.Cooldown;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CooldownManager {

	private final Map<UUID, Long> _cooldowns = new HashMap<>();

	public void setCooldown(final UUID player, final long time) {
		if (time < 1) {
			_cooldowns.remove(player);
		} else {
			_cooldowns.put(player, time);
		}
	}

	public long getCooldown(final UUID player) {
		return _cooldowns.getOrDefault(player, 0L);
	}
}

