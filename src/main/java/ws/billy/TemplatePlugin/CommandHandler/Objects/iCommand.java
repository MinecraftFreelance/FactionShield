package ws.billy.TemplatePlugin.CommandHandler.Objects;

import org.bukkit.command.CommandSender;
import ws.billy.TemplatePlugin.Configuration.Messages.Messages;

public interface iCommand {

	/***
	 * Reply to the command with a message
	 * @param target the target of the message
	 * @param message the message
	 * @param includePrefix to include the plugin's prefix or not
	 */
	default void commandReply(final CommandSender target, final String message, final boolean includePrefix) {
		if (includePrefix) {
			target.sendMessage(Messages.getPrefix() + message);
			return;
		}
		target.sendMessage(message);
	}

}
