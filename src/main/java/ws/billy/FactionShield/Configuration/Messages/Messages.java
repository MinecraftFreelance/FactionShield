package ws.billy.FactionShield.Configuration.Messages;

import com.massivecraft.factions.Faction;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import ws.billy.FactionShield.FactionShield;

@SuppressWarnings("checkstyle:HideUtilityClassConstructor")
public class Messages {

	public static String getCustomMessage(final String path) {
		return ChatColor.translateAlternateColorCodes('&', "" + FactionShield.getConfiguration().getConfigField(path));
	}

	public static String getCustomMessage(final String path, final String defaultMessage) {
		return ChatColor.translateAlternateColorCodes('&', FactionShield.getMessagesConfiguration().getConfigField(path, defaultMessage)).replace("{{time}}", "" + getCustomMessage("escape_time"));
	}

	public static String getCustomMessage(final String path, final String defaultMessage, final int remaining) {
		return ChatColor.translateAlternateColorCodes('&', FactionShield.getMessagesConfiguration().getConfigField(path, defaultMessage)).replace("{{remaining_time}}", "" + remaining);
	}

	public static String getCustomMessage(final String path, final String defaultMessage, final Location location) {
		return ChatColor.translateAlternateColorCodes('&', FactionShield.getMessagesConfiguration().getConfigField(path, defaultMessage)).replace("{{location}}", "" + location.getX() + ", " + location.getZ());
	}

	public static String getCustomMessage(final String path, final String defaultMessage, final Faction faction, final int time) {
		return ChatColor.translateAlternateColorCodes('&', FactionShield.getMessagesConfiguration().getConfigField(path, defaultMessage)).replace("{{faction}}", "" + faction.getTag()).replace("{{time}}", "" + getCustomMessage("escape_time"));
	}

	public static String getPrefix() {
		return ChatColor.translateAlternateColorCodes('&', FactionShield.getMessagesConfiguration().getConfigField("prefix", "&b[" + FactionShield.getPluginName() + "] "));
	}

	public static String getPermissionsErrorMessage() {
		return ChatColor.translateAlternateColorCodes('&', FactionShield.getMessagesConfiguration().getConfigField("no_permission", "&cYou do not have permission to use this command!"));
	}

}
