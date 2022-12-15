package ws.billy.FactionShield.Configuration.Messages;

import org.bukkit.ChatColor;
import ws.billy.FactionShield.FactionShield;

@SuppressWarnings("checkstyle:HideUtilityClassConstructor")
public class Messages {

	public static String getCustomMessage(final String path) {
		return ChatColor.translateAlternateColorCodes('&', FactionShield.getMessagesConfiguration().getConfigField(path));
	}

	public static String getCustomMessage(final String path, final String defaultMessage) {
		return ChatColor.translateAlternateColorCodes('&', FactionShield.getMessagesConfiguration().getConfigField(path, defaultMessage));
	}

	public static String getPrefix() {
		return ChatColor.translateAlternateColorCodes('&', FactionShield.getMessagesConfiguration().getConfigField("prefix", "&b[" + FactionShield.getPluginName() + "] "));
	}

	public static String getPermissionsErrorMessage() {
		return ChatColor.translateAlternateColorCodes('&', FactionShield.getMessagesConfiguration().getConfigField("no_permission", "&cYou do not have permission to use this command!"));
	}

}
