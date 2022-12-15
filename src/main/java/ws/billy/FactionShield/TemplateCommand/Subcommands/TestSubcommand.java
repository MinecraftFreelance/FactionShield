package ws.billy.FactionShield.TemplateCommand.Subcommands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import ws.billy.FactionShield.CommandHandler.Objects.SubCommand;

public class TestSubcommand extends SubCommand {
	@Override
	public void execute(final CommandSender sender, final Command command, final String label, final String[] args) {
		commandReply(sender, "test", true);
	}

	@Override
	public String getUsage() {
		return null;
	}

	@Override
	public String getDescription() {
		return "test if the command works";
	}
}
