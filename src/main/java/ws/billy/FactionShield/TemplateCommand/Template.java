package ws.billy.FactionShield.TemplateCommand;

import org.bukkit.command.CommandSender;
import ws.billy.FactionShield.CommandHandler.Objects.Command;
import ws.billy.FactionShield.Configuration.Messages.Messages;
import ws.billy.FactionShield.TemplateCommand.Subcommands.TestSubcommand;

public class Template extends Command {

	/***
	 * Create a new command, with subcommand
	 */
	public Template() {
		super("template", "A template command", 1, null, Messages.getCustomMessage("template_command_permission", "FactionShield.command.template"), 0);
		super.addSubCommand("test", new TestSubcommand());
	}

	@Override
	protected boolean command(final CommandSender sender, final org.bukkit.command.Command command, final String label, final String[] args) {
		commandReply(sender, "This works!", true);
		return true;
	}
}
