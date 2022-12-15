package ws.billy.TemplatePlugin.Configuration.Messages;

import org.bukkit.ChatColor;
import ws.billy.TemplatePlugin.TemplatePlugin;

@SuppressWarnings("checkstyle:HideUtilityClassConstructor")
public class Messages {

	public static String getCustomMessage(final String path) {
		return ChatColor.translateAlternateColorCodes('&', TemplatePlugin.getMessagesConfiguration().getConfigField(path));
	}

	public static String getCustomMessage(final String path, final String defaultMessage) {
		return ChatColor.translateAlternateColorCodes('&', TemplatePlugin.getMessagesConfiguration().getConfigField(path, defaultMessage));
	}

	public static String getPrefix() {
		return ChatColor.translateAlternateColorCodes('&', TemplatePlugin.getMessagesConfiguration().getConfigField("prefix", "&b[" + TemplatePlugin.getPluginName() + "] "));
	}

	public static String getPermissionsErrorMessage() {
		return ChatColor.translateAlternateColorCodes('&', TemplatePlugin.getMessagesConfiguration().getConfigField("no_permission", "&cYou do not have permission to use this command!"));
	}

}
