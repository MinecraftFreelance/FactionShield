package ws.billy.FactionShield.CommandHandler.Objects;

import org.bukkit.command.CommandSender;
import org.bukkit.command.Command;

public abstract class SubCommand implements iCommand {

	public abstract void execute(CommandSender sender, Command command, String label, String[] args);

	public abstract String getUsage();

	public abstract String getDescription();

}
